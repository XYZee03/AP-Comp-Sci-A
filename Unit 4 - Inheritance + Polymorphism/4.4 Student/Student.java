
/**
 * Write a description of class Student here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Student
{
    private String name, school;
    private int id;
    private double gpa;
    
    public Student(String n, int i, String s, double g){
        name = n;
        id = i;
        school = s;
        gpa = g;
        updateStatus(0);
    }
    
    public String getSchoolName(){
        return school;
    }
    
    public double getGPA(){
        return gpa;
    }
    
    public abstract boolean updateStatus(int in);
    
    public String toString(){
        return "Name: " + name + "\tID: " + id + "\tGPA: " + gpa;
    }
}
