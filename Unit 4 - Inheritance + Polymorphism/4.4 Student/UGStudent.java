
/**
 * Write a description of class UGStudent here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UGStudent extends Student
{
    private String major, year;
    private int hours;
    
    public UGStudent(String n, int i, String s, double g, int c, String m){
        super(n, i, s, g);
        hours = c;
        major = m;
    }
    
    public boolean updateStatus(int in){
        hours += in;
        if (hours <= 30)
            year = "Freshman";
        if (hours >= 31 && hours <= 60)
            year = "Sophomore";
        if (hours >= 61 && hours <= 90)
            year = "Junior";
        if (hours >= 91 && hours <= 119)
            year = "Senior";
        if (hours >= 120){
            year = "Graduate";
            return true;
        }
        return false;
    }
    
    public String toString(){
        return super.toString() + "\tMajor: " + major + "\tYear: " + year;
    }
}
