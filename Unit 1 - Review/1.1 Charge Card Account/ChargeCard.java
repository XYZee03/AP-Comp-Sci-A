
/**
 * Write a description of class ChargeCard here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Scanner;

public class ChargeCard
{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);  //Scanner creation
        
        //input 1
        System.out.print("Enter the previous balance: ");
        double prev = scan.nextDouble();
        System.out.println();
        
        //input 2
        System.out.print("Enter the total new charges: ");
        double charges = scan.nextDouble();
        
        
        //interest calculation
        double interest;
        if (prev == 0)
            interest = 0;
        else
            interest = .02 * (prev + charges);
        
        double newBal = interest + prev + charges;
        
        double minPay;
        if (newBal < 50)
            minPay = newBal;
        else if (newBal >= 50 && newBal <= 300)
            minPay = 50;
        else
            minPay = .2*newBal;
        
        System.out.printf("CS CARD International Statement\n===============================\n\nPrevious Balance:\t\t$%.2f\nAdditional Charges:\t\t$%.2f\nInterest:\t\t\t$%.2f\n\nNew Balance:\t\t\t$%.2f\n\nMinimum Payment:\t\t$%.2f", prev, charges, interest, newBal, minPay);
    }
}
