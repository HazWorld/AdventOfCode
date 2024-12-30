/**
 * 
 */
package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 */
public class XmasFinder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		char[][] grid = readInToGrid();
		
		String word = "XMAS"; 
		
		
		
		System.out.println(countOccurrences(grid, word));
		
		System.out.println(countXMasOccurrences(grid));

	}

	/**
	 * reads in txt file to char grid
	 * 
	 * @return
	 */
	public static char[][] readInToGrid() {

		File file = new File("XMASinput.txt");

		List<char[]> gridList = new ArrayList<char[]>();

		try {

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {

				String line = scanner.nextLine().trim();

				gridList.add(line.toCharArray());

			}

			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		char[][] grid = new char[gridList.size()][];

		for (int i = 0; i <= gridList.size() - 1; i++) {
			grid[i] = gridList.get(i);
		}

		return grid;

	}

	
	/**
	 * 
	 * @param grid
	 * @param word
	 * @return
	 */
	public static int countOccurrences(char[][] grid, String word) {
		
		int rows = grid.length;
		int cols = grid[0].length;
		int count = 0;
		
		
		int[][] directions = { { 0, 1 }, // Right
				
				{ 0, -1 }, // Left
				{ 1, 0 }, // Down
				{ -1, 0 }, // Up
				{ 1, 1 }, // Diagonal Down-Right
				{ 1, -1 }, // Diagonal Down-Left
				{ -1, 1 }, // Diagonal Up-Right
				{ -1, -1 } // Diagonal Up-Left
		};

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				for (int[] direction : directions) {
					if (isWordFound(grid, word, row, col, direction[0], direction[1])) {
						count++;
					}
				}
			}
		}

		return count;
	}

	/**
	 * 
	 * @param grid
	 * @param word
	 * @param startRow
	 * @param startCol
	 * @param rowDelta
	 * @param colDelta
	 * @return
	 */
	public static boolean isWordFound(char[][] grid, String word, int startRow, int startCol, int rowDelta, int colDelta) {
		
		int rows = grid.length;
		int cols = grid[0].length;
		int wordLength = word.length();

		for (int i = 0; i < wordLength; i++) {
			int newRow = startRow + i * rowDelta;
			int newCol = startCol + i * colDelta;

			// Check bounds
			if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
				return false;
			}

			// Check character match
			if (grid[newRow][newCol] != word.charAt(i)) {
				return false;
			}
		}

		return true;
	}
	
	 public static int countXMasOccurrences(char[][] grid) {
	        int rows = grid.length;
	        int cols = grid[0].length;
	        int count = 0;

	        // Iterate through each cell in the grid
	        for (int row = 1; row < rows - 1; row++) {
	            for (int col = 1; col < cols - 1; col++) {
	                // Check if the current cell can be the center of an X-MAS
	                if (grid[row][col] == 'A' && isXMas(grid, row, col)) {
	                    count++;
	                }
	            }
	        }
	        return count;
	    }

	    public static boolean isXMas(char[][] grid, int centerRow, int centerCol) {
	        // Check diagonals for MAS or SAM patterns
	        return (matchesPattern(grid, centerRow - 1, centerCol - 1, centerRow + 1, centerCol + 1) && // Top-left to bottom-right
	                matchesPattern(grid, centerRow - 1, centerCol + 1, centerRow + 1, centerCol - 1)) || // Top-right to bottom-left
	               (matchesPattern(grid, centerRow - 1, centerCol - 1, centerRow + 1, centerCol + 1) && // Top-left to bottom-right
	                matchesPattern(grid, centerRow + 1, centerCol - 1, centerRow - 1, centerCol + 1)); // Bottom-left to top-right
	    }

	    public static boolean matchesPattern(char[][] grid, int row1, int col1, int row2, int col2) {
	        // Validate boundaries
	        if (row1 < 0 || col1 < 0 || row2 >= grid.length || col2 >= grid[0].length) {
	            return false;
	        }

	        // Extract characters
	        char char1 = grid[row1][col1];
	        char char2 = grid[row2][col2];

	        // Check for MAS or SAM pattern
	        return (char1 == 'M' && char2 == 'S') || (char1 == 'S' && char2 == 'M');
	    }

}
