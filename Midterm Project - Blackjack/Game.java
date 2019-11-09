import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

@SuppressWarnings("WeakerAccess")
public class Game implements KeyListener {
    private static JTextArea textArea = new JTextArea();
    private static JFrame frame = new JFrame("Blackjack");
    private static String temp, lastText;
    private static volatile boolean press, waitPress;
    private final static Object obj = new Object();


    public void init(){
        textArea.addKeyListener(this);
    }

    public Game(){
        init();
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (waitPress && key == KeyEvent.VK_ENTER) {
            String text=textArea.getText();

            String[] split = text.split("\\n");

            temp = split[split.length-1].strip();

            if (temp.equals(lastText.strip()))
                return;

            press = true;

            synchronized (obj){
                obj.notify();
            }
        }
    }


    @SuppressWarnings("unchecked")
    public static <T> T scanner(Class<T> c) throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        synchronized (obj) {
            waitPress=true;
            while (!press)
                obj.wait();
                reset();
                if (c == String.class)
                    return (T) temp;
                else
                    return (T) c.getMethod("valueOf", String.class).invoke(null, temp);
        }
    }

    public static void reset(){
        press = waitPress = false;
    }

    public static void println(String s){
        System.out.println(s);
        lastText = s;

    }

    public static void createAndShowGUI(){
        Game g = new Game();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800,600);

        TextAreaOutputStream taos = new TextAreaOutputStream(textArea, 60);


        PrintStream ps = new PrintStream(taos);
        System.setOut(ps);
        System.setErr(ps);

        frame.setResizable(false);

        frame.add(new JScrollPane(textArea));
    }

    public static void main(String[] args) throws InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        createAndShowGUI();
        Shoe shoe = new Shoe(4);
        Dealer d = new Dealer(shoe);
        println("What's your name? ");
        String name = scanner(String.class);
        println("Starting amount: ");
        int start = scanner(Integer.class);
        Player p = new Player(start, name, shoe);
        Thread.sleep(1000);
        textArea.setText(null);
        
        while (p.getBalance() >= 10){
            System.out.println("Balance: " + p.getBalance());
            println("Bet amount: ");
            int bet = scanner(Integer.class);
            int inBet = 0;
            while (bet <= 0 || bet%10 != 0){
                println("\nError, bet an amount divisible by ten: ");
                bet = scanner(Integer.class);
            }
            while (bet > p.getBalance()){
                println("\nError, bet an amount lower or equal than balance: ");
                bet = scanner(Integer.class);
            }
            p.betMoney(bet);

            p.hit();
            d.getHand().addCard(d.deal());
            p.hit();
            d.getHand().addCard(d.deal());
            Thread.sleep(700);
            System.out.println("Dealer's up-card: " + d.getHand().getCard(1));
            Thread.sleep(700);
            System.out.println(p);
            Thread.sleep(300);
            System.out.println("Current value: " + p.getHand().getValue());
            Thread.sleep(700);
            boolean bust = false; boolean dBJ = false; boolean pBJ = false; boolean charlie = false; boolean surrender = false; boolean splitted = false; boolean splitStand = false;
            int pVal = 0;
            if (p.getHand().getValue() == 21){
                pBJ = true;
            }
            if (d.getHand().getCard(1).getCardValue() >= 10){
                System.out.print("Dealer checks for blackjack");
                Thread.sleep(500);
                System.out.print(".");
                Thread.sleep(500);
                System.out.print(".");
                Thread.sleep(500);
                System.out.print(".");
                Thread.sleep(500);
                if (!pBJ){
                    println("\nSurrender (y/n)? ");
                    String surr = scanner(String.class);
                    if (surr.contains("y")){
                        surrender = true;
                    }
                    if (surr.contains("n")){
                        surrender = false;
                    }
                }
                if (!pBJ && !surrender){
                    println("\nInsurance bet: ");
                    inBet = scanner(Integer.class);
                    while (inBet > bet/2 || inBet < 0){
                        println("\nError, bet an amount less than or equal to half your original bet: ");
                        inBet = scanner(Integer.class);
                    }
                    p.betMoney(inBet);
                }
                if (d.getHand().getValue() == 21){
                    dBJ = true;
                }
            }
            if (!dBJ && !surrender && p.getHand().getCard(0).getRank().equals(p.getHand().getCard(1).getRank())){
                println("\nSplit (y/n)? ");
                String split = scanner(String.class);
                if (split.contains("y")){
                    splitted = true;
                    p.betMoney(bet);
                    Hand splitHand = new Hand(p.getHand().getHand().remove(0));
                    System.out.println(p.toString(splitHand));
                    Thread.sleep(300);
                    System.out.print("Current value: " + splitHand.getValue());
                    while (!bust && !charlie){
                        println("\nWhat's your move (h/s/dd)? ");
                        String init = scanner(String.class);
                        if (init.equals("h")){
                            p.hit(splitHand);
                            bust = splitHand.isBust();
                        }
                        if (init.equals("s")){
                            break;
                        }
                        if (init.equals("dd")) {
                            if (p.getBalance()-bet >= 0){
                                p.doubleDown(bet, splitHand);
                                bust = splitHand.isBust();
                                System.out.println(p.toString(splitHand));
                                Thread.sleep(300);
                                System.out.println("Current value: " + splitHand.getValue());
                                bet *= 2;
                                break;
                            }
                        }
                        System.out.println(p.toString(splitHand));
                        Thread.sleep(300);
                        System.out.print("Current value: " + splitHand.getValue());
                        if (!bust && splitHand.getHand().size() == 5){
                            charlie = true;
                        }
                    }
                    System.out.println();
                    Thread.sleep(500);
                    if (bust){
                        System.out.println("You busted...");
                    }

                    else if(charlie){
                        System.out.println("5 card charlie!");
                        p.addWin(bet*2);
                    }
                    else{
                        splitStand = true;
                        pVal = splitHand.getValue();
                    }
                    charlie = false; bust = false;
                    Thread.sleep(1000);
                }
                if (splitted) {System.out.println(p.toStringS());}
                else {System.out.println(p);}
                Thread.sleep(300);
                System.out.print("Current value: " + p.getHand().getValue());
            }
            while (!bust && !dBJ && !pBJ && !charlie && !surrender){
                println("\nWhat's your move (h/s/dd)? ");
                String init = scanner(String.class);
                if (init.equals("h")){
                    p.hit();
                    bust = p.getHand().isBust();
                }
                if (init.equals("s")){
                    break;
                }
                if (init.equals("dd")){
                    if (p.getBalance()-bet >= 0){
                        p.doubleDown(bet);
                        bust = p.getHand().isBust();
                        if (splitted){System.out.println(p.toStringS());}
                        else{System.out.println(p);}
                        Thread.sleep(300);
                        System.out.println("Current value: " + p.getHand().getValue());
                        bet *= 2;
                        break;
                    }
                }
                if (splitted){System.out.println(p.toStringS());}
                else{System.out.println(p);}
                Thread.sleep(300);
                System.out.print("Current value: " + p.getHand().getValue());
                if (!bust && p.getHand().getHand().size() == 5){
                    charlie = true;
                }
            }
            System.out.println();
            Thread.sleep(500);
            if (bust){
                System.out.println("You busted...");
            }
            else if(charlie){
                System.out.println("5 card charlie!");
                p.addWin(bet*2);
            }
            else if (splitted){
                int dVal = d.dealerMove();
                if (splitStand){
                    if (dVal > 21 || (dVal < pVal)){
                        System.out.println("You win the new hand!");
                        p.addWin(bet*2);
                    }
                    else if (dVal > pVal){
                        System.out.println("You lost the new hand...");
                    }
                    else if (dVal == pVal){
                        System.out.println("The new hand is a push.");
                        p.addWin(bet);
                    }
                }
                if (dVal > 21 || (dVal < p.getHand().getValue())){
                    System.out.println("You win the old hand!");
                    p.addWin(bet*2);
                }
                else if (dVal > p.getHand().getValue()){
                    System.out.println("You lost the old hand...");
                }
                else if (dVal == p.getHand().getValue()){
                    System.out.println("The old hand is a push.");
                    p.addWin(bet);
                }
            }
            else if (!dBJ && !pBJ && !surrender){
                int dVal = d.dealerMove();
                if (dVal > 21 || (dVal < p.getHand().getValue())){
                    System.out.println("You win!");
                    p.addWin(bet*2);
                }
                else if (dVal > p.getHand().getValue()){
                    System.out.println("You lost...");
                }
                else if (dVal == p.getHand().getValue()){
                    System.out.println("It's a push.");
                    p.addWin(bet);
                }
            }
            else{
                Thread.sleep(200);
                if (surrender){
                    System.out.println("Hand surrendered.");
                    p.addWin(bet/2);
                }
                else if (dBJ && !pBJ){
                    System.out.println("Dealer has blackjack :`(");
                    if (inBet != 0){
                        System.out.println("Insurance pays out.");
                        p.addWin(3*inBet);
                    }
                }
                else if (!dBJ){
                    System.out.println(name + " has blackjack!! :)");
                    p.addWin((int) (2.5*bet));
                }
                else{
                    System.out.println("Both have blackjack.");
                    p.addWin(bet);
                }
            }
            Thread.sleep(3000);
            textArea.setText(null);
            p.reset();
            d.reset();
            if (shoe.getNumCardsInShoe() < 75){shoe.shuffleShoe();}
        }
        System.out.print("Game Over");
        Thread.sleep(5000);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}