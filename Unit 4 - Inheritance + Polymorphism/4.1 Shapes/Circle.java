
/**
 * Write a description of class Circle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Circle extends Shape
{
    private double radius;
    
    public Circle(String n, String c, double r){
        super(n, c);
        radius = r;
    }
    
    public double getRadius(){
        return radius;
    }
    
    public double area(){
        return Math.round(100 * Math.PI * (radius * radius)) / 100.0;
    }
    
    public double circumference(){
        return Math.round(100 * Math.PI * 2 * radius) / 100.0;
    }
    
    public String toString(){
        return super.toString() + "\nIt is a circle with area " + area() + " and circumference " + circumference() + ".";
    }
}
