
/**
 * Write a description of class AccountRunner here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AccountRunner
{
    public static void main(String[] args){
        UserAccount u1 = new UserAccount("Babbage", "2Bo!2b?Shkspr", "Master");
        
        
        
        u1.login("Babbage", "password123");
        
        
        
        u1.login("Babbage", "2Bo!2b?Shkspr");
        
        
        
        u1.logout();
        
        
        
        UserAccount u2 = new UserAccount("Lovelace", "ttls,hl1derwUr!", "Admin");
        
        u2.login("Lovelace", "ttls,hl1derwUr!");
        
    }
}
