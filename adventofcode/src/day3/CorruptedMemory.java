/**
 * 
 */
package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class CorruptedMemory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<String> multiplierList = new LinkedList<String>();

		String text = readInMemory();
		
		
		
		System.out.println(checkForMul(text));

	}

	
	
	
	/**
	 * 
	 * @return
	 */
	public static String readInMemory() {
	    File file = new File("corruptedMemoryInput.txt");
	    StringBuilder text = new StringBuilder();

	    try {
	        Scanner scanner = new Scanner(file);

	        while (scanner.hasNextLine()) {
	            text.append(scanner.nextLine().trim()).append(" ");
	        }

	        scanner.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	    return text.toString();
	}
	
	
	/**
	 * 
	 */
	public static int checkForMul(String input) {
      
        String mulRegex = "mul\\(\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\)";
        String doRegex = "do\\(\\)";
        String dontRegex = "don't\\(\\)";

      
        Pattern mulPattern = Pattern.compile(mulRegex);
        Pattern doPattern = Pattern.compile(doRegex);
        Pattern dontPattern = Pattern.compile(dontRegex);

        
        Matcher mulMatcher = mulPattern.matcher(input);
        Matcher doMatcher = doPattern.matcher(input);
        Matcher dontMatcher = dontPattern.matcher(input);

   
        boolean isMulEnabled = true; 
        int mulTotal = 0;

     
        int currentIndex = 0;


        while (currentIndex < input.length()) {

            if (mulMatcher.find(currentIndex) && mulMatcher.start() == currentIndex) {
           
                if (isMulEnabled) {
                    int num1 = Integer.parseInt(mulMatcher.group(1));
                    int num2 = Integer.parseInt(mulMatcher.group(2));
                    mulTotal += num1 * num2;
                }
                currentIndex = mulMatcher.end();
            } else if (doMatcher.find(currentIndex) && doMatcher.start() == currentIndex) {
              
                isMulEnabled = true;
                currentIndex = doMatcher.end();
            } else if (dontMatcher.find(currentIndex) && dontMatcher.start() == currentIndex) {
             
                isMulEnabled = false;
                currentIndex = dontMatcher.end();
            } else {
         
                currentIndex++;
            }
        }

        return mulTotal;
    }

}
