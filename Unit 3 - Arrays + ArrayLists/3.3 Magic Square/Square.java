// ****************************************************************
// Square.java
//
// Define a Square class with methods to create and read in
// info for a square matrix and to compute the sum of a row,
// a col, either diagonal, and whether it is magic.
//          
// ****************************************************************
import java.io.*;
import java.util.Scanner;

public class Square
{
    int[][] square;
    //--------------------------------------
    //create new square from a data file
    //--------------------------------------
    public Square(String filename) throws IOException
    {
        square = readSquare(filename);
    }

    //-----------------------------------
    //return size of the square
    //-----------------------------------
    public int size()
    {
        return square.length;
    }
    //--------------------------------------
    //return the sum of the values in the given row
    //--------------------------------------
    public int sumRow(int row)
    {
        int sum = 0;
        for (int i: square[row]){
            sum += i;
        }
        return sum;
    }

    //--------------------------------------
    //return the sum of the values in the given column
    //--------------------------------------
    public int sumCol(int col)
    {
        int sum = 0;
        for (int i = 0; i < square.length; i++){
            sum += square[i][col];
        }
        return sum;
    }

    //--------------------------------------
    //return the sum of the values in the main diagonal
    //--------------------------------------
    public int sumMainDiag()
    {
        int sum = 0;
        for(int i = 0; i < square.length; i++){
            sum += square[i][i];
        }
        return sum;
    }

    //--------------------------------------
    //return the sum of the values in the other ("reverse") diagonal
    //--------------------------------------
    public int sumOtherDiag()
    {
        int sum = 0;
        for(int i = 0; i < square.length; i++){
            sum += square[i][square.length-1-i];
        }
        return sum;
    }

    //--------------------------------------
    //return true if the square is magic (all rows, cols, and diags have
    //same sum), false otherwise
    //--------------------------------------
    public boolean magic()
    {
        int val = sumRow(0);
        for (int i = 0; i < square.length; i++){
            if (sumRow(i) != val)
                return false;
            if (sumCol(i) != val)
                return false;
        }
        if(sumMainDiag() != val)
            return false;
        if (sumOtherDiag() != val)
            return false;
        return true;
    }

    //--------------------------------------
    //read info into the square from a file
    //--------------------------------------
    public int[][] readSquare(String fileName) throws IOException
    {
        Scanner scan = new Scanner (new File(fileName));
        int size = scan.nextInt();
        int [][] readSquare = new int[size][size];
        for (int row = 0; row < readSquare.length; row++)
            for (int col = 0; col < readSquare.length; col ++)
                readSquare[row][col] = scan.nextInt();
        return readSquare;
    }

    //--------------------------------------
    //print the contents of the square, neatly formatted
    // MUST BE DONE USING A FOR-EACH LOOP!
    //--------------------------------------
    public void printSquare()
    {
        for (int[] i: square){
            for (int j: i)
                System.out.print(j + " ");
            System.out.println();
        }
    }

}