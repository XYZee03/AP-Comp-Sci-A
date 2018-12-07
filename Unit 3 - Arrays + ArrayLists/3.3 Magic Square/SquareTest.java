
import java.io.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class SquareTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SquareTest
{
    /**
     * Default constructor for test class SquareTest
     */
    public SquareTest()
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
    public void square1() throws IOException
    {
        Square square1 = new Square("magicData1");
        square1.printSquare();
        int magicNumber = 15;
        for(int i=0;i<square1.size();i++)
        {
          assertEquals(magicNumber, square1.sumCol(i));
          assertEquals(magicNumber, square1.sumRow(i));
        }
        assertEquals(magicNumber, square1.sumMainDiag());
        assertEquals(magicNumber, square1.sumOtherDiag());
        assertEquals(true, square1.magic());
    }
    @Test
    public void square2() throws IOException
    {
        Square square1 = new Square("magicData2");
        square1.printSquare();
        int magicNumber = 175;
        for(int i=0;i<square1.size();i++)
        {
          assertEquals(magicNumber, square1.sumCol(i));
          assertEquals(magicNumber, square1.sumRow(i));
        }
        assertEquals(magicNumber, square1.sumMainDiag());
        assertEquals(magicNumber, square1.sumOtherDiag());
        assertEquals(true, square1.magic());
    }
    @Test
    public void square3() throws IOException
    {
        Square square1 = new Square("magicData3");
        square1.printSquare();
        int magicNumber = 102;
        for(int i=0;i<square1.size();i++)
        {
          assertEquals(magicNumber, square1.sumCol(i));
          assertEquals(magicNumber, square1.sumRow(i));
        }
        assertEquals(magicNumber, square1.sumMainDiag());
        assertEquals(magicNumber, square1.sumOtherDiag());
        assertEquals(true, square1.magic());
    }
    @Test
    public void square4() throws IOException
    {
        Square square1 = new Square("magicData4");
        square1.printSquare();
        assertEquals(9, square1.sumCol(0));
        assertEquals(16, square1.sumCol(1));
        assertEquals(14, square1.sumCol(2));
        assertEquals(15, square1.sumRow(0));
        assertEquals(9, square1.sumRow(1));
        assertEquals(15, square1.sumRow(2));
        assertEquals(15, square1.sumMainDiag());
        assertEquals(14, square1.sumOtherDiag());
        assertEquals(false, square1.magic());
    }
    @Test
    public void square5() throws IOException
    {
        Square square1 = new Square("magicData5");
        square1.printSquare();
        int magicNumber = 34;
        for(int i=0;i<square1.size();i++)
        {
          assertEquals(magicNumber, square1.sumCol(i));
          assertEquals(magicNumber, square1.sumRow(i));
        }
        assertEquals(24, square1.sumMainDiag());
        assertEquals(40, square1.sumOtherDiag());
        assertEquals(false, square1.magic());
    }
    @Test
    public void square6() throws IOException
    {
        Square square1 = new Square("magicData6");
        square1.printSquare();
        int magicNumber = 65;
        for(int i=0;i<square1.size();i++)
        {
          assertEquals(magicNumber, square1.sumRow(i));
        }
        assertEquals(65, square1.sumCol(0));
        assertEquals(65, square1.sumCol(1));
        assertEquals(65, square1.sumCol(2));
        assertEquals(58, square1.sumCol(3));
        assertEquals(72, square1.sumCol(4));
        assertEquals(90, square1.sumMainDiag());
        assertEquals(60, square1.sumOtherDiag());
        assertEquals(false, square1.magic());
    }
    @Test
    public void square7() throws IOException
    {
        Square square1 = new Square("magicData7");
        square1.printSquare();
        int magicNumber = 175;
        for(int i=0;i<square1.size();i++)
        {
          assertEquals(magicNumber, square1.sumCol(i));
          assertEquals(magicNumber, square1.sumRow(i));
        }
        assertEquals(magicNumber, square1.sumMainDiag());
        assertEquals(168, square1.sumOtherDiag());
        assertEquals(false, square1.magic());
    }
    @Test
    public void square8() throws IOException
    {
        Square square1 = new Square("magicData8");
        square1.printSquare();
        assertEquals(11, square1.sumCol(0));
        assertEquals(19, square1.sumCol(1));
        assertEquals(15, square1.sumCol(2));
        assertEquals(15, square1.sumRow(0));
        assertEquals(15, square1.sumRow(1));
        assertEquals(15, square1.sumRow(2));
        assertEquals(10, square1.sumMainDiag());
        assertEquals(10, square1.sumOtherDiag());
        assertEquals(false, square1.magic());
    }
    @Test
    public void square9() throws IOException
    {
        Square square1 = new Square("magicData9");
        square1.printSquare();
        assertEquals(9, square1.sumCol(0));
        assertEquals(10, square1.sumCol(1));
        assertEquals(8, square1.sumCol(2));
        assertEquals(9, square1.sumRow(0));
        assertEquals(10, square1.sumRow(1));
        assertEquals(8, square1.sumRow(2));
        assertEquals(11, square1.sumMainDiag());
        assertEquals(11, square1.sumOtherDiag());
        assertEquals(false, square1.magic());
    }
}

