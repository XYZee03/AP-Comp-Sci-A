import java.util.ArrayList;
/**
 * Write a description of class StudentRunner here.
 * 
 * @author David Gumminger
 * @version August 2016
 */
public class StudentMain
{
    public static void main (String [] args)
    {
        //create an arraylist to hold the students we are following in the study
        ArrayList<Student> students = new ArrayList<Student>();
        
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
        
        for (Student s: students)
            s.updateStatus(0);
        /**
         * Print the list of all students who have a GPA of less than
         * 3.5 and also print the name of the school they are attending.
         */
        System.out.println("Students with GPA less than 3.5");
        for (Student s: students)
            if (s.getGPA() < 3.5)
                System.out.println(s + "\n" + s.getSchoolName());
        
        
        /**
         * Print out all students who are in some state of ABD
         * "All But Dissertation"
         */
        System.out.println("\n\nGrad students who are ABD");
        for (Student s: students)
            if (s.toString().contains("ABD")){
                System.out.println(s);
            }
        
        /** update the status of each student in the list based on the
         * data provided below. Note that the first data item corresponds
         * to the first Student object in the ArrayList created above.
         * 
         * Print out any students who have graduated along with a congratulations
         * message as the ArrayList is processed. 
         */
        //This is the array of updates to apply
        int [] updates = {4, 7, 8, 6, 15, 10, 30, 31, 5, 7, 127, 21};
        
        System.out.println("\n\nUpdate Students and List of Students Graduating:");
        int arrInd = 0;
        for (int i = 0; i < students.size(); i++){
            students.get(i).updateStatus(updates[arrInd]);
            if (students.get(i).updateStatus(0)){
                System.out.println(students.get(i) + "\nCongratulations Graduate!");
                students.remove(i);
                i--;
            }
            arrInd++;
        }
        /**
         * Print out the remaining students in the student list
         */
        System.out.println("\n\nStatus of remaining Students:");
        for (Student s: students)
            System.out.println(s);
    }
}