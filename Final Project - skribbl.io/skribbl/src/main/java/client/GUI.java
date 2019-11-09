package main.java.client;

import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;

import main.java.game.Game;

import java.awt.event.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GUI extends JFrame implements Actions
{
    private Client client;

    private String name;

    private JTextArea receivedText = new JTextArea();

    private JTextArea playerList;

    private JTextField guessField = new JTextField();

    private JLabel label = new JLabel("", SwingConstants.CENTER);

    private JButton disconnectOrConnectBtn = new JButton("Connect");

    private JButton clearBtn = new JButton("Clear");

    private Drawer drawer = new Drawer();

    private boolean connected = false;

    private boolean drawing = false;

    private StringBuilder list;

    private CopyOnWriteArrayList<String> playerNames;

    private String word;


    private GUI(Client client) {
        super("Not connected");
        this.addWindowListener(new CloseWindowListener());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1200, 850);
        this.setLayout(new BorderLayout());
        this.client = client;
        JLayeredPane panel = new JLayeredPane();
        panel.setSize(1200, 850);
        panel.setLayout(null);
        ImagePanel image = new ImagePanel(new ImageIcon(getClass().getResource("background.png")).getImage());
        this.getContentPane().add(image);

        TitledBorder title;
        receivedText.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret)receivedText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        title = BorderFactory.createTitledBorder("");
        guessField.setBorder(title);

        JPanel textAreaFrame = new JPanel();
        textAreaFrame.setSize(300, 600);
        textAreaFrame.setLayout(new BorderLayout());
        JScrollPane receivedTextScrollPane = new JScrollPane(receivedText);
        receivedTextScrollPane.setPreferredSize(new Dimension(300, 560));
        textAreaFrame.add(receivedTextScrollPane, BorderLayout.NORTH);

        guessField.setPreferredSize(new Dimension(300, 40));
        textAreaFrame.add(guessField, BorderLayout.SOUTH);


        JPanel buttonFrame = new JPanel();
        buttonFrame.setLayout(new BoxLayout(buttonFrame, BoxLayout.X_AXIS));
        buttonFrame.add(disconnectOrConnectBtn);
        buttonFrame.setSize(100, 25);
        buttonFrame.setOpaque(false);


        receivedText.setEnabled(false);
        receivedText.setDisabledTextColor(Color.black);
        disconnectOrConnectBtn.addActionListener(new ConnectButtonListener());
        final String TEXT_SUBMIT = "text-submit";
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        InputMap input = guessField.getInputMap();
        input.put(enter, TEXT_SUBMIT);
        ActionMap actions = guessField.getActionMap();
        actions.put(TEXT_SUBMIT, new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (drawing){
                    guess(guessField.getText().trim());
                    guessField.setText("");
                }
                else{
                    sendText();
                }
            }
        });

        playerList = new JTextArea();
        playerList.setLineWrap(false);
        playerList.setEditable(false);
        playerList.setSize(120, 300);
        playerList.setFont(new Font(Font.SERIF, Font.PLAIN, 18));

        title = BorderFactory.createTitledBorder("");
        drawer.setBorder(title);
        drawer.setSize(600, 600);
        drawer.setEnabled(false);

        clearBtn.addActionListener(new ClearButtonListener());
        clearBtn.setSize(100, 25);
        clearBtn.setEnabled(false);

        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setSize(600, 50);
        label.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);

        panel.add(drawer);
        drawer.setLocation(200, 100);
        panel.add(textAreaFrame);
        textAreaFrame.setLocation(850, 100);
        panel.add(playerList);
        playerList.setLocation(30, 100);
        panel.add(clearBtn);
        clearBtn.setLocation(200, 700);
        panel.add(label);
        label.setLocation(200, 40);
        panel.add(buttonFrame);
        buttonFrame.setLocation(0, 0);

        panel.setLayer(image, 0);
        panel.add(image);


        this.add(panel);
        guessField.setText("Write your makeGuess here");
        guessField.setEnabled(false);

        guessField.requestFocusInWindow();
    }

    private class ImagePanel extends JPanel {

        private Image img;

        ImagePanel(Image img) {
            this.img = img;
            Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }

    }

    private class CloseWindowListener extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            client.disconnect();
        }
    }

    private class ConnectButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            if (!connected)
            {
                try
                {
                    client.connect(GUI.this);
                    GUI.this.setTitle("Connected as \"" + name + "\"");
                    guessField.setText("");
                    guessField.setEnabled(true);
                    guessField.requestFocusInWindow();
                    disconnectOrConnectBtn.setText("Disconnect");
                    connected = true;
                    client.join();
                }
                catch (IOException e)
                {
                    JOptionPane
                            .showMessageDialog(GUI.this,
                                    "Could not establish connection to server, the program will exit");
                    GUI.this
                            .processWindowEvent(new WindowEvent(
                                    GUI.this,
                                    WindowEvent.WINDOW_CLOSING));
                }
            }
            else
            {
                connected = false;
                client.disconnect();
                GUI.this.disconnect();
                GUI.this.processWindowEvent(new WindowEvent(GUI.this, WindowEvent.WINDOW_CLOSING));
            }
        }
    }

    private class ClearButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            drawer.addPointAndSend(null);
            drawer.clear();
        }
    }

    private class Drawer extends JPanel
    {
        private HashSet<Point> hs = new HashSet<>();

        private final Object obj = new Object();

        private boolean enabled;

        Drawer()
        {
            setBackground(Color.WHITE);
            addMouseListener(new L1());
            addMouseMotionListener(new L2());
        }

        @Override
        public void paintComponent(Graphics g)
        {
            g.setColor(Color.WHITE);
            super.paintComponent(g);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.BLACK);
            synchronized (obj) {
                for (Point p : hs) {
                    g.fillOval(p.x, p.y, 4, 4);
                }
            }
        }

        void clear()
        {
            synchronized (obj)
            {
                hs = new HashSet<>();
                this.repaint();
            }
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        void addPoint(Point point)
        {
            synchronized (obj)
            {
                hs.add(point);
            }
            repaint();
        }

        void addPointAndSend(Point point)
        {
            synchronized (obj)
            {
                hs.add(point);
            }
            Point p2 = getLocation();
            client.sendDrawing(point);
            client.sendDrawing(p2);
            repaint();
        }

        class L1 extends MouseAdapter
        {
            @Override
            public void mousePressed(MouseEvent me)
            {
                if (enabled)
                {
                    addPointAndSend(me.getPoint());
                }
            }
        }

        class L2 extends MouseMotionAdapter
        {
            public void mouseDragged(MouseEvent me)
            {
                if (enabled)
                {
                    addPointAndSend(me.getPoint());
                }
            }
        }
    }

    private void whenDisconnected()
    {
        JOptionPane.showMessageDialog(GUI.this,
                "Connection to the server was lost, the program will exit");
        GUI.this.processWindowEvent(new WindowEvent(
                GUI.this, WindowEvent.WINDOW_CLOSING));
        playerNames.remove(name);
    }

    private void disconnect()
    {
        connected = false;
        drawer.setEnabled(false);
        disconnectOrConnectBtn.setText("Connect");
        GUI.this.setTitle("Not connected");
        playerNames.remove(name);
    }

    private void sendText()
    {
        if (guessField.getText().length() == 0 || !connected)
        {
            return;
        }
        client.sendMessage(guessField.getText());
        guessField.setText("");
        guessField.requestFocusInWindow();
    }

    private void guess(String guess)
    {
        client.makeGuess(guess);
    }


    @Override
    public void correctClientMessage(String player, String guess)
    {
        receivedText.append(player + " guessed the word!\n");
    }

    @Override
    public void correctMessage()
    {
        receivedText.append("You guessed the word!\n");
        guessField.setEnabled(false);
        label.setText(word);
    }

    @Override
    public void incorrectClientMessage(String player, String guess)
    {
        receivedText.append(player + ": " + guess + "\n");
    }

    @Override
    public void disconnectClient(String client)
    {
        receivedText.append(client + " left.\n");
        for (String player : playerNames){
            if (client.equals(player))
                playerNames.remove(player);
        }
        list = new StringBuilder();
        String deliminator = "\n";
        for(String player : this.playerNames)
        {
            list.append(player);
            list.append(deliminator);
        }
        list.append(name).append(deliminator);
        playerList.setText("");
        playerList.append(list.toString());
    }

    @Override
    public void connectClient(String client)
    {
        receivedText.append(client + " joined.\n");
        playerNames.add(client);
        list = new StringBuilder();
        String deliminator = "\n";
        for(String player : this.playerNames)
        {
            list.append(player);
            list.append(deliminator);
        }
        list.append(name).append(deliminator);
        playerList.setText("");
        playerList.append(list.toString());
    }

    @Override
    public void serverMessage(String message)
    {
        receivedText.append(message + "\n");
    }

    @Override
    public void clientMessage(String client, String message)
    {
        receivedText.append(client + ": " + message + "\n");
    }

    @Override
    public void connectedToServer()
    {
        playerNames = new CopyOnWriteArrayList<>();
    }

    @Override
    public void joined(List<String> players, int gameStatus)
    {
        this.playerNames.clear();
        this.playerNames.addAll(players);
        list = new StringBuilder();
        String deliminator = "\n";
        for(String player : this.playerNames)
        {
            list.append(player);
            list.append(deliminator);
        }
        list.append(name).append(deliminator);
        playerList.setText("");
        playerList.append(list.toString());
        StringBuilder message = new StringBuilder();
        if (gameStatus != Game.PLAYER_COUNT_TOO_LOW && gameStatus != Game.PLAYER_COUNT_TOO_LOW_AGAIN) {
            message.append("The game has started, wait for your turn.\n");
            guessField.setEnabled(false);
        }
        receivedText.append(message.toString());
        drawing = false;
    }

    @Override
    public void startDraw(String wordToDraw, int drawTime)
    {
        word = wordToDraw;
        guessField.setEnabled(false);
        drawer.clear();
        drawer.setEnabled(true);
        clearBtn.setEnabled(true);
        receivedText.append("The word is \"" + wordToDraw + "\".\n");
        label.setText(word);
    }

    @Override
    public void getDrawing(Point point)
    {
        if (!drawer.enabled)
        {
            if (point != null){
                drawer.addPoint(point);
                //Point p2 = getLocation();
                //drawer.addPoint(p2);
            }
            else{
                drawer.clear();
            }
        }
    }

    @Override
    public void disconnectedFromServer()
    {
        whenDisconnected();
        playerNames.remove(name);
    }

    @Override
    public void stopDraw()
    {
        drawer.setEnabled(false);
        clearBtn.setEnabled(false);
        guessField.setEnabled(true);
        receivedText.append("Drawing ended!\n");
        drawing = false;
    }

    @Override
    public void startGuess(String word)
    {
        this.word = word;
        guessField.setEnabled(true);
        guessField.requestFocusInWindow();
        drawing = true;
        int letters = word.length();
        StringBuilder dashes = new StringBuilder();
        for (int i = 0; i < letters; i++)
            dashes.append(" _ ");
        label.setText(dashes.toString());
    }

    @Override
    public void stopGuess()
    {
        receivedText.append("The guessing turn has ended.\n");
        guessField.setEnabled(true);
        drawing = false;
        label.setText(word);
    }

    @Override
    public String getUsername()
    {
        name = JOptionPane.showInputDialog(this, null, "Please enter a username", JOptionPane.INFORMATION_MESSAGE);
        return name;
    }

    @Override
    public void startClientDraw(String client)
    {
        drawer.clear();
        receivedText.append(client + " is now drawing.\n");
        drawing = true;
    }

    @Override
    public void error(String error)
    {
        receivedText.append("Connection closed by error:\n" + error + "\n");
        playerNames.remove(name);
        disconnect();
    }


    public static void main(String[] args)
    {
        Client client;
        String hostname;
        int port = 3000;
        hostname = JOptionPane.showInputDialog(null, "Enter IP", "IP", JOptionPane.QUESTION_MESSAGE);
        try {
            port = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter port", "Port #", JOptionPane.QUESTION_MESSAGE));
        }
        catch (Exception ignored) { }
        try
        {
            client = new Client(hostname, port);
        }
        catch (Exception e)
        {
            System.err.println("Could not start main.java.client:");
            e.printStackTrace();
            return;
        }
        final GUI gui = new GUI(client);

        EventQueue.invokeLater(() -> gui.setVisible(true));
    }
}
