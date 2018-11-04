
/**
 * Write a description of class HardCodingMess here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HardCodingMess
{
    public static void main(String... args){
        Charger c = new Charger();
        Device d = new Device(15, 100, 10);
        
        c.addDevice(d);
        
        System.out.println(d.getCurrentCharge());
        
        c.chargeDevs();
        
        System.out.println(d.getCurrentCharge());
        
        c.chargeDevs();
        
        System.out.println(d.getCurrentCharge());
        
        c.chargeDevs();
        
        System.out.println(d.getCurrentCharge());
        
        c.chargeDevs();
        
        System.out.println(d.getCurrentCharge());
        
        c.chargeDevs();
        
        System.out.println(d.getCurrentCharge());
        
        c.chargeDevs();
        
        System.out.println(d.getCurrentCharge());
        
        c.chargeDevs();
        
        System.out.println(d.getCurrentCharge());
        
        c.chargeDevs();
        
        System.out.println(d.getCurrentCharge());
        
        c.chargeDevs();
        
        System.out.println(d.getCurrentCharge());
        
        System.out.println(c);
    }
}
