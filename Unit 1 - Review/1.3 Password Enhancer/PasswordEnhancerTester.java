

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PasswordEnhancerTester.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PasswordEnhancerTester
{
    /**
     * Default constructor for test class PasswordEnhancerTester
     */
    public PasswordEnhancerTester()
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
    public void testOne()
    {
        assertEquals("f@c3t!0^s", PasswordEnhancer.enhancePassword("facetious"));
    }

    @Test
    public void testTwo()
    {
        assertEquals("pry", PasswordEnhancer.enhancePassword("pry"));
    }

    @Test
    public void testThree()
    {
        assertEquals("", PasswordEnhancer.enhancePassword(""));
    }

    @Test
    public void testFour()
    {
        assertEquals("b!0f33db@ck", PasswordEnhancer.enhancePassword("biofeedback"));
    }

    @Test
    public void testFive()
    {
        assertEquals("@l!3n c@r!b0^", PasswordEnhancer.enhancePassword("alien caribou"));
    }

    @Test
    public void testSix()
    {
        assertEquals("@@@@@", PasswordEnhancer.enhancePassword("aaaaa"));
    }

    @Test
    public void testSeven()
    {
        assertEquals("@3!0^", PasswordEnhancer.enhancePassword("@3!0^"));
    }

    @Test
    public void testEight()
    {
        assertEquals("b3@^t!f^l!gl00", PasswordEnhancer.enhancePassword("beautifuligloo"));
    }
}








