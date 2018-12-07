
/**
 * Write a description of class OldMacDonald here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class OldMacDonald{
    public static void main(String[] args){
        NamedCow c = new NamedCow("cow", "moo", "Daniel");
        System.out.println(c.getType() + " goes " + c.getSound() );
        
        Pig p = new Pig("pig", "oink");
        System.out.println(p.getType() + " goes " + p.getSound() );
        
        Chick h = new Chick("chicken", "cluck");
        System.out.println(h.getType() + " goes " + h.getSound() );
        
        Farm f = new Farm();
        f.animalSounds();
        System.out.println("The cow is known as " + c.getName());
    }
}