package day6;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GuardLoopFinder {

    public static void main(String[] args) {
        // Input file path
        File file = new File("GuardMap.txt");

        try {
        	
        	
            // Read the map from the file
            char[][] map = readMapFromFile(file);

            // Find positions that cause a loop
            int loopPositions = findLoopCausingPositions(map);

            System.out.println("Number of positions that can cause a loop: " + loopPositions);
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

    /**
     * 
     * @param map
     * @return
     */
    public static int findLoopCausingPositions(char[][] map) {
        int rows = map.length;
        int cols = map[0].length;

        // Directions and their deltas: up, right, down, left
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        char[] directions = {'^', '>', 'v', '<'};

        // Find the guard's starting position and direction
        int startX = -1, startY = -1, startDirection = -1;
        outer:
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                char cell = map[y][x];
                for (int d = 0; d < directions.length; d++) {
                    if (cell == directions[d]) {
                        startX = x;
                        startY = y;
                        startDirection = d;
                        break outer;
                    }
                }
            }
        }

        // Track the number of positions that cause a loop
        int loopPositions = 0;

        // Try placing an obstruction at every empty position
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                // Skip the starting position and existing obstacles
                if ((x == startX && y == startY) || map[y][x] == '#') {
                    continue;
                }

                // Temporarily place an obstruction
                map[y][x] = '#';

                // Simulate the guard's path
                if (causesLoop(map, startX, startY, startDirection, dx, dy, directions)) {
                    loopPositions++;
                }

                // Remove the obstruction
                map[y][x] = '.';
            }
        }

        return loopPositions;
    }

    /**
     * 
     * @param map
     * @param startX
     * @param startY
     * @param startDirection
     * @param dx
     * @param dy
     * @param directions
     * @return
     */
    public static boolean causesLoop(char[][] map, int startX, int startY, int startDirection, int[] dx, int[] dy, char[] directions) {
        Set<String> visitedStates = new HashSet<>();
        int x = startX, y = startY, direction = startDirection;

        while (true) {
            // Encode the current state (position + direction)
            String state = x + "," + y + "," + direction;

            // Check if we've visited this state before
            if (visitedStates.contains(state)) {
                return true; // A loop is detected
            }
            visitedStates.add(state);

            // Calculate the next position
            int nextX = x + dx[direction];
            int nextY = y + dy[direction];

            // Check if the next position is out of bounds
            if (nextX < 0 || nextX >= map[0].length || nextY < 0 || nextY >= map.length) {
                return false; // Guard leaves the map
            }

            // Check the next cell
            char nextCell = map[nextY][nextX];
            if (nextCell == '#') {
                // Obstacle encountered, turn right
                direction = (direction + 1) % 4;
            } else {
                // Move forward
                x = nextX;
                y = nextY;
            }
        }
    }
}
