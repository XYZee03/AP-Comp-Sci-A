import java.util.Scanner;
/**
 * Write a description of class Twitter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Twitter
{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String handle = sc.nextLine();
        System.out.print("\n");
        System.out.print("Enter tweet: ");
        String tweet = sc.nextLine();
        
        Tweet t1 = new Tweet(tweet, handle);
        
        System.out.println(t1);
    }
}
