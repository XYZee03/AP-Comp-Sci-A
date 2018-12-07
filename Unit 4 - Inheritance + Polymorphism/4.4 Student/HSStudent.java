
/**
 * Write a description of class HSStudent here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HSStudent extends Student
{
    private int hours;
    private String year;
    
    public HSStudent(String n, int i, String s, double g, int c){
        super(n, i, s, g);
        hours = c;
    }
    
    public boolean updateStatus(int in){
        hours += in;
        if (hours <= 8)
            year = "Freshman";
        if (hours >= 9 && hours <= 16)
            year = "Sophomore";
        if (hours >= 17 && hours <= 24)
            year = "Junior";
        if (hours >= 25 && hours <= 27)
            year = "Senior";
        if (hours >= 28){
            year = "Graduate";
            return true;
        }
        return false;
    }
    
    public String toString(){
        return super.toString() + "\tYear: " + year;
    }
}
