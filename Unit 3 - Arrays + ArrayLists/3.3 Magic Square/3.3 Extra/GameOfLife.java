import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameOfLife extends JFrame implements ActionListener {
    public void actionPerformed(ActionEvent ae){}

    public static void main(String[] args) throws IOException, InterruptedException{
        Life l1 = new Life("Pente.txt");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setBackground(Color.WHITE);
        frame.setTitle("Conway's Game Of Life");
        frame.setVisible(true);
        while(true){
            Graphics g = frame.getGraphics();
            Graphics(l1.getGame(), g);
            for (int i = 0; i < l1.getGame().length; i++)
                for (int j = 0; j < l1.getGame().length; j++)
                    l1.around(i, j);
            for (int i = 0; i < l1.getGame().length; i++)
                for (int j = 0; j < l1.getGame().length; j++)
                    l1.check(i, j);

            Thread.sleep(500);
        }
    }

    private static void Graphics(String[][] array, Graphics g) {
        int BOX_DIM = 25;
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[0].length; j++){
                g.setColor(array[i][j].equals("□") ? Color.WHITE : Color.BLACK);
                g.fillRect(j * BOX_DIM, i * BOX_DIM, BOX_DIM, BOX_DIM);
                g.setColor(Color.GRAY);
                g.drawRect(j * BOX_DIM, i * BOX_DIM, BOX_DIM, BOX_DIM);
            }
    }
}
