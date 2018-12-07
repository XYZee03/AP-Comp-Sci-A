

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TriviaTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TriviaTest
{
    Question question1, question2, question3;
    Trivia t;
    /**
     * Default constructor for test class TriviaTest
     */
    public TriviaTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
     question3 = new TFQuestion("Green?","false");
     question2 = new MCQuestion("How fast?","B", "17", "55", "99","165");
     question1 = new Question("How many?", "5");
     t = new Trivia();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void CheckGetQuestion()
    {
        assertEquals("How many?", question1.getQuestion());
        assertEquals("How fast?\nA: 17\nB: 55\nC: 99\nD: 165\nEnter Choice: ", question2.getQuestion());
        assertEquals("Green? (true or false): ", question3.getQuestion());
    }
    
    @Test
    public void CheckGetAnswer()
    {
        assertEquals("5", question1.getAnswer());   
        assertEquals("B", question2.getAnswer());
        assertEquals("false", question3.getAnswer());
    }
    
    @Test
    public void CheckSetQuestion()
    {
        question1.setQuestion("who?");
        question2.setQuestion("why?");
        question3.setQuestion("where?");
        assertEquals("who?", question1.getQuestion());
        assertEquals("why?\nA: 17\nB: 55\nC: 99\nD: 165\nEnter Choice: ", question2.getQuestion());
        assertEquals("where? (true or false): ", question3.getQuestion());
    }
    
    @Test
    public void CheckSetAnswer()
    {
        question1.setAnswer("4");
        question2.setAnswer("A");
        question3.setAnswer("yellow");
        assertEquals("4", question1.getAnswer());   
        assertEquals("A", question2.getAnswer());
        assertEquals("false", question3.getAnswer());
        question3.setAnswer("true");
        assertEquals("true", question3.getAnswer());
    }
    @Test
    public void CheckSetOptions()
    {
        assertEquals("How fast?\nA: 17\nB: 55\nC: 99\nD: 165\nEnter Choice: ", question2.getQuestion());
        ((MCQuestion)question2).setOptions("1", "2", "3", "4");
        assertEquals("How fast?\nA: 1\nB: 2\nC: 3\nD: 4\nEnter Choice: ", question2.getQuestion());
    }
    
    
}
