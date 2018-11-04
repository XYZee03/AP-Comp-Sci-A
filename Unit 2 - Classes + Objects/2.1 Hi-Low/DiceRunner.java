import java.util.Scanner;
/**
 * Write a description of class DiceRunner here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DiceRunner
{
    public static void main(String[] args){
        Die die1 = new Die();
        Die die2 = new Die();
        int diecount = 0, loss = 0, win = 0;
        String in = "", out = "";
        Scanner scan = new Scanner(System.in);
        
        diecount = die1.getNum() + die2.getNum();
        System.out.println("The die is showing " + die1.getNum());
        System.out.println("The die is showing " + die2.getNum());
        System.out.println("The number is " + (diecount));
        while (loss<3){
            System.out.println("Enter guess (h/l/s): ");
            in = scan.next();
            while (!((in.equals("h")) || (in.equals("l")) || (in.equals("s")))){
                System.out.println("Enter guess (h/l/s): ");
                in = scan.next();
            }
            die1.roll(); die2.roll();
            
            if (diecount < (die1.getNum() + die2.getNum()))
                out = "h";
            
            if (diecount > (die1.getNum() + die2.getNum()))
                out = "l";
            
            if (diecount == (die1.getNum() + die2.getNum()))
                out = "s";
            
            diecount = die1.getNum() + die2.getNum();
            System.out.println("The die is showing " + die1.getNum());
            System.out.println("The die is showing " + die2.getNum());
            System.out.println("The dice add up to " + diecount);
            if (in.equals(out)){
                System.out.println("Your guess was correct!");
                win += 1;
            }
            else{
                System.out.println("Your guess was incorrect...");
                loss += 1;
            }
            System.out.println("Your score is " + win);
        }
        System.out.println("Gracias para playing");
        System.out.println("Score: " + win);
    }
}
