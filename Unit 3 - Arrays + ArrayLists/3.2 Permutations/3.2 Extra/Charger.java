import java.util.*;
/**
 * Write a description of class Charger here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Charger
{
    private int numConnected;
    private List<Device> devices = new ArrayList<>();

    public Charger(){
        numConnected = 0;
    }

    public int getNumConnected(){
        return numConnected;
    }

    public boolean addDevice(Device d){
        if (d.getCurrentCharge() == d.getMaxCharge())
            return false;
        devices.add(d);
        numConnected += 1;
        return true;
    }

    public void chargeDevs(){
        devices.forEach(Device::charge);
        //for (Device i: devices){
        //if (i.getCurrentCharge() == i.getMaxCharge())
        //devices.remove(devices.indexOf(i));
        //}
        Iterator<Device> i = devices.iterator();
        while (i.hasNext()) {
            Device s = i.next();
            if (s.getCurrentCharge() == s.getMaxCharge())
                i.remove();
        }
    }
    
    public String toString(){
        return "" + devices;
    }
}
