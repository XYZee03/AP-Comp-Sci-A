import java.io.*;
import java.util.*;
/**
 * Write a description of class Life here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Life{
    private String[][] game;
    private int[][] stored;
    private String[][] readInput(String fileName) throws IOException{
        Scanner in = new Scanner(new File(fileName)); 
        List<String[]> lines = new ArrayList<>();
        int size = in.nextInt();
        while(in.hasNextLine()) {
            String line = in.nextLine().trim();
            String[] splitted = line.split(" ");
            lines.add(splitted);
        }
        lines.remove(0);

        game = new String[size][size];
        stored = new int[size][size];
        for(int i = 0; i<game.length; i++) {
            game[i] = lines.get(i);
        }
        return game;
    }

    public String[][] getGame(){
        return game;
    }

    public Life(String file) throws IOException{
        game = readInput(file);
    }

    private boolean topRight(int r, int c){
        return !(r == 0 || c == game.length-1);
    }

    private boolean topMid(int r, int c){
        return !(r == 0);
    }

    private boolean topLeft(int r, int c){
        return !(c == 0 || r == 0);
    }

    private boolean midLeft(int r, int c){
        return !(c == 0);
    }

    private boolean midRight(int r, int c){
        return !(c == game.length-1);
    }

    private boolean bottomLeft(int r, int c){
        return !(c == 0 || r == game.length-1);
    }

    private boolean bottomMid(int r, int c){
        return !(r == game.length-1);
    }

    private boolean bottomRight(int r, int c){
        return !(r == game.length-1 || c == game.length-1);
    }

    public void around(int r, int c){
        int count = 0;

        if (topLeft(r, c))
            if (game[r-1][c-1].equals("■"))
                count++;

        if (topMid(r, c))
            if (game[r-1][c].equals("■"))
                count++;

        if (topRight(r, c))
            if (game[r-1][c+1].equals("■"))
                count++;

        if (midLeft(r, c))
            if (game[r][c-1].equals("■"))
                count++;

        if (midRight(r, c))
            if (game[r][c+1].equals("■"))
                count++;

        if (bottomLeft(r, c))
            if (game[r+1][c-1].equals("■"))
                count++;

        if (bottomMid(r, c))
            if (game[r+1][c].equals("■"))
                count++;

        if (bottomRight(r, c))
            if (game[r+1][c+1].equals("■"))
                count++;

        stored[r][c] = count;
    }

    public void check(int r, int c){
        switch(stored[r][c]){
            case(0):
                game[r][c] = "□";
            case(1):
                game[r][c] = "□";
                break;
            case(2):
                game[r][c] = game[r][c];
                break;
            case(3):
                game[r][c] = "■";
                break;
            case(4):
                game[r][c] = "□";
            case(5):
                game[r][c] = "□";
            case(6):
                game[r][c] = "□";
            case(7):
                game[r][c] = "□";
            case(8):
                game[r][c] = "□";
                break;
        }
    }
}
