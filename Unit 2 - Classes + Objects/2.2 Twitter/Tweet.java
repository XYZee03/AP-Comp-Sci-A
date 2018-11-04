/**
 * Write a description of class Tweet here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Tweet
{
    private String tweet, handle;
    public Tweet(String b, String h){
        if (b.length() > 140)
            tweet = b.substring(0, 140);
        else
            tweet = b;
        handle = "@" + h;
    }
    
    public String getBody(){
        return tweet;
    }
    
    public String getHandle(){
        return handle;
    }
    
    public String getAllRecipients(){
        String ret = "";
        String handleStart = "@";
        int i = 0;
        while (i < tweet.length()){
            if (tweet.substring(i, i + 1).equals(handleStart)){
                while (i < tweet.length() && !tweet.substring(i, i + 1).equals(" ")){
                    ret += tweet.substring(i, i + 1);
                    i += 1;
                }
                ret += "\n";
            }
            else
                i += 1;
        }
        return ret;
    }
    
    public String getAllHashtags(){
        String ret = "";
        String hashtagStart = "#";
        int i = 0;
        while (i < tweet.length()){
            if (tweet.substring(i, i + 1).equals(hashtagStart)){
                while (i < tweet.length() && !tweet.substring(i, i + 1).equals(" ")){
                    ret += tweet.substring(i, i + 1);
                    i += 1;
                }
                ret += "\n";
            }
            else
                i += 1;
        }
        return ret;
    }
    
    public String toString(){
        return (handle + ": " + tweet);
    }
}
