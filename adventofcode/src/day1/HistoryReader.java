package day1;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HistoryReader {
	
	
	/**
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
   
        List<Integer> col1 = new ArrayList<>();
        List<Integer> col2 = new ArrayList<>();


        readFile("input.txt", col1, col2);


        int totalDistance = calculateTotalDistance(col1, col2);


        System.out.println("Total Distance: " + totalDistance);
        
        System.out.println(calculateSimilarityScore(col1, col2));
    }

    
    
    /**
     * 
     * @param fileName
     * @param col1
     * @param col2
     */
    public static void readFile(String fileName, List<Integer> col1, List<Integer> col2) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
            	
                String line = scanner.nextLine().trim();
                
                String[] numbers = line.split("\\s+");

                if (numbers.length >= 2) {
                    col1.add(Integer.parseInt(numbers[0]));
                    col2.add(Integer.parseInt(numbers[1]));
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    
    
    /**
     * 
     * @param col1
     * @param col2
     * @return
     */
    public static int calculateTotalDistance(List<Integer> col1, List<Integer> col2) {

        Collections.sort(col1);
        Collections.sort(col2);

        int totalDistance = 0;

        for (int i = 0; i < col1.size(); i++) {
            totalDistance += Math.abs(col1.get(i) - col2.get(i));
        }

        return totalDistance;
    }
    
    /**
     * 
     * @param col1
     * @param col2
     */
    public static int calculateSimilarityScore(List<Integer> col1, List<Integer> col2) {
    	
    	int similarityScore = 0;
 
    	
    	
    	for(int counter = 0; counter <= col1.size()-1; counter++) {
    		
    		int num1 = col1.get(counter);
    		
    		int colCounter = 0;
    		
//    		System.out.println(num1);
    		
    		for (int secondCounter = 0; secondCounter <= col2.size() - 1 ; secondCounter++) {
    			
    			int num2 = col2.get(secondCounter);
    			
    			if(num1 == num2) {
    				colCounter++;
    			}
    			
    		}
    		
    		similarityScore += colCounter * num1;
    		
    	}
    	
    	
    	return similarityScore;
    	
    }
}

