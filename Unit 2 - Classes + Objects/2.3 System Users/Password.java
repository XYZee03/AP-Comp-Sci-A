
/**
 * Write a description of class Password here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Password
{
    private String pw;
    
    public Password(String p){
        pw = p;
    }
    
    public String toString(){
        return "Password: " + pw;
    }
    
    public String getPassword(){
        return pw;
    }
    
    public void setPassword(String p){
        pw = p;
    }
}
