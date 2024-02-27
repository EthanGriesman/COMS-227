package hw3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import api.Tile;

/**
 * Utility class with static methods for saving and loading game files.
 */
public class GameFileUtil {
	/**
	 * Saves the current game state to a file at the given file path.
	 * <p>
	 * The format of the file is one line of game data followed by multiple lines of
	 * game grid. The first line contains the: width, height, minimum tile level,
	 * maximum tile level, and score. The grid is represented by tile levels. The
	 * conversion to tile values is 2^level, for example, 1 is 2, 2 is 4, 3 is 8, 4
	 * is 16, etc. The following is an example:
	 * 
	 * <pre>
	 * 5 8 1 4 100
	 * 1 1 2 3 1
	 * 2 3 3 1 3
	 * 3 3 1 2 2
	 * 3 1 1 3 1
	 * 2 1 3 1 2
	 * 2 1 1 3 1
	 * 4 1 3 1 1
	 * 1 3 3 3 3
	 * </pre>
	 * 
	 * @param filePath the path of the file to save
	 * @param game     the game to save
	 * @throws IOException 
	 */
	public static void save(String filePath, ConnectGame game) throws IOException {
		
		// Calculate the maximum tile level based on the game's max level
	    int maxLevel = game.getMaxTileLevel();
	    
	    // Create a BufferedWriter instance to write data to a file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	        // Write game data to file
	        writer.write(game.getGrid().getWidth() + " " + game.getGrid().getHeight() + " " +
	                     game.getMinTileLevel() + " " + game.getMaxTileLevel() + " " +
	                     game.getScore() + "\n");
	        
	        // Write grid data to file
	        for (int y = 0; y < game.getGrid().getHeight(); y++) {
	        	
	            for (int x = 0; x < game.getGrid().getWidth(); x++) {
	            	
	                // Get the value of the tile at (x, y)
	                int value = game.getGrid().getTile(x, y).getValue();
	                // Calculate the level of the tile based on its value
	                int level = game.getGrid().getTile(x, y).getLevel();
	                // Cap the level of the tile if it exceeds the max tile level allowed in the game
	                if (level > maxLevel) {
	                    level = maxLevel;
	                }
	                
	                // Write the level of the tile to the file
	                writer.write(Integer.toString(level));
	                
	                // Add a space if there are more tiles in the row
	                if (x < game.getGrid().getWidth() - 1) {
	                    writer.write(" ");
	                }
	            }
	            	if (y < game.getGrid().getHeight() - 1) {
	            		// Add a new line after each row of tiles
	            		writer.write("\n");
	            	}
	    }
	    }
	}
	
	/**
	 * 
	 * @param string
	 * @param game
	 * @throws FileNotFoundException
	 */
	public static void load(String filePath, ConnectGame game) throws FileNotFoundException {
		
		// Create a Scanner object to read from the file specified in the file path
		try (Scanner scanner = new Scanner(new File(filePath))) {
	        // Read game data from file
	        String[] gameData = scanner.nextLine().split(" ");
	        
	        // Parse the game data values from the string array
	        int width = Integer.parseInt(gameData[0]);
	        int height = Integer.parseInt(gameData[1]);
	        int minTileLevel = Integer.parseInt(gameData[2]);
	        int maxTileLevel = Integer.parseInt(gameData[3]);
	        int score = Integer.parseInt(gameData[4]);

	        // Initialize the game grid if necessary based on the width and height values
	        if (game.getGrid() == null || game.getGrid().getWidth() != width || game.getGrid().getHeight() != height) {
	        	// Create a new Grid object with the specified width and height
	            game.setGrid(new Grid(width, height));
	            // Update the game's width and height values
	            game.setWidth(width);
	            game.setHeight(height);
	        }
	        
	        // Set the game's minimum and maximum tile level and score based on the values read from the file
	        game.setMinTileLevel(minTileLevel);
	        game.setMaxTileLevel(maxTileLevel);
	        game.setScore(score);

	        // Read the remaining lines of the file, which contain grid data separated by spaces
	        for (int y = 0; y < height; y++) {
	        	// Split the current line into an array of string values
	            String[] row = scanner.nextLine().split(" ");
	            // Loop through the values in the row array and parse them into integers
	            for (int x = 0; x < width; x++) {
	                int value = Integer.parseInt(row[x]);
	                // If the value is greater than 0, create a new Tile object with the appropriate level and add it to the game grid at the specified positio
	                if (value > 0) {
	                    int level = value - 1;
	                    Tile tile = new Tile(level);
	                    game.getGrid().setTile(tile, x, y);
	                }
	            }
	        }
	    }
	}
}
