

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
/**
 * The test class StudentPolymorphTester.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StudentPolymorphTester
{
    ArrayList<Student> students;
    /**
     * Default constructor for test class StudentPolymorphTester
     */
    public StudentPolymorphTester()
    {
        //create an arraylist to hold the students we are following in the study
        students = new ArrayList<Student>();
        
        /** Add the students and their current status */
        //High School Students
        students.add(new HSStudent("Fred Jones", 1111, "Midvale HS", 3.62,25));
        students.add(new HSStudent("Daphne Blake", 2222, "Midvale HS", 3.37,19));
        students.add(new HSStudent("Velma Dinkley", 3333, "Midvale HS", 4.47,12));
        students.add(new HSStudent("Shaggy Rogers", 4444, "Midvale HS", 2.85,6));
        
        //Undergrads
        students.add(new UGStudent("Wade Watts", 5555, "Ludis Comm. Coll.", 
                    2.31, 17, "Computer Science"));
        students.add(new UGStudent("Henry Jones Jr.", 6666, "Arizona State", 
                    3.89, 119, "Archeology"));
        students.add(new UGStudent("Walt Disney", 7777, "Univ. of Florida", 
                    3.47, 45, "Art"));
        students.add(new UGStudent("Albert Einstein", 8888, "Mass. Inst. of Tech.", 
                    3.99, 76, "Physics"));
                    
        //Grads 
        students.add(new GradStudent("Mortimer Snerd", 1313, "Whatsamatta U.", 
                    1.76, 56, 0, "Forestry"));
        students.add(new GradStudent("Barack Obama", 9999, "Univ. of Illinois", 
                    3.55, 35, 0, "Political Science"));
        students.add(new GradStudent("Marie Curie", 1010, "Oxford Univeristy", 
                    3.88, 62, 0, "Chemistry"));
        students.add(new GradStudent("Alexander Hamilton", 1212, "Harvard", 
                    3.41, 76, 233, "Economics"));
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
    @Test
    public void HStoString() 
    {
        //make sure all values are present
        assertEquals(students.get(3).toString().contains("Shaggy Rogers"),true);
        assertEquals(students.get(3).toString().contains("4444"),true);
        assertEquals(students.get(3).toString().contains("2.85"),true);
        assertEquals(students.get(3).toString().contains("Freshman"),true);
    }
    @Test
    public void UGtoString() 
    {
        //make sure all values are present
        assertEquals(students.get(4).toString().contains("Wade Watts"),true);
        assertEquals(students.get(4).toString().contains("5555"),true);
        assertEquals(students.get(4).toString().contains("2.31"),true);
        assertEquals(students.get(4).toString().contains("Freshman"),true);
        assertEquals(students.get(4).toString().contains("Computer Science"),true);
    }
    @Test
    public void GStoString() 
    {
        //make sure all values are present
        assertEquals(students.get(8).toString().contains("Mortimer Snerd"),true);
        assertEquals(students.get(8).toString().contains("1313"),true);
        assertEquals(students.get(8).toString().contains("1.76"),true);
        assertEquals(students.get(8).toString().contains("Planning"),true);
    }
    @Test
    public void getSchool() 
    {
        //make sure all values are present
        assertEquals(students.get(3).getSchoolName().contains("Midvale HS"),true);
        assertEquals(students.get(4).getSchoolName().contains("Ludis Comm. Coll."),true);
        assertEquals(students.get(8).getSchoolName().contains("Whatsamatta U."),true);
    }
    @Test
    public void getGPA() 
    {
        //make sure all values are present
        assertEquals(students.get(3).getGPA()==2.85,true);
        assertEquals(students.get(4).getGPA()==2.31,true);
        assertEquals(students.get(8).getGPA()==1.76,true);
    }
    @Test
    public void HSUpdate() 
    {
        //starting value
        assertEquals(students.get(3).toString().contains("Freshman"),true);
        //update to 8 credits, still a freshman
        assertEquals(students.get(3).updateStatus(2),false);
        assertEquals(students.get(3).toString().contains("Freshman"),true);
        //update to 9 credits, sophomore
        assertEquals(students.get(3).updateStatus(1),false);
        assertEquals(students.get(3).toString().contains("Sophomore"),true);
        //update to 16 credits, sophomore
        assertEquals(students.get(3).updateStatus(7),false);
        assertEquals(students.get(3).toString().contains("Sophomore"),true);
        //update to 17 credits, junior
        assertEquals(students.get(3).updateStatus(1),false);
        assertEquals(students.get(3).toString().contains("Junior"),true);
        //update to 24 credits, junior
        assertEquals(students.get(3).updateStatus(7),false);
        assertEquals(students.get(3).toString().contains("Junior"),true);
        //update to 25 credits, senior
        assertEquals(students.get(3).updateStatus(1),false);
        assertEquals(students.get(3).toString().contains("Senior"),true);        
        //update to 27 credits, senior
        assertEquals(students.get(3).updateStatus(2),false);
        assertEquals(students.get(3).toString().contains("Senior"),true); 
        //update to 28 credits, graduates
        assertEquals(students.get(3).updateStatus(1),true);
        assertEquals(students.get(3).toString().contains("Graduate"),true); 
    }
    @Test
    public void UGUpdate() 
    {
        //starting value
        assertEquals(students.get(4).toString().contains("Freshman"),true);
        //update to 30 credits, still a freshman
        assertEquals(students.get(4).updateStatus(13),false);
        assertEquals(students.get(4).toString().contains("Freshman"),true);
        //update to 31 credits, sophomore
        assertEquals(students.get(4).updateStatus(1),false);
        assertEquals(students.get(4).toString().contains("Sophomore"),true);
        //update to 60 credits, sophomore
        assertEquals(students.get(4).updateStatus(29),false);
        assertEquals(students.get(4).toString().contains("Sophomore"),true);
        //update to 61 credits, junior
        assertEquals(students.get(4).updateStatus(1),false);
        assertEquals(students.get(4).toString().contains("Junior"),true);
        //update to 90 credits, junior
        assertEquals(students.get(4).updateStatus(29),false);
        assertEquals(students.get(4).toString().contains("Junior"),true);
        //update to 91 credits, senior
        assertEquals(students.get(4).updateStatus(1),false);
        assertEquals(students.get(4).toString().contains("Senior"),true);        
        //update to 119 credits, senior
        assertEquals(students.get(4).updateStatus(28),false);
        assertEquals(students.get(4).toString().contains("Senior"),true); 
        //update to 28 credits, graduates
        assertEquals(students.get(4).updateStatus(1),true);
        assertEquals(students.get(4).toString().contains("Graduate"),true); 
    }
    @Test
    public void GSUpdate() 
    {
        //starting value
        assertEquals(students.get(8).toString().contains("Planning"),true);
        //update to 59 credits, still doing coursework
        assertEquals(students.get(8).updateStatus(3),false);
        assertEquals(students.get(8).toString().contains("Planning"),true);
        //update to 60 credits, writing dissertation
        assertEquals(students.get(8).updateStatus(1),false);
        assertEquals(students.get(8).toString().contains("Writing"),true);
        //update to 99 pages - Still Writing
        assertEquals(students.get(8).updateStatus(99),false);
        assertEquals(students.get(8).toString().contains("Writing"),true);
        //update to 100 pages, Revising
        assertEquals(students.get(8).updateStatus(1),false);
        assertEquals(students.get(8).toString().contains("Revising"),true);
        //update to 249 pages Revising, junior
        assertEquals(students.get(8).updateStatus(149),false);
        assertEquals(students.get(8).toString().contains("Revising"),true);
        //update to 250 pages, done!
        assertEquals(students.get(8).updateStatus(1),true);
        assertEquals(students.get(8).toString().contains("PhD"),true);        
 
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
}
