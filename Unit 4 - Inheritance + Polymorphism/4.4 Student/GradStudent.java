
/**
 * Write a description of class GradStudent here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GradStudent extends Student
{
    private String degree, status;
    private int hours, pages;
    
    public GradStudent(String n, int i, String s, double g, int c, int p, String d){
        super(n, i, s, g);
        hours = c;
        pages = p;
        degree = d;
    }
    
    public boolean updateStatus(int in){
        if (hours < 60){
            hours += in;
        }
        else
            pages += in;
        if (hours < 60)
            status = "Planning";
        if (hours >= 60 && pages < 100)
            status = "ABD - Writing";
        if (hours >= 60 && pages >= 100 && pages <= 249)
            status = "ABD - Revising";
        if (hours >= 60 && pages >= 250){
            status = "PhD Earned";
            return true;
        }
        return false;
    }
    
    public String toString(){
        return super.toString() + "\tStatus: " + status;
    }
}
