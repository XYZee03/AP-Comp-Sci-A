
/**
 * Write a description of class UserAccount here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UserAccount
{
    private String id, access;
    private boolean logged;
    private Password p1;
    
    public UserAccount(String in, String pw, String acc){
        id = in;
        p1 = new Password(pw);
        if ((acc.equals("Admin")) || (acc.equals("SuperUser")))
            access = acc;
        else
            access = "Standard";
        logged = false;
    }
    
    public boolean login(String in, String pw){
        if ((in.equals(id)) && (pw.equals(p1.getPassword())))
            logged = true;
        else
            logged = false;
        return logged;
    }
    
    public void logout(){
        logged = false;
    }
    
    public String toString(){
        if (logged)
            return "User: " + id + "\nPassword: " + p1.getPassword() + "\nAccessLevel: " + access;
        else
            return "Access Denied";
    }
    
    public boolean setPassword(String newpass){
        if (logged){
            p1.setPassword(newpass);
            return logged;
        }
        else
            return logged;
    }
    
    public boolean setPassword(String userID, String oldpass, String newpass){
        int i = 0;
        boolean num = false;
        if ((userID.equals(id)) && (oldpass.equals(p1.getPassword())))
            logged = true;
        if (!(newpass.length() >= 8) && (logged))
            logged = false;
        while (i < newpass.length() && !(num)){
            if ((((int) (newpass.charAt(i))) > 47) && (((int) (newpass.charAt(i))) < 58)){
                num = true;
            }
            i++;
        }
        if (!num)
            logged = false;
        i = 0;
        boolean capital = false;
        while (i < newpass.length() && !(capital)){
            if ((((int) (newpass.charAt(i))) > 64) && (((int) (newpass.charAt(i))) < 91)){
                capital = true;
            }
            i++;
        }
        if (!capital)
            logged = false;
        i = 0;
        boolean special = false;
        while (i < newpass.length() && !(special)){
            if (
                ((((int) (newpass.charAt(i))) > 32) && (((int) (newpass.charAt(i))) < 48))
                ||
                ((((int) (newpass.charAt(i))) > 57) && (((int) (newpass.charAt(i))) < 65))
                ||
                ((((int) (newpass.charAt(i))) > 90) && (((int) (newpass.charAt(i))) < 97))
                ||
                ((((int) (newpass.charAt(i))) > 122) && (((int) (newpass.charAt(i))) < 127))
                ){
                special = true;
            }
            i++;
        }
        if (!special)
            logged = false;
        if (logged){
            p1.setPassword(newpass);
        }
        return logged;
    }
}
