
/**
 * Write a description of class TFQuestion here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

//TFQuestion is-a Question
public class TFQuestion extends Question
{
    /** constructor */
    public TFQuestion(String q, String a){
        super(q, a);
    }

    /**
     * Name: getQuestion
     * @param: none
     * @return: String representing the question plus
     * the help prompt " (true or false): " appended to the
     * end
     */
    public String getQuestion(){
        return super.getQuestion() + " (true or false): ";
    }
    /**
     * Name: setAnswer
     * @param: String a: the answer to be saved
     * @return: none
     * Operation: if the answer is being changed to either
     * "true" or "false", update the instance variable.
     * Do NOT update the variable if any other answer is
     * provided.
     */
    public void setAnswer(String a){
        if (a.equals("true") || a.equals("false"))
            super.setAnswer(a);
    }
}
