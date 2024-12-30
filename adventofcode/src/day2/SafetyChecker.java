/**
 * 
 */
package day2;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 */
public class SafetyChecker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<List<Integer>> numberList = new ArrayList<>();
		
		readFile(numberList);
		
		System.out.println(findSafeReports(numberList));
		
		System.out.println(findSafeReportsWithDampener(numberList));
		
		

	}
	
	
	/**
	 * 
	 * @param numList
	 */
	public static void readFile(List<List<Integer>> numList) {
	    String input = "input2.txt";
	    File file = new File(input);

	    try {
	        Scanner scanner = new Scanner(file);

	        while (scanner.hasNextLine()) {
	        	
	            String line = scanner.nextLine().trim();


	            String[] numbers = line.split("\\s+");


	            List<Integer> lineList = new ArrayList<>();

	 
	            for (String number : numbers) {
	                lineList.add(Integer.parseInt(number));
	            }

	    
	            numList.add(lineList);
	        }

	        scanner.close();
	    } catch (Exception e) {
	        System.out.println("Error reading file: " + e.getMessage());
	    }
	}
	
	
	
	/**
	 * 
	 * @param numList
	 * @return
	 */
	public static int findSafeReports(List<List<Integer>> numList) {
	    int safeReports = 0;

	    
	    for (List<Integer> eachList : numList) {
	        if (isSafe(eachList)) {
	            safeReports++;
	        }
	    }

	    return safeReports;
	}
	
	
	/**
	 * 
	 * @param numList
	 * @return
	 */
	public static int findSafeReportsWithDampener(List<List<Integer>> numList) {
        int safeWithDampener = 0;

        for (List<Integer> eachList : numList) {
            // If the report is already safe, count it
            if (isSafe(eachList)) {
                safeWithDampener++;
                continue;
            }

            // Try removing each level and check if the modified list is safe
            for (int i = 0; i < eachList.size(); i++) {
                List<Integer> modifiedList = new ArrayList<>(eachList);
                modifiedList.remove(i);

                if (isSafe(modifiedList)) {
                    safeWithDampener++;
                    break; // Stop checking further if it's safe with one removal
                }
            }
        }

        return safeWithDampener;
    }

	
	
	/**
	 * Determines if a single report is safe.
	 */
	public static boolean isSafe(List<Integer> report) {
	    if (report.size() < 2) {
	        return false; // Reports with fewer than 2 levels can't be evaluated
	    }

	    boolean isIncreasing = true; 
	    boolean isDecreasing = true;

	    for (int i = 1; i < report.size(); i++) {
	        int diff = report.get(i) - report.get(i - 1);

	        // Check if the difference is within the allowed range
	        if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
	            return false; 
	        }

	        // Check monotonicity
	        if (diff > 0) {
	            isDecreasing = false; // Not decreasing
	        } else if (diff < 0) {
	            isIncreasing = false; // Not increasing
	        }
	    }

	    // A report is safe if it is entirely increasing or decreasing
	    return isIncreasing || isDecreasing;
	}
	
	

}
