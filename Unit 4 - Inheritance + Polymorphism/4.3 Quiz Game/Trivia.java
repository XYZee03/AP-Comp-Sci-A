import java.util.ArrayList;
import java.util.Scanner;
/**
 * Write a description of class Trivia here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Trivia
{
    // This class contains an ArrayList of Question objects
    // and an integer for tracking the number of correct
    // answers given.
    private ArrayList<Question> game;
    private int score;
    // Create the constructor for this class.
    public Trivia(){
        game = new ArrayList<>();
    }

    /**
     * Name: addQuestion
     * @param: q - a Question object
     * @return: none
     * Operation: adds the provided question object to 
     * the end of the ArrayList questions.
     */
    public void addQuestion(Question q){
        game.add(q);
    }

    /** 
     * Name: getAnswer
     * @param q: a Question object
     * @return : String representing the user supplied answer.
     * Operation: Prints out the question text from the 
     * given input Question object, takes input from the
     * user (use a Scanner and the .next() method), and 
     * returns the user entered value.
     */
    public String getAnswer(Question q){
        Scanner s = new Scanner(System.in);
        System.out.print(q.getQuestion());
        String ans = s.nextLine();
        return ans;
    }

    /**
     * Name: checkAnswer
     * @param q: a Question object
     * @param a: String - the user's answer to the question
     * @return: boolean - True if the user's answer is 
     *          correct, False otherwise.
     * Operation: compares the user's answer to the answer
     * from the Question object. Returns True if they match
     */
    public boolean checkAnswer(Question q, String a){
        if (q.getAnswer().equals(a))
            return true;
        return false;
    }

    /**
     * Name: Play
     * @param: none
     * @return: none
     * Operation: For each Question, calls getAnswer to 
     * print the question and get user response, then
     * calls checkAnswer to validate user input.
     * Score is updated for correct answers and
     * a correct/incorrect message is printed after each
     * question. Once all questions have been asked, the
     * player's final score is printed.
     */
    public void play(){
        score = 0;
        for (Question q: game){
            String ans = getAnswer(q);
            if (checkAnswer(q, ans)){
                System.out.println("Correct!\n");
                score++;
            }
            else
                System.out.println("Incorrect...\n");
        }
        System.out.println("Final score: " + score);
    }
}
