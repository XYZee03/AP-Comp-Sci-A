import java.util.Scanner;
/**
 * PasswordEnhancer.java  
 *
 * @author: APCS A
 * Assignment #: 1.3
 * 
 * Brief Program Description:
 * This program will strengthen any String password provided
 * by the user by using the strategy of converting every vowel 
 * to a corresponding special character.
 * The program then displays the improved password in the terminal
 * window.
 * 
 * The program enhances Strings (passwords) it receives until the
 * user inputs "-999", at which point the main() method ends.
 * 
 * The method enhancePassword() does the actual work of strengthening
 * the password.  It makes use of the "helper method" replaceCharacter
 * a total of five times; once for each of the five vowels.
 * 
 * You may ONLY use methods and programming structures that we have talked
 * about this year in class (no arrays, for example).
 */
public class PasswordEnhancer
{
    public static void main(String[] args)
    {
        
    }
    
    /**
     * This method enhances a password passed in as a String parameter
     * by replacing every vowel with a designated special character.
     * The method makes use of the replaceCharacter method a total of 
     * five times; once per vowel.
     * 
     * @param   oldPassword     the password to be strengthened
     * @return  the resultant strengthened password (as a new String)
     */
    public static String enhancePassword(String oldPassword)
    {
        return oldPassword;
    }
    
    /**
     * This method takes a given String and searches it for a given
     * character.  For each instance of the character found in the
     * given String, the String is rebuilt by replacing that character
     * with a given replacement character.  Essentially, this method
     * is written to be called multiple times by the enhancePassword()
     * method.
     * 
     * @param   password                the given String to be processed
     * @param   toBeReplaced            the given single-character String to be replaced
     * @param   replacementCharacter    the single-character String to replace all instances
     *                                  of the String toBeReplaced
     * @return  the resultant slightly-strengthened password
     */
    public static String replaceCharacter
        (String password, String toBeReplaced, String replacementCharacter)
    {
        return password;
    }
}
