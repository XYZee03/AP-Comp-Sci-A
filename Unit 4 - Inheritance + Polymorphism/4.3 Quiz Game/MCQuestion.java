
/**
 * Write a description of class MCQuestion here:
 *
 * @author (your name)
 * @version (a version number or a date)
 */

//MCQuestion is-a Question
public class MCQuestion extends Question
{
    //This type of question requires 4 answer options in addition
    // to the standard question and answer variables:
    private String ans1, ans2, ans3, ans4;
    
    /** constructor */
    public MCQuestion(String q, String a, String a1, String a2, String a3, String a4){
        super(q, a);
        ans1 = a1;
        ans2 = a2;
        ans3 = a3;
        ans4 = a4;
    }
    
    /**
     * Name: setOptions
     * @param String a: answer option A
     * @param String b: answer option B
     * @param String c: answer option C
     * @param String d: answer option D
     * @ return: none
     * Operation: update the answer options for this question
     */
    public void setOptions(String a, String b, String c, String d){
        ans1 = a;
        ans2 = b;
        ans3 = c;
        ans4 = d;
    }
    
    /**
     * Name: getQuestion
     * @param: none
     * @return: String representing the question plus
     * the answer options appended to the end as shown here:
     *    Question Text
     *    A: choice A
     *    B: choice B
     *    C: choice C
     *    D: choice D
     *    Enter Choice: 
     */
    public String getQuestion(){
        return super.getQuestion() + "\nA: " + ans1 + "\nB: " + ans2 + "\nC: " + ans3 + "\nD: " + ans4 + "\nEnter Choice: ";
    }
}
