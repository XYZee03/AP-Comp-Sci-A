package main.java.server;

import main.java.game.Player;
import main.java.communication.GameState;
import main.java.game.Game;
import main.java.communication.Message.MessageType;
import main.java.communication.Message;
import main.java.communication.Draw;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import javax.swing.*;
import java.util.HashSet;
import java.util.List;

public final class Server extends JFrame
{

    private int port;

    private String host;

    private HashSet<String> bannedNames = new HashSet<>();

    private HashMap<String, ClientThread> connections = new HashMap<>();

    private LinkedBlockingQueue<ClientThreadMessage> inChat = new LinkedBlockingQueue<>();

    private ServerSocket serverSocket;

    private ServerThread serverThread;

    private JButton onOffBtn = new JButton("Connect");

    private JButton startGameBtn = new JButton("Start Game");

    private JButton overrideRoundBtn = new JButton("End Round");

    volatile private boolean shutdown;

    private boolean running = false;

    private boolean wasOff = false;

    private final ArrayList<String> words;

    private Game game;

    private Server()
    {
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        this.add(onOffBtn);
        this.add(startGameBtn);
        this.add(overrideRoundBtn);


        InputStream in = getClass().getResourceAsStream("settings.txt");

        Scanner scanner;
        host = "";
        port = -1;
        boolean useDefault = false;
        scanner = new Scanner(new InputStreamReader(in));
        scanner.useDelimiter("~");
        try
        {
            while ( scanner.hasNextLine() )
            {
                if ( scanner.hasNext() )
                {
                    String name = scanner.next().trim();
                    String value = scanner.next().trim();
                    switch (name) {
                        case "host:":
                            host = value;
                            break;
                        case "port:":
                            port = Integer.parseInt(value);
                            break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        catch (Exception ex)
        {
            useDefault = true;
        }
        if(useDefault)
        {
            System.err.println("Using default settings - IP: 127.0.0.1 (localhost) - Port: 3000");
            host = "127.0.0.1";
            port = 3000;
        }
        ArrayList<String> words = new ArrayList<>();
        String wordFilePath = "words.txt";
        try
        {
            InputStream wordReader = getClass().getResourceAsStream(wordFilePath);
            scanner = new Scanner(new InputStreamReader(wordReader));
            while ( scanner.hasNext() )
            {
                words.add(scanner.next().toLowerCase());
            }
        }
        catch(Exception e)
        {
            System.exit(ERROR);
        }

        this.words = words;
        game = new Game(words);
        Dimension d = new Dimension(100, 100);
        onOffBtn.addActionListener(new ConnectButtonListener());
        onOffBtn.setMinimumSize(d);
        onOffBtn.setMaximumSize(d);
        startGameBtn.addActionListener(new StartGameListener());
        startGameBtn.setMinimumSize(d);
        startGameBtn.setMaximumSize(d);
        startGameBtn.setEnabled(false);
        overrideRoundBtn.addActionListener(new OverrideListener());
        overrideRoundBtn.setMinimumSize(d);
        overrideRoundBtn.setMaximumSize(d);
        overrideRoundBtn.setEnabled(false);
        banNames();
        this.addWindowListener(new CloseWindowListener());
        this.setResizable(false);
        this.setSize(300, 100);
    }

    private void banNames()
    {
        bannedNames.clear();
        bannedNames.add("server");
        bannedNames.add("admin");
        bannedNames.add("");
    }

    private boolean nameAllowed(String username)
    {
        return username != null && !bannedNames.contains(username.toLowerCase());
    }

    private class OverrideListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            game.next();
        }
    }

    private class CloseWindowListener extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            ShutOffServer();
            System.exit(0);
        }
    }

    private class ConnectButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            if(!running)
            {
                try
                {
                    onOffBtn.setText("Disconnect");
                    running = true;
                    startGameBtn.setEnabled(true);
                    overrideRoundBtn.setEnabled(true);
                    if(!wasOff)
                    {
                        connect();
                    }
                    else
                    {
                        restart(port);
                    }
                }
                catch (IOException e)
                {
                    JOptionPane.showMessageDialog(Server.this, "Could not start server, exiting.");
                    Server.this.processWindowEvent(
                            new WindowEvent(
                                    Server.this, WindowEvent.WINDOW_CLOSING));
                }
            }
            else
            {
                ShutOffServer();
                running = false;
                wasOff = true;
                onOffBtn.setText("Connect");
                startGameBtn.setText("Start Game");
                startGameBtn.setEnabled(false);
                overrideRoundBtn.setEnabled(false);
            }
        }
    }

    private class StartGameListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            game.start();
            startGameBtn.setText("Next Round");
        }
    }

    private void connect() throws IOException
    {
        serverSocket = new ServerSocket(port, 0, InetAddress.getByName(host));
        serverThread = new ServerThread();
        serverThread.start();
        Thread readerThread = new Thread(() -> {
            while (true) {
                try {
                    ClientThreadMessage msg = inChat.take();
                    getMessage(msg.clientThread, msg.message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        readerThread.setDaemon(true);
        readerThread.start();
    }

    private void getMessage(ClientThread client, Message message)
    {
        if(message.messageType == MessageType.JOIN_REQUEST)
        {
            GameState gameState = new GameState(game.getPlayerNames(), game.getStatus());
            sendSolo(client.clientID, Message.CreateDataMessage(MessageType.JOIN, gameState));
            game.join(client);
        }
        else if(message.messageType == MessageType.GUESS)
        {
            game.guess(client, (String)message.data.data);
        }
        else if(message.messageType == MessageType.DISCONNECT)
        {
            if(game.isPlayer(client))
            {
                game.playerLeft(client);

            }
            disconnect(client);
            client.close();
        }
        else
        {
            send(message);
        }
    }

    synchronized private List<String> getClients()
    {
        return new ArrayList<>(this.connections.keySet());
    }

    private void restart(int port) throws IOException
    {
        banNames();
        game = new Game(words);
        if (serverThread != null && serverThread.isAlive())
            throw new IllegalStateException("Server is listening.");
        shutdown = false;
        serverSocket = new ServerSocket(port);
        serverThread = new ServerThread();
        serverThread.start();
    }

    private void shutOffSocket()
    {
        if (serverThread == null)
            return;
        inChat.clear();
        shutdown = true;
        try
        {
            serverSocket.close();
        }
        catch (IOException ignored){}
        serverThread = null;
        serverSocket = null;
    }

    private void ShutOffServer()
    {
        shutOffSocket();
        send(new Message(MessageType.SERVER_DISCONNECT));
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ignored) {}
        for (ClientThread clientThread : connections.values())
        {
            clientThread.close();
        }
    }

    synchronized private void connect(ClientThread newConnection)
    {
        connections.put(newConnection.clientID, newConnection);
        bannedNames.add(newConnection.clientID.toLowerCase());
        send(new Message(MessageType.CONNECT_CLIENT, newConnection.clientID));
    }

    synchronized private void disconnect(ClientThread client)
    {
        if (connections.containsKey(client.clientID))
        {
            connections.remove(client.clientID);
            bannedNames.remove(client.clientID.toLowerCase());
            Message message = new Message(MessageType.DISCONNECT_CLIENT, client.clientID);
            send(message);
        }
    }


    synchronized private void send(Message message)
    {
        for (ClientThread clientThread : connections.values())
        {
            clientThread.send(message);
        }
    }

    synchronized private void sendSolo(String recipient, Message message)
    {
        ClientThread clientThread = connections.get(recipient);
        if (clientThread != null) {
            clientThread.send(message);
        }
    }

    synchronized private void error(ClientThread client, String errorMessage)
    {
        disconnect(client);
        System.err.println(errorMessage);
    }

    private class ClientThreadMessage
    {
        ClientThread clientThread;
        Message message;
    }

    private class ServerThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                while (!shutdown)
                {
                    Socket connection = serverSocket.accept();
                    new ClientThread(inChat, connection);
                }
            }
            catch (Exception ignored)
            {
            }
        }
    }

    private class ClientThread extends Player
    {
        private String clientID;

        private BlockingQueue<ClientThreadMessage> incomingMessages;

        private LinkedBlockingQueue<Message> outgoingMessages;

        private Socket connection;

        private ObjectInputStream in;

        private ObjectOutputStream out;

        private volatile boolean closed;

        private Thread sendThread;

        private volatile Thread receiveThread;

        ClientThread(BlockingQueue<ClientThreadMessage> receivedMessageQueue, Socket connection)
        {
            super(0, -1);
            this.connection = connection;
            incomingMessages = receivedMessageQueue;
            outgoingMessages = new LinkedBlockingQueue<>();
            sendThread =  new SendThread();
            sendThread.start();
        }

        public String getUsername()
        {
            return clientID;
        }

        private void close()
        {
            closed = true;
            sendThread.interrupt();
            if (receiveThread != null)
            {
                receiveThread.interrupt();
            }
            try
            {
                connection.close();
            }
            catch (IOException ignored) { }
        }

        void send(Message message)
        {
            outgoingMessages.add(message);
        }

        private void closedWithError(String message)
        {
            error(this, message);
            close();
        }

        private class SendThread extends Thread
        {
            @Override
            public void run()
            {
                try
                {
                    out = new ObjectOutputStream(connection.getOutputStream());
                    in = new ObjectInputStream(connection.getInputStream());
                    while(true)
                    {
                        out.writeObject(new Message(MessageType.GET_USERNAME));
                        out.flush();
                        Message message = (Message)in.readObject();
                        if(message == null)
                        {
                            ClientThread.this.closedWithError("The client is not behaving");
                            return;
                        }
                        if(message.messageType == MessageType.RETURN_USERNAME)
                        {
                            if(nameAllowed(message.client))
                            {
                                ClientThread.this.clientID = message.client;
                                break;
                            }
                        }
                    }
                    connect(ClientThread.this);
                    main.java.communication.State clients = new main.java.communication.State(getClients());
                    Message serverAccept = Message.CreateDataMessage(MessageType.CONNECTED_TO_SERVER, clients);
                    out.writeObject(serverAccept);
                    out.flush();
                    receiveThread = new ReceiveThread();
                    receiveThread.start();
                }
                catch(Exception e)
                {
                    try
                    {
                        closed = true;
                        connection.close();
                    }
                    catch (Exception ignored)
                    {
                    }
                    e.printStackTrace();
                    return;
                }
                try
                {
                    while (!closed)
                    {
                        try
                        {
                            Message message = outgoingMessages.take();
                            out.reset();
                            out.writeObject(message);
                            out.flush();
                        }
                        catch (InterruptedException ignored)
                        {
                        }
                    }
                }
                catch (IOException e)
                {
                    if (!closed)
                    {
                        closedWithError("Error while sending data to client.");
                    }
                }
                catch (Exception e)
                {
                    if (!closed)
                    {
                        closedWithError("Internal Error: Unexpected exception in output thread: " + e);
                        e.printStackTrace();
                    }
                }
            }

        }

        private class ReceiveThread extends Thread
        {
            @Override
            public void run()
            {
                try
                {
                    while (!closed)
                    {
                        try
                        {
                            Message message = (Message)in.readObject();
                            ClientThreadMessage clientThreadMessage = new ClientThreadMessage();
                            clientThreadMessage.clientThread = ClientThread.this;
                            clientThreadMessage.message = message;
                            incomingMessages.put(clientThreadMessage);
                        }
                        catch (InterruptedException e)
                        {
                            return;
                        }
                    }
                }
                catch (IOException e)
                {
                    if (!closed)
                    {
                        closedWithError("Error while reading data from client.");
                    }
                }
                catch (Exception e)
                {
                    if (!closed)
                    {
                        closedWithError("Internal Error: Unexpected exception in input thread: " + e);
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void stopDraw()
        {
            send(new Message(MessageType.STOP_DRAW));
        }

        @Override
        public void stopDrawClient(String clientID)
        {
            send(new Message(MessageType.STOP_DRAW_CLIENT, clientID));
        }

        @Override
        public void startDraw(String wordToDraw)
        {
            Draw draw = new Draw(wordToDraw, 0);
            Message theMessage = Message.CreateDataMessage(MessageType.START_DRAW, draw);
            send(theMessage);
        }

        @Override
        public void startDrawClient(String clientID)
        {

            send(new Message(MessageType.START_DRAW_CLIENT, clientID));
        }

        @Override
        public void startGame()
        {
            send(Message.CreateDataMessage(MessageType.SERVER_MESSAGE, ""));
        }

        @Override
        public void correct(String guess)
        {
            send(Message.CreateDataMessage(MessageType.CORRECT, guess));
        }

        @Override
        public void correctMessage(String clientID, String guess) {
            send(Message.CreateDataMessage(MessageType.CORRECT_CLIENT, clientID, guess));
        }

        @Override
        public void startGuess(String word)
        {
            send(new Message(MessageType.START_GUESS, word));
        }

        @Override
        public void incorrect(String clientID, String guess)
        {
            send(Message.CreateDataMessage(MessageType.INCORRECT_CLIENT, clientID, guess));
        }

        private int roundNum = 0;

        @Override
        public void scoreMessage(List<String> players, List<Integer> scores)
        {
            roundNum++;
            StringBuilder message = new StringBuilder();
            int highScore = -1;
            message.append("End of Round ").append(roundNum).append("\n");
            for(int i = 0; i < players.size(); ++ i)
            {
                message.append(players.get(i)).append(" finished with ").append(scores.get(i)).append(" points.\n");
                if(scores.get(i) > highScore)
                {
                    highScore = scores.get(i);
                }
            }

            ArrayList<String> winners = new ArrayList<>();
            for(int i = 0; i < scores.size(); ++i)
            {
                if(scores.get(i) == highScore)
                {
                    winners.add(players.get(i));
                }
            }

            if(winners.size() == 1)
            {
                message.append(winners.get(0)).append(" wins, with ").append(highScore).append(" points.");
            }
            else
            {
                message.append("The winners, with ").append(highScore).append(" points, are:");
                String deliminator = "\n";
                for(String winner : winners)
                {
                    message.append(deliminator).append(winner);
                    deliminator = ",\n";
                }
            }
            send(Message.CreateDataMessage(MessageType.SERVER_MESSAGE, message.toString()));
        }

        @Override
        public void revealWord(String word)
        {
            send(Message.CreateDataMessage(MessageType.SERVER_MESSAGE, "The word was \"" + word + "\""));
        }
    }

    public static void main(String[] args)
    {
        final Server server = new Server();

        EventQueue.invokeLater(() -> server.setVisible(true));
    }
}
