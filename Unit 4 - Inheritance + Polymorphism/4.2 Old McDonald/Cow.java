// The children of an abstract parent must contain the abstract methods declared
// because they have been made abstract to share across all common children.
// If they did not contain those methods, the child itself would also have to be abstract.
/**
 * Write a description of class Cow here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Cow extends Animal{
    private String myType;
    private String mySound;
    public Cow(String type, String sound){
        super(); //although not required in this case, HIGHLY RECOMMENDED myType = type;
        myType = type;
        mySound = sound;
    }
    public String getSound(){
        return mySound;
    }
    public String getType(){
       return myType;
    }
}
