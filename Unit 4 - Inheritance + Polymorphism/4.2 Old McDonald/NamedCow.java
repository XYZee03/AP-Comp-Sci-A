
/**
 * Write a description of class NamedCow here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NamedCow extends Cow
{
    private String name;
    public NamedCow(String type, String sound, String n){
        super(type, sound);
        name = n;
    }
    
    public String getName(){
        return name;
    }
}
