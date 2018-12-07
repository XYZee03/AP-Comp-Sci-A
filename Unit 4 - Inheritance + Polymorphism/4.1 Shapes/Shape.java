
/**
 * Write a description of class Shape here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Shape
{
    private String name, color;
    
    public Shape(String n, String c){
        name = n;
        color = c;
    }
    
    public void setName(String n){
        name = n;
    }
    
    public void setColor(String c){
        color = c;
    }
    
    public String getName(){
        return name;
    }
    
    public String getColor(){
        return color;
    }
    
    public String toString(){
        return "This shape is named " + name + " and is the color " + color + ".";
    }
}
