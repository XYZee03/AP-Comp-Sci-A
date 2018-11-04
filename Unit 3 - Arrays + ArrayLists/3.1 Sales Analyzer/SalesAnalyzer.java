
/**
 * This class can be used to analyze the sales of a group of sales people.
 * You will be completsalesg the methods to fsalesd the total sales, average sale, 
 * min and max sale and a generatsalesg a report that returns a table with each salesperson's 
 * name and amount of sales.  The more challengsalesg part of this project is 
 * to write the methods that return an array of names that meet certasales criteria.
 * These methods salesclude people with sales greater than or equal to a given 
 * amount as well as people that have the maximum sales amounts.
 * 
 * @author Robert Gammelgaard/David Gumminger 
 * @version 20171025
 */
public class SalesAnalyzer
{

    private int[] sales;
    private String[] names;

    /**
     * Constructor for objects of class SalesForce.
     * @param names is the names of the salespeople.
     * @param sales is the amount of sales for each sales person.
     * Precondition: names and sales must have same length and not null.
     */
    public SalesAnalyzer(String[] names,int[] sales)
    {
        this.names = names;
        this.sales = sales;
    }

    /*#
     * 1. Write the method totalSales
     */
    /**
     * @return  the total sales of all sales people
     */
    public int totalSales()
    {
        int count = 0;
        for (int i: sales)
            count += i;
        return count;
    }
    
    /*#
     * 2. Write the method averageSale
     */
    /**
     * @return  the average of the sales from all sales people
     */
    public double averageSale()
    {
        double count = 0;
        for (int i: sales)
            count += i;
        return count/sales.length;
    }    

    /*#
     * 3. Write the method maxSale 
     */
    /**
     * @return  the maximum sales amount from all sales people
     */
    public int maxSale()
    {        
        int count = sales[0];
        for (int i: sales){
            if (i > count)
                count = i;
        }
        return count;
    }
    /*#
     * 4. Write the method minSale 
     */
    /**
     * @return  the minimum sales amount of all sales people
     */
    public int minSale()
    {           
        int count = sales[0];
        for (int i: sales){
            if (i < count)
                count = i;
        }
        return count;
    }
    
    /*#
     * 5. Write the method numSalesAtOrAbove
     */
    /**
     * @param n is the minimum sales amount to be counted   
     * @return  the number of sales amounts that are greater or equal to n.
     */
    public int numSalesAtOrAbove(int n)
    {
        int count = 0;
        for (int i: sales){
            if (i >= n)
                count++;
        }
        return count;
    }
    
    /*#
     * 6. Write the method maxSalesPeople
     */
    /** 
     * @return  an array of the names of the salespeople who have the highest sales.
     */
    public String[] maxSalesPeople()
    {   
        int max = maxSale();
        String[] top = new String[numSalesAtOrAbove(max)];
        int topInd = 0;
        for (int i = 0; i < sales.length; i++){
            if (sales[i] == max){
                top[topInd] = names[i];
                topInd++;
            }
        }
        return top;
    }
    /*#
     * 7. Write the method peopleWithSalesAtOrAbove
     */
    /**
     * @param n is the minimum sales amount for sales person to be included   
     * @return an array of strings of the names of salespeople that have sales
     *  that are greater than or equal to n.
     */
    public String[] peopleWithSalesAtOrAbove(int n)
    {       
        int count = numSalesAtOrAbove(n);
        String[] top = new String[count];
        int topInd = 0;
        for (int i = 0; i < sales.length; i++){
            if (sales[i] >= n){
                top[topInd] = names[i];
                topInd++;
            }
        }
        return top; 
    }
    /*#
     * 8. Complete the report method
     */
    /**
     * @return a string that, when printed, creates a table of salespeople and sales amounts.
     */
    public String report()
    {

        String s= "Salesperson   Sales\n"+
            "--------------------\n";
        /*add a loop here to add sales the names and sales*/
        for (int i = 0; i < names.length; i++){
            s += (names[i] + "\t\t" + sales[i] + "\n");
        }
        return s;
    }
}
