package day5;

import java.io.File;
import java.util.*;

public class WtfRules {

    public static void main(String[] args) {
        // Input file paths
        File file = new File("WTFinput.txt");

        // Read the day5 and updates
        int[][] rules = readInRules(file);
        List<int[]> updates = readInUpdates(file);

        // Identify incorrectly ordered updates and fix them
        int sumOfMiddlePages = fixAndSumMiddlePages(rules, updates);
        System.out.println("Sum of middle pages after fixing: " + sumOfMiddlePages);
    }

    public static int[][] readInRules(File file) {
        List<List<Integer>> tempRules = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip lines that don't contain "|"
                if (!line.contains("|")) {
                    continue;
                }

                // Split the line on the "|" character
                String[] parts = line.split("\\|");

                // Parse the integers and store them in a temporary list
                List<Integer> currentSet = new ArrayList<>();
                for (String part : parts) {
                    try {
                        currentSet.add(Integer.parseInt(part.trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in line: " + line);
                    }
                }

                // Add the current set to the main list
                tempRules.add(currentSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert List<List<Integer>> to int[][]
        int[][] rules = new int[tempRules.size()][];
        for (int i = 0; i < tempRules.size(); i++) {
            List<Integer> row = tempRules.get(i);
            rules[i] = row.stream().mapToInt(Integer::intValue).toArray();
        }

        return rules;
    }

    public static List<int[]> readInUpdates(File file) {
        List<int[]> updates = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip lines that are day5
                if (line.contains("|") || line.isEmpty()) {
                    continue;
                }

                // Split the line on commas
                String[] parts = line.split(",");
                int[] update = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    update[i] = Integer.parseInt(parts[i].trim());
                }

                updates.add(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updates;
    }

    public static int fixAndSumMiddlePages(int[][] rules, List<int[]> updates) {
        int sum = 0;

        // Convert day5 to a map for faster access
        HashMap<Integer, List<Integer>> ruleMap = new HashMap<>();
        for (int[] rule : rules) {
            ruleMap.computeIfAbsent(rule[0], k -> new ArrayList<>()).add(rule[1]);
        }

        for (int[] update : updates) {
            if (!isValidUpdate(update, ruleMap)) {
                // Fix the update using topological sorting
                int[] fixedUpdate = topologicalSort(update, ruleMap);

                // Find the middle page number of the fixed update
                int middlePage = fixedUpdate[fixedUpdate.length / 2];
                sum += middlePage;
            }
        }

        return sum;
    }

    public static boolean isValidUpdate(int[] update, HashMap<Integer, List<Integer>> ruleMap) {
        for (int i = 0; i < update.length; i++) {
            for (int j = i + 1; j < update.length; j++) {
                // If there's a rule that update[j] must come before update[i], return false
                if (ruleMap.containsKey(update[j]) && ruleMap.get(update[j]).contains(update[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] topologicalSort(int[] update, HashMap<Integer, List<Integer>> ruleMap) {
        // Build graph from the update and the day5
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        HashMap<Integer, Integer> inDegree = new HashMap<>();

        for (int page : update) {
            graph.put(page, new ArrayList<>());
            inDegree.put(page, 0);
        }

        for (int page : update) {
            if (ruleMap.containsKey(page)) {
                for (int neighbor : ruleMap.get(page)) {
                    if (inDegree.containsKey(neighbor)) {
                        graph.get(page).add(neighbor);
                        inDegree.put(neighbor, inDegree.get(neighbor) + 1);
                    }
                }
            }
        }

        // Perform topological sort using Kahn's algorithm
        Queue<Integer> queue = new LinkedList<>();
        for (int page : inDegree.keySet()) {
            if (inDegree.get(page) == 0) {
                queue.add(page);
            }
        }

        List<Integer> sortedOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sortedOrder.add(current);

            for (int neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sortedOrder.stream().mapToInt(Integer::intValue).toArray();
    }
}