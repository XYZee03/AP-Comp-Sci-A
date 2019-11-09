package main.java.client;

import main.java.communication.GameState;
import main.java.communication.Message;
import main.java.communication.Message.MessageType;
import main.java.communication.Draw;
import main.java.communication.State;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.awt.Point;

class Client
{
    private List<String> clients;

    private ServerThread connection = null;

    private String ip;

    private int port;

    private Actions actions = null;

    Client(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    void join()
    {
        send(new Message(MessageType.JOIN_REQUEST));
    }

    void connect(Actions actions)
            throws IOException
    {
        this.actions = actions;
        connection = new ServerThread(ip, port);
    }

    void disconnect()
    {
        if (connection != null)
        {
            connection.send(new Message(MessageType.DISCONNECT));
        }
    }

    private void send(Message message) throws IllegalStateException
    {
        if (connection.closed)
        {
            throw new IllegalStateException(
                    "Can't send messages when the connection is closed.");
        }
        connection.send(message);
    }

    void sendMessage(String message)
    {
        send(Message.CreateDataMessage(Message.MessageType.CLIENT_MESSAGE, this.getUsername(), message));
    }

    void sendDrawing(Point p)
    {
        send(Message.CreateDataMessage(MessageType.DRAWING, p));
    }

    void makeGuess(String guess)
    {
        if(!guess.equals(""))
        {
            send(Message.CreateDataMessage(MessageType.GUESS, guess));
        }
    }

    private String getUsername()
    {
        return connection.id;
    }

    private class ServerThread
    {
        private final String id;

        private final Socket socket;

        private final ObjectInputStream in;

        private final ObjectOutputStream out;

        private final SendThread sendThread;

        private final ReceiveThread receiveThread;

        private final LinkedBlockingQueue<Message> outgoingMessages;

        private volatile boolean closed;

        ServerThread(String host, int port) throws IOException
        {
            outgoingMessages = new LinkedBlockingQueue<>();
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            String userName = null;
            try
            {
                Message message = (Message)in.readObject();
                while(message.messageType == MessageType.GET_USERNAME)
                {
                    userName = actions.getUsername().trim();
                    out.writeObject(
                            new Message(MessageType.RETURN_USERNAME, userName));
                    out.flush();
                    message = (Message)in.readObject();
                }
                this.handleMessage(message);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                closedByError("Could not connect to the main.java.server.");
                id = null;
                sendThread = null;
                receiveThread = null;
                return;
            }
            id = userName;
            sendThread = new SendThread();
            receiveThread = new ReceiveThread();
            sendThread.start();
            receiveThread.start();
        }

        private void close()
        {
            closed = true;
            if(sendThread != null && receiveThread != null)
            {
                sendThread.interrupt();
                receiveThread.interrupt();
            }
            try
            {
                socket.close();
            }
            catch (IOException ignored)
            {
            }
        }

        private void send(Message message)
        {
            outgoingMessages.add(message);
        }

        private synchronized void closedByError(String error)
        {
            actions.error(error);
            System.err.println(error);
            if (!closed)
            {
                close();
            }
        }

        private void handleMessage(Message message)
        {
            if(message.messageType == MessageType.CONNECTED_TO_SERVER)
            {
                State state = (State)message.data.data;
                clients = state.connectedClients;
                actions.connectedToServer();
            }
            else if(message.messageType == MessageType.CONNECT_CLIENT)
            {
                clients.add(message.client);
                actions.connectClient(message.client);
            }
            else if(message.messageType == MessageType.DISCONNECT_CLIENT)
            {
                clients.remove(message.client);
                actions.disconnectClient(message.client);
            }
            else if(message.messageType == MessageType.SERVER_MESSAGE)
            {
                actions.serverMessage((String)message.data.data);
            }
            else if(message.messageType == MessageType.CLIENT_MESSAGE)
            {
                actions.clientMessage(message.client, (String)message.data.data);
            }
            else if(message.messageType == MessageType.JOIN)
            {
                GameState gameState = (GameState)message.data.data;
                actions.joined(gameState.connectedPlayers, gameState.gameStatus);
            }
            else if (message.messageType == MessageType.START_DRAW)
            {
                Draw draw = (Draw) message.data.data;
                actions.startDraw(draw.word, draw.time);
            }
            else if (message.messageType == MessageType.STOP_DRAW)
            {
                actions.stopDraw();
            }
            else if (message.messageType == MessageType.DRAWING)
            {
                Point point = (Point)message.data.data;
                actions.getDrawing(point);
            }
            else if (message.messageType == MessageType.START_GUESS)
            {
                actions.startGuess(message.client);
            }
            else if (message.messageType == MessageType.STOP_GUESS)
            {
                actions.stopGuess();
            }
            else if (message.messageType == MessageType.CORRECT)
            {
                actions.correctMessage();
            }
            else if (message.messageType == MessageType.CORRECT_CLIENT)
            {
                actions.correctClientMessage(
                        message.client, (String) message.data.data);
            }
            else if (message.messageType == MessageType.INCORRECT_CLIENT)
            {
                actions.incorrectClientMessage(message.client, (String)message.data.data);
            }
            else if(message.messageType == MessageType.START_DRAW_CLIENT)
            {
                actions.startClientDraw(message.client);
            }
        }

        private class SendThread extends Thread
        {
            @Override
            public void run()
            {
                try
                {
                    while (!closed)
                    {
                        Message message = outgoingMessages.take();
                        out.reset();
                        out.writeObject(message);
                        out.flush();
                        if(message.messageType == MessageType.DISCONNECT)
                        {
                            connection.close();
                        }
                    }
                }
                catch (IOException e)
                {
                    if (!closed)
                    {
                        closedByError("Error occurred when sending message.");
                    }
                }
                catch (Exception e)
                {
                    if (!closed)
                    {
                        e.printStackTrace();
                        closedByError("SendThread encountered an unexpected error: " + e);
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
                        Message message = (Message) in.readObject();
                        if (message.messageType == MessageType.SERVER_DISCONNECT)
                        {
                            actions.disconnectedFromServer();
                            close();
                        }
                        else
                        {
                            handleMessage(message);
                        }
                    }
                }
                catch (IOException e)
                {
                    if (!closed)
                    {
                        closedByError("Error occurred when receiving message.");
                    }
                }
                catch (Exception e)
                {
                    if (!closed)
                    {
                        e.printStackTrace();
                        closedByError("ReceiveThread encountered an unexpected error: " + e);
                    }
                }
            }
        }

    }
}
