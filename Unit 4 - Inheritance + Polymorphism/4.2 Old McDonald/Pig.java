
/**
 * Write a description of class Pig here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Pig extends Animal{
    private String myType;
    private String mySound;
    public Pig(String type, String sound){
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
