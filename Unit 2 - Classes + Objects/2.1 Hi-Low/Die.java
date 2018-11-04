/**
 * Write a description of class Die here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Die
{
    private int num;
    
    public Die(){
        num = ((int) (Math.random() * 6)) + 1;
    }
    
    public void roll(){
        num = ((int) (Math.random() * 6)) + 1;
    }
    
    public int getNum(){
        return num;
    }
}
