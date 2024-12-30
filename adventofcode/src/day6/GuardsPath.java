package day6;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class GuardsPath {
	
	
	/**
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
        // Input file path
        File file = new File("GuardMap.txt");

        try {
            // Read the map from the file
            char[][] map = readMapFromFile(file);

            // Simulate the guard's movement and calculate distinct positions
            int distinctPositions = simulateGuardPath(map);

            System.out.println("Distinct positions visited by the guard: " + distinctPositions);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static char[][] readMapFromFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);

        // Read all lines to determine map dimensions
        StringBuilder mapBuilder = new StringBuilder();
        int rows = 0, cols = 0;
        while (scanner.hasNextLine()) {
        	
            String line = scanner.nextLine();
            mapBuilder.append(line).append("\n");
            rows++;
            
            cols = line.length(); // Assuming consistent row length
        }
        scanner.close();

        // Build the map as a 2D character array
        char[][] map = new char[rows][cols];
        String[] lines = mapBuilder.toString().split("\n");
        for (int i = 0; i < rows; i++) {
            map[i] = lines[i].toCharArray();
        }

        return map;
    }

    public static int simulateGuardPath(char[][] map) {
        // Directions and their deltas: up, right, down, left
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        char[] directions = {'^', '>', 'v', '<'};

        // Find the guard's starting position and direction
        int startX = -1, startY = -1, directionIndex = -1;
        
 
        
        outer:
        	
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                char cell = map[y][x];
                for (int d = 0; d < directions.length; d++) {
                    if (cell == directions[d]) {
                        startX = x;
                        startY = y;
                        directionIndex = d;
                        break outer;
                    }
                }
            }
        }

        // Track visited positions
        Set<String> visited = new HashSet<>();
        visited.add(startX + "," + startY);

        // Guard's current position
        int x = startX, y = startY;

        // Simulate guard's movement
        while (true) {
            // Calculate the next position
            int nextX = x + dx[directionIndex];
            int nextY = y + dy[directionIndex];

            // Check if the next position is out of bounds
            if (nextX < 0 || nextX >= map[0].length || nextY < 0 || nextY >= map.length) {
                break;
            }

            // Check the cell at the next position
            char nextCell = map[nextY][nextX];
            if (nextCell == '#') {
                // Obstacle encountered, turn right
                directionIndex = (directionIndex + 1) % 4;
            } else {
                // Move forward
                x = nextX;
                y = nextY;
                visited.add(x + "," + y);
            }
            
            
            
        }

        return visited.size();
    }
}