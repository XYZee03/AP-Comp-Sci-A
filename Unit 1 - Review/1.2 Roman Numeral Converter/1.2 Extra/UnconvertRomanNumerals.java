
/**
 * Write a description of class UncovertRomanNumerals here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UnconvertRomanNumerals
{

    public final int[] NUMBER_VALUES = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 }; // array containing all of the values
    public final String[] NUMBER_LETTERS = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" }; // array containing all of the numerals

    /**
     * Method used to convert a string to a integer
     * @param roman roman numeral to be converted
     * @return integer
     */
    public int toNumerical(String roman) {
        for (int i : NUMBER_LETTERS) { // Loop through all the letters
            if(roman.startsWith(NUMBER_LETTERS[i])) // Check if the string starts with that letter
                return NUMBER_VALUES[i] + toNumerical(roman.replaceFirst(NUMBER_LETTERS[i], "")); // Rinse and repeats until the string is empty and return it
        }
        return 0; // If something went wrong, simply return 0
    }
}
