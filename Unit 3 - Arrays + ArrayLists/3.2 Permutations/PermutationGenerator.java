import java.util.ArrayList;
/**
 * Write a description of class Producer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PermutationGenerator
{
    public static void main(String[] args){
        for (int i = 1; i < 11; i++)
            System.out.println("List " + i + " " + nextPermutation());
    }
    
    public static ArrayList<Integer> nextPermutation(){
        ArrayList<Integer> a1 = new ArrayList<Integer>();
        for (int i = 1; i < 11; i++)
            a1.add(i);
        ArrayList<Integer> a2 = new ArrayList<Integer>();
        for (int i = 1; i < 11; i++)
            a2.add(a1.remove((int) (Math.random() * a1.size())));
        return a2;
    }
}