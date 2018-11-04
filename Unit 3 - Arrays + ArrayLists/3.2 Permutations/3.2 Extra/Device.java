
/**
 * Write a description of class Device here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Device
{
    private int currentCharge, maxCharge, rate;
    
    public Device(int c, int m, int r){
        currentCharge = c;
        maxCharge = m;
        rate = r;
    }
    
    public int getCurrentCharge(){
        return currentCharge;
    }
    
    public int getMaxCharge(){
        return maxCharge;
    }
    
    public boolean charge(){
        if (currentCharge < maxCharge){
            currentCharge += rate;
            if (currentCharge >= maxCharge){
                currentCharge = maxCharge;
                return true;
            }
        }
        return false;
    }
}
