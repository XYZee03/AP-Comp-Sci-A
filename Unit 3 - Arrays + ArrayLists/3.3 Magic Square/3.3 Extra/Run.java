import java.io.*;
/**
 * Write a description of class Run here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Run
{
    public static void main(String... args) throws InterruptedException, IOException{
        Life l = new Life("Pente.txt");
        while(true){
            for (String[] i: l.getGame()){
                for (String j: i){
                    System.out.print(j);
                }
                System.out.println();
            }
            for (int i = 0; i < l.getGame().length; i++){
                for (int j = 0; j < l.getGame().length; j++)
                    l.around(i, j);
            }
            for (int i = 0; i < l.getGame().length; i++){
                for (int j = 0; j < l.getGame().length; j++)
                    l.check(i, j);
            }
            Thread.sleep(500);
            System.out.println('\u000C');
        }
    }
}
