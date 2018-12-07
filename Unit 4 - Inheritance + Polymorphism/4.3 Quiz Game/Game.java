
/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Game
{
    public static void main (String[] args)
    {
        //create a new Trivia object
        Trivia t = new Trivia();
        // Create 3 Question objects. You should use polymorphism to create
        // one Question, one MCQuestion and one TFQuestion.
        Question q1 = new MCQuestion("What is the opposite of up?", "A", "down", "left", "Rohan", "shellshock.io");
        Question q2 = new TFQuestion("Apple is owned by the Zucc.", "false");
        Question q3 = new Question("Who is the president?", "Donald Trump");
        // add your questions to the list (in the Trivia object)
        t.addQuestion(q1);
        t.addQuestion(q2);
        t.addQuestion(q3);
        // play the game
        t.play();
        //use downcasting to call the setOptions method for your MCQuestion
        ((MCQuestion)q1).setOptions("down","t̵̞̖̲͗͌h̶͎̻̮̫̙͚̋͗̅̔̕͝͝ ","aaaaaaaaa","banana");
        //use downcasting to call the setAnswer method for your TFQuestion
        // set the answer to something OTHER THAN "true" or "false". Print
        // out the answer to the TFQuestion and make sure it did NOT
        // change
        ((TFQuestion)q2).setAnswer("maybe idk");
        /** System.out.println(q2.getAnswer()); **/
        // again, use downcasing to call the setAnswer method of the TFQuestion
        // This time change the answer to the opposite value as originally used
        // above.  
        ((TFQuestion)q2).setAnswer("true");
        //play the game again and confirm both the TFQuestion and MCQuestion
        //objects updated correctly.
        t.play();
        
        
    }
}
