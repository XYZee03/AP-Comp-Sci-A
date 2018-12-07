
/**
 * Write a description of class Chick here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Chick extends Animal{
    private String myType;
    private String mySound1, mySound2;
    public Chick(String type, String sound){
        super(); //although not required in this case, HIGHLY RECOMMENDED myType = type;
        myType = type;
        mySound1 = sound;
    }
    
    public Chick(String type, String sound1, String sound2){
        super();
        myType = type;
        mySound1 = sound1;
        mySound2 = sound2;
    }
    public String getSound(){
        if (mySound2 != null && Math.random() >= 0.5)
            return mySound2;
        return mySound1;
    }
    public String getType(){
       return myType;
    }
}
