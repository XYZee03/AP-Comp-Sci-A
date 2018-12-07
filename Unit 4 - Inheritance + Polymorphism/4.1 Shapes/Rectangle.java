
/**
 * Write a description of class Rectangle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rectangle extends Shape
{
    private double width, height;
    
    public Rectangle(String n, String c, double w, double h){
        super(n, c);
        width = w;
        height = h;
    }
    
    public double getWidth(){
        return width;
    }
    
    public double getHeight(){
        return height;
    }
    
    public double area(){
        return width * height;
    }
    
    public double perimeter(){
        return (2 * width) + (2 * height);
    }
    public String toString(){
        if (width != height)
            return super.toString() + "\nIt is a rectangle with area " + area() + " and perimeter " + perimeter() + ".";
        else
            return super.toString() + "\nIt is a square with area " + area() + " and perimeter " + perimeter() + ".";
    }
}
