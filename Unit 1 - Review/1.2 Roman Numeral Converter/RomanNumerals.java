
/**
 * Write a description of class RomanNumerals here.
 *
 * @Zeeshan A
 * @version (a version number or a date)
 */
import java.util.Scanner;
public class RomanNumerals
{
    public static String convertToRoman(int in){
        String answer = "";
        int[] romanNumDict = {1, 5, 10, 50, 100};
        String[] romanLetDict = {"I", "V", "X", "L", "C"};
        while ((in - romanNumDict[4]) >= 0){
            answer += romanLetDict[4];
            in -= romanNumDict[4];
        }
        while ((in - romanNumDict[3]) >= 0){
            if (in / romanNumDict[2] == 9){
                answer += (romanLetDict[2] + romanLetDict[4]);
                in -= (romanNumDict[4] - romanNumDict[2]);
            }
            else{
                answer += romanLetDict[3];
                in -= romanNumDict[3];
            }
        }
        while ((in - romanNumDict[2]) >= 0){
            if (in / romanNumDict[2] == 4){
                answer += (romanLetDict[2] + romanLetDict[3]);
                in -= (romanNumDict[3] - romanNumDict[2]);
            }
            else{
                answer += romanLetDict[2];
                in -= romanNumDict[2];
            }
        }
        while ((in - romanNumDict[1]) >= 0){
            if (in / romanNumDict[0] == 9){
                answer += (romanLetDict[0] + romanLetDict[2]);
                in -= (romanNumDict[2] - romanNumDict[0]);
            }
            else{
                answer += romanLetDict[1];
                in -= romanNumDict[1];
            }
        }
        while ((in - romanNumDict[0]) >= 0){
            if (in / romanNumDict[0] == 4){
                answer += (romanLetDict[0] + romanLetDict[1]);
                in -= (romanNumDict[1] - romanNumDict[0]);
            }
            else{
                answer += romanLetDict[0];
                in -= romanNumDict[0];
            }
        }
        return answer;
    }
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int num = 1;
         while (num >= 0){
             System.out.print("ENTER A NUMBER: ");
             num = scan.nextInt();
             while (num > 100)
                num = scan.nextInt();
             String ans = convertToRoman(num);
             System.out.println(ans);
        }
    }
}
