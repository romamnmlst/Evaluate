//Matthew Usnick
//Evalute.java
//Spring 2011

import java.io.*;
import java.util.*;

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Evaluate@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
/** Task: A program that analyzes plain text file(s). It displays the number of 
*         characters in the file, the number of unique characters, the number of 
*         lines, and the number of words (any string of characters separated by 
*         white space). Finally, it displays every character used, with the 
*         number of times each character is used, in ascending order. If a file
*         cannot be found, the user is alerted, and the program moves to the 
*         next file to evaluate.
*/
public class Evaluate
{
    //*****************************countLines()*********************************
	/** Task: The method counts the number of lines in the file
	* 
    * @param file is the File to be evaluated
    * @return the total number of lines in the file (int)
    * @throws IOException 
    */
    static int countLines(File file) throws IOException
    {
        //create a LineNumberReader object
        LineNumberReader read = new LineNumberReader(new FileReader(file));

        //temporary variable to count the lines
        int lineTotal = 0;

        //while there is a line to read
        while (read.readLine() != null)
        {
            //increase the line count
            lineTotal++;
        }

        //return the total number of lines
        return lineTotal;

    }//end of countLines()

    //****************************displayHelp()*********************************
    /** Task: Display information about what this program does, and how to use
    *         it.
    */
    static void displayHelp()
    {
        System.out.println();
        System.out.println("Evaluate.java");
        System.out.println("-------------");

        System.out.println("This program analyzes any number of inputed plain " 
                         + "text files. It displays the number of characters " 
                         + "in the file, the number of unique characters, the " 
                         + "number of lines, and the number of words (any " 
                         + "string of characters separated by white space). " 
                         + "Finally, it displays every character used, with " 
                         + "the number of times each character is used, in " 
                         + "ascending order. If a file cannot be found, the "
                         + "user is alerted, and the program moves to the next " 
                         + "file to evaluate.");

        System.out.println("Example: java Evaluate fileToEvaluate " 
                         + " anotherFileToEvaluate etc");

    }//end displayHelp()

    //****************************evaluateFile()********************************
    /** Task: This method evaluates the specified file, and prints the results.
    *
    * @param file is a File that is being evaluated
    * @throws IOException
    */
    static void evaluateFile(File file) throws IOException
    {
        //analyze the file------------------------------------------------------

        //create a HashMap to hold characters and their count
        HashMap<String,Integer> charCountArray = new HashMap<String,Integer>();

        //pass the file and charCountArray reference into analyzeFile(). 
        //this returns the word count
        int wordCount = getCount(file, charCountArray);

        //get the number of characters in file
        int charCount = (int)file.length();

        //get number of unique characters in file
        int uniqueCount = charCountArray.size();

        //pass lineRead to countLines()
        //this returns the number of lines in the file
        int lineCount = countLines(file);

        //create an array to hold characters
        String[] charArray = new String[charCountArray.size()];

        //create an array to hold char counts
        Integer[] valueArray = new Integer[charCountArray.size()];

        //put character keys into charArray
        charCountArray.keySet().toArray(charArray);

        //put character count values into valueArray
        charCountArray.values().toArray(valueArray);

        //sort the arrays
        sortArrayPairs(charArray, valueArray);

        //print the results-----------------------------------------------------
        //print file length
        System.out.println("The number of characters are: " + charCount);

        //print number of unique characters
        System.out.println("The number of unique characters are: "
                         + uniqueCount);

        //print number of words
        System.out.println("The number of words are: " + wordCount);

        //print number of lines
        System.out.println("The number of lines are: " + lineCount);

        //print characters and their count
        System.out.println("The following are the characters used file, " 
                         + "followed by the number of times they appear :");

        //blank line
        System.out.println();

        //for every character and value
        for (int i=0; i < charCountArray.size(); i++)
        {
            //print character and value separated by " : " on a new line
        	System.out.println(charArray[i] + " : " + valueArray[i]);
        }
    }//end evaluateFile()

    //*****************************formatChar()*********************************
    /** Task: This method converts escaped characters to a string equivalent
    * 
    * @param charToFormat is the inputed character
    * @return the reformatted character (as a string) 
    */
    static String formatChar(String charToFormat)
    {
        //if char is a newline
        if(charToFormat.equals("\n"))
        {
            //rename to '\n'
            charToFormat = "\\n";
        }

        //if char is a space
        if(charToFormat.equals(" "))
        {
            //rename to "space"
            charToFormat = "space";
        }

        //if char is a tab
        if(charToFormat.equals("\t"))
        {
            //rename to \t
            charToFormat = "\\t";
        }

        //if char is a return
        if(charToFormat.equals("\r"))
        {
            //rename to \r
            charToFormat = "\\r";
        }

        //if char is a form feed
        if (charToFormat.equals("\f"))
        {
            //rename to \f
            charToFormat ="\\f";
        }

        //if the char is a single char
        if(charToFormat.length() == 1)
        {
            //prefix a space, for formatting purposes
            charToFormat = (" " + charToFormat);
        }

        //return formatted character
        return charToFormat;

    }//end of formatChar()

    //*****************************getCount()***********************************
    /** Task: This method gets the word count of the file. It also populates
    *         the hashmap with the individual character count of the file.
    * 
    * @param inputFile is the file that is being evaluated
    * @param countArray is a hashmap that stores the characters and their count
    * @return the number of words in the file (int)
    * @throws IOException
    */
    static int getCount(File inputFile, HashMap<String,Integer> countArray) 
        throws IOException
    {
        //create a new file reader
        FileReader readFile = new FileReader(inputFile);

        //variable to temporarily hold the current character
        char tempChar;

        //variable to temporarily hold the last character
        char lastChar = ' ';

        //variable to temporarily hold the character as a String
        String tempString;

        //variable to temporarily hold the value of the current character count
        int tempValue;

        //variable to count the number of words in the file
        int wordCountInt = 0;

        // do while file has another character to read 
        do
        {
            //convert the character number to a character and store in the 
            //tempChar variable
            tempChar = ((char)readFile.read());

            //put tempChar into tempString
            tempString = ("" + tempChar);

            //format tempString character and assign to tempString
            tempString = formatChar(tempString);

            //if validation array has no value for the current char key
            if(countArray.get(tempString) == null)
            {
                //add the current char, and set value to 1
                countArray.put(tempString, 1);
            }

            //if the validation array already has a value for the current char 
            else
            {
                //get the value from the current key and store in tempValue
                tempValue = (countArray.get(tempString));

                //increase the value by one
                countArray.put(tempString, tempValue + 1);
            }

            //if lastChar = whitespace, and current char = character
            if (Character.isWhitespace(lastChar) 
            && !Character.isWhitespace(tempChar))
            {
                //increase the word count by one
                wordCountInt++;
            }

            //assign tempChar to lastChar
            lastChar = tempChar;

        }while (readFile.ready());//while there is a character to read, repeat

        //return the word count
        return wordCountInt;

    }//end of getCount()

    //****************************sortArrayPairs()******************************
    /** Task: This method sorts the character and characterCount arrays in 
    *         parallel. They are ordered from least number of characters to
    *         the most. There is no return value, as the arrays are passed by
    *         reference.
    * 
    * @param characters is a string array of characters
    * @param characterCount is an Integer array of character counts
    */
    static void sortArrayPairs(String[] characters, Integer[] characterCount)
    {
        //counter variable
        int counter;

        //flag variable to signal if a swap occurred
        boolean swapFlag = true;

        //variable to temporarily hold a character value
        String charTemp;

        //variable to temporarily hold a character count value
        int countTemp;

        //while a swap has occurred
        while (swapFlag)
        {
            //set swapFlag to false
            swapFlag = false;

            //repeat for the length of the array
            for (counter = 0; counter < characterCount.length-1; counter++)
            {
                //if char count is larger than the next character count in the
                //array
                if((characterCount[counter]) > (characterCount[counter + 1]))
                {
                    //place character count in temp variable
                    countTemp = characterCount[counter];

                    //place character in temp variable
                    charTemp = characters[counter];

                    //move next char count into current character count index
                    characterCount[counter] = characterCount[counter + 1];

                    //move next character into current character index
                    characters[counter] = characters[counter + 1];

                    //move old char count into next char count index
                    characterCount[counter + 1] = countTemp;

                    //move old character into next character index
                    characters[counter + 1] = charTemp;

                    swapFlag = true;//set swapFlag to true

                }//end if
            }//end for
        }//end while

    }//end sortArrayPairs()

    ///////////////////////////////main()///////////////////////////////////////
    /** Task: Check to see if the user has inputed file(s) to evaluate. If not,
    *         display a help file and exit. If there are files to evaluate, 
    *         check to see if the file exists. If it exists, evaluate the file,
    *         otherwise move to the next file to evaluate.
    * 
    * @param args is the file(s) the user has specified to evaluate.
    * @throws IOException 
    */
    public static void main(String[] args) throws IOException
    {
        //if the user has not entered a file to evaluate
        if(args.length == 0)
        {
            //display help information
            displayHelp();

            //exit the program
            System.exit(0);
        }

        //for every file that the user has entered
        for(int i=0; i<args.length; i++)
        {
            //print a dividing line
            System.out.println("---------------------------------------------");

            //display the name of the file that is currently being evaluated
            System.out.println("Evaluating: " + args[i]);
            System.out.println();

            //load the file
            File fileToRead = new File (args[i]);

            //if the file doesn't exist
            if(!fileToRead.exists())
            {
                //display a message that says the file does not exist 
                System.out.println("The file \"" + fileToRead + "\" does " 
                                 + "not exist or cannot be found.");

                //move to the next file
                continue;
            }
            //if the file exists
            else
            {
                //evaluate the file
                evaluateFile(fileToRead);
            }

        }//end for loop

        //print a dividing line
        System.out.println("---------------------------------------------");

    }//end main()
    
}//end Evaluate.java