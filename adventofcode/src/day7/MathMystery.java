package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MathMystery {
    public static void main(String[] args) {
        File file = new File("mathInput.txt");

        // Read equations from file
        Map<Long, List<Integer>> equations = ReadInEquations(file);

        // Calculate total calibration result
        long calibrationResult = calculateValidEquations(equations);

        // Output the result
        System.out.println("Total Calibration Result: " + calibrationResult);
    }

    public static Map<Long, List<Integer>> ReadInEquations(File file) {
        Map<Long, List<Integer>> equations = new HashMap<>();

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split(":");
                long target = Long.parseLong(parts[0].trim());
                List<Integer> numbers = new ArrayList<>();

                for (String num : parts[1].trim().split(" ")) {
                    numbers.add(Integer.parseInt(num));
                }

                equations.put(target, numbers);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return equations;
    }

    public static long calculateValidEquations(Map<Long, List<Integer>> equations) {
        long totalCalibrationResult = 0;

        for (Map.Entry<Long, List<Integer>> entry : equations.entrySet()) {
            long target = entry.getKey();
            List<Integer> numbers = entry.getValue();

            // Check if the target value can be produced
            if (canProduceTarget(numbers, target)) {
                totalCalibrationResult += target;
            }
        }

        return totalCalibrationResult;
    }

    public static boolean canProduceTarget(List<Integer> numbers, long target) {
        return evaluateCombinations(numbers, 0, numbers.get(0), target);
    }

    
    public static boolean evaluateCombinations(List<Integer> numbers, int index, long currentValue, long target) {
        if (index == numbers.size() - 1) {
            return currentValue == target;
        }

        int nextNumber = numbers.get(index + 1);

        // Try addition
        if (evaluateCombinations(numbers, index + 1, currentValue + nextNumber, target)) {
            return true;
        }

        // Try multiplication
        if (evaluateCombinations(numbers, index + 1, currentValue * nextNumber, target)) {
            return true;
        }

        return false;
    }
}