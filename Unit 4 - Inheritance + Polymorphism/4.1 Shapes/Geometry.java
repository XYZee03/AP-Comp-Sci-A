
/**
 * Write a description of class Geometry here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Geometry
{
    public static void main(String... args){
        Rectangle r = new Rectangle("R1", "blue", 20, 30);
        Circle c = new Circle("C1", "red", 15);
        
        System.out.println(r);
        System.out.println("\n");
        System.out.println(c);
        
        System.out.println("\n");
        
        System.out.println("The width of rectangle " + r.getName() + " is " + r.getWidth() + " and the height is " + r.getHeight() + ".");
        
        System.out.println("\n");
        
        c.setName("Sir Cumference");
        System.out.println(c);
        
        System.out.println("\n");
        
        Square s = new Square("Huey", "green", 5, 5);
        System.out.println(s);
    }
}
