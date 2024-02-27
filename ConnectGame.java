package hw3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Tile;

/**
 * Class that models a ConnectGame.
 * @author Ethan Griesman 3/31/2023
 * Submitted: 4/14/2023
 */
public class ConnectGame {
	
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	// An ArrayList storing the tiles which have been selected in the game
	private List<Tile> tilesSelected;
	// Variable representing grid object in grid class.
	private Grid gameGrid; 
	// Random number generator with a given seed
	private Random random;
	// Variable representing the width of the grid (num of cols)
	private int gridWidth;
	// Variable representing the height of the grid (num of rows)
	private int gridHeight;
	// Default player score at beginning of game
	private long playerScore;
	// Minimum tile level specified at beginning of game
	private int minLevel;
	// Maximum tile level specified at beginning of game
	private int maxLevel;
	// Checks to see if selection is in progress
	private boolean selectionInProgress;
	
	/**
	 * Constructs a new ConnectGame object with given grid dimensions and minimum
	 * and maximum tile levels.
	 * 
	 * @param width  Width of grid.
	 * @param height Height of grid.
	 * @param min    Minimum level n for a tile 2^n
	 * @param max    Maximum level n for a tile 2^n
	 * @param rand   Random number generator with seed.
	 */
	public ConnectGame(int width, int height, int min, int max, Random rand) {
		// Assuming max is > min and min is > 0, create min and max tile levels
		if (max > min && min > 0) {
			this.maxLevel = max;
			this.minLevel = min;
		}
		
		// Assuming width and height are positive, create a new grid with a width and height
		if (width > 0 && height > 0) {
			// Link gameGrid variable to grid class which creates a new grid containing tiles
			this.gameGrid = new Grid(width, height);
			// Initialize grid width
			this.gridWidth = width;
			// Initialize grid height
			this.gridHeight = height;
		}
		
		this.random = rand;
		// Initialize dynamic array list for tiles to be added into when selected.
		tilesSelected = new ArrayList<>();
	}
	
	/**
	 * Gets a random tile with level between minimum tile level inclusive and
	 * maximum tile level exclusive. For example, if minimum is 1 and maximum is 4,
	 * the random tile can be either 1, 2, or 3.
	 * <p>
	 * DO NOT RETURN TILES WITH MAXIMUM LEVEL
	 * 
	 * @return a tile with random level between minimum inclusive and maximum
	 *         exclusive
	 */
	public Tile getRandomTile() {
		// Check if minimum level is less than maximum level
	    if (minLevel < maxLevel) {
	        // Create a new Random object to generate a random number
	        int randomLevel = minLevel + random.nextInt(maxLevel - minLevel);
	        // Create a new Tile object using the random level and return it
	        return new Tile(randomLevel);
	    } else {
	        // Otherwise, return null if minimum level is greater than or equal to maximum level
	        return null;
	    }
	}

	/** 
	 * Regenerates the grid with all random tiles produced by getRandomTile().
	 */
	public void radomizeTiles() {
		// Loop through each row of the grid
	    for (int y = 0; y < gameGrid.getHeight(); y++) {
	        // Loop through each column of the grid
	        for (int x = 0; x < gameGrid.getWidth(); x++) {
	            // Get a new random tile
	            Tile tile = getRandomTile();
	            if (tile != null) {
	                // Set the new random tile into the tile at the current position in the grid
	            	gameGrid.setTile(tile, x, y);
	            } else {
	                // If a null tile was generated, try again
	                x--;
	            }
	        } 
	    }
	}
	
	/**
	 * Determines if two tiles are adjacent to each other. The may be next to each
	 * other horizontally, vertically, or diagonally.
	 * 
	 * @param t1, one of the two tiles
	 * @param t2, one of the two tiles
	 * @return true if they are next to each other horizontally, vertically, or
	 *         diagonally on the grid, false otherwise
	 */
	public boolean isAdjacent(Tile t1, Tile t2) {
		if (t1 == null || t2 == null) {
	        // If either tile is null, they can't be adjacent
	        return false;
	    }
		// differential x and y to determine adjacency.
	    int dx = Math.abs(t1.getX() - t2.getX());
	    int dy = Math.abs(t1.getY() - t2.getY());

	    // if these distances are less than or equal to one.
	    if ((dx <= 1 && dy <= 1) || (dx == 1 && dy == 1)) {
	        // Horizontal, vertical, or diagonal adjacency
	        return true;
	    }
	    return false;
	}
	
	/**
	 * Indicates the user is trying to select (clicked on) a tile to start a new
	 * selection of tiles.
	 * <p>
	 * If a selection of tiles is already in progress, the method should do nothing
	 * and return false.
	 * <p>
	 * If a selection is not already in progress (this is the first tile selected),
	 * then start a new selection of tiles and return true.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return true if this is the first tile selected, otherwise false
	 */
	public boolean tryFirstSelect(int x, int y) {
		// First, check if a selection is already in progress or grid is null
	    if (selectionInProgress || gameGrid == null) {
	    	// selection process has already begun, return false
	        return false;
	    }
	    
	    // Get the tile at the specified x, y on the grid.
	    Tile tile = gameGrid.getTile(x, y);
	    
	    // Tile not found, do nothing
	    if (tile == null) {
	        return false;
	    }
	    
	    // Tile exists, start selection process
	    selectionInProgress = true;
	    
	    // Mark this tile as selected
	    tile.setSelect(true);
	    
	    // add this selected tile into the ArrayList of selected tiles to begin selection process.
	    tilesSelected.add(tile);
	    return true;
	}
	
	/**
	 * Indicates the user is trying to select (mouse over) a tile to add to the
	 * selected sequence of tiles. The rules of a sequence of tiles are:
	 * 
	 * <pre>
	 * 1. The first two tiles must have the same level.
	 * 2. After the first two, each tile must have the same level or one greater than the level of the previous tile.
	 * </pre>
	 * 
	 * For example, given the sequence: 1, 1, 2, 2, 2, 3. The next selected tile
	 * could be a 3 or a 4. If the use tries to select an invalid tile, the method
	 * should do nothing. If the user selects a valid tile, the tile should be added
	 * to the list of selected tiles.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 */
	public void tryContinueSelect(int x, int y) {
		// Check if a selection is in progress and if grid exists and x, y are within bounds of the grid
	    if (this.selectionInProgress && gameGrid != null && x >= 0 && x < gameGrid.getWidth() && y >= 0 && y < gameGrid.getHeight()) {
	        // If no tile is selected, this is the first selected tile.
	        if (tilesSelected.isEmpty()) {
	        	//redirect to tryFirstSelect
	            tryFirstSelect(x, y);
	        } else {
	            // If a tile is already selected, get the tile at (x, y)
	            Tile newTile = gameGrid.getTile(x, y);
	            
	            // If this new tile hasn't been selected already...
	            if (!newTile.isSelected()) {
	                // And if the level of the new tile is the same as the level of the first selected tile or the list of selected tiles is empty
	                if (tilesSelected.isEmpty() || newTile.getLevel() == tilesSelected.get(0).getLevel()) {
	                    // Set the new tile as having been selected
	                    newTile.setSelect(true);
	                    // Add this new tile to the selected tiles list
	                    tilesSelected.add(newTile);
	                }
	                // Else, if the level of the new tile is greater than the level of the previous tile by 1 or the same as the previous tile level
	                else if (tilesSelected.get(tilesSelected.size()-1) != null && 
	                         (newTile.getLevel() == tilesSelected.get(tilesSelected.size()-1).getLevel()+1 ||
	                          newTile.getLevel() == tilesSelected.get(tilesSelected.size()-1).getLevel())) {
	                    // Set the new tile as having been selected
	                    newTile.setSelect(true);
	                    // Add this new tile to the selected tiles
	                    tilesSelected.add(newTile);
	                }
	                // If the level of the new tile is not valid, end the selection process
	                else {
	                    // Selection now not in progress
	                    selectionInProgress = false;
	                    // For each tile within the selected tiles list
	                    for (Tile tile : tilesSelected) {
	                        // Set them all to false
	                        tile.setSelect(false);
	                    }
	                    // Clear the list
	                    tilesSelected.clear();
	                }
	            }
	            
	            // If the second-to-last selected tile is unselected, remove the last tile from selectedTiles
	            else if (tilesSelected.size() >= 2 && tilesSelected.get(tilesSelected.size()-2).equals(newTile)) {
	                Tile lastTile = tilesSelected.remove(tilesSelected.size()-1);
	                lastTile.setSelect(false);
	            }
	        }
	    }
	}

	/**
	 * Indicates the user is trying to finish selecting (click on) a sequence of
	 * tiles. If the method is not called for the last selected tile, it should do
	 * nothing and return false. Otherwise it should do the following:
	 * 
	 * <pre>
	 * 1. When the selection contains only 1 tile reset the selection and make sure all tiles selected is set to false.
	 * 2. When the selection contains more than one block:
	 *     a. Upgrade the last selected tiles with upgradeLastSelectedTile().
	 *     b. Drop all other selected tiles with dropSelected().
	 *     c. Reset the selection and make sure all tiles selected is set to false.
	 * </pre>
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return return false if the tile was not selected, otherwise return true
	 */
	public boolean tryFinishSelection(int x, int y) {
		if (tilesSelected == null || tilesSelected.isEmpty()) {
	        return false;
	    }

	    // Check if the last tile selected is the same as the tile at (x, y)
	    Tile lastTile = tilesSelected.get(tilesSelected.size() - 1);
	    if (lastTile.getX() != x || lastTile.getY() != y) {
	        return false;
	    }

	    boolean result = true;
	    int totalValue = 0; // initialize total value to 0
	    // Upgrade the last selected tile with upgradeLastSelectedTile().
	    upgradeLastSelectedTile();

	    // Calculate total value of all selected tiles
	    for (Tile tile : tilesSelected) {
	        // calculates the total value which is 2^n, n being the level
	        totalValue += Math.pow(2, tile.getLevel()); // multiply value by 2^level
	        // mark all tiles as unselected
	        tile.setSelect(false);
	    }

	    // Add value of last tile if only one tile is selected
	    if (tilesSelected.size() == 1) {
	        totalValue += lastTile.getValue();
	    }

	    // Set score to total value
	    setScore(getScore() + totalValue);
	    dropSelected();
	    
	    // Reset selection
	    selectionInProgress = false;
	    tilesSelected.clear();
	    return result;
	}
	
	/**
	 * Increases the level of the last selected tile by 1 and removes that tile from
	 * the list of selected tiles. The tile itself should be set to unselected.
	 * <p>
	 * If the upgrade results in a tile that is greater than the current maximum
	 * tile level, both the minimum and maximum tile level are increased by 1. A
	 * message dialog should also be displayed with the message "New block 32,
	 * removing blocks 2". Not that the message shows tile values and not levels.
	 * Display a message is performed with dialogListener.showDialog("Hello,
	 * World!");
	 */
	public void upgradeLastSelectedTile() {
		// Check if any tile is selected
	    if (tilesSelected == null || tilesSelected.isEmpty()) {
	        return; // If no tile is selected, return and exit method
	    }
	    
	    // Get the last tile that was selected
	    Tile lastTile = tilesSelected.get(tilesSelected.size() - 1);
	    
	    // Add the value of the last tile to the player's score.
	    playerScore += lastTile.getValue();
	    
	    // Remove the last tile from the list of selected tiles
	    tilesSelected.remove(tilesSelected.size() - 1);
	    
	    // Unselect the last tile
	    lastTile.setSelect(false);
	    
	    // Increase the level of the last tile by 1
	    lastTile.setLevel(lastTile.getLevel() + 1);

	    
	    // Check if the new level of the tile is greater than the current max level
	    if (lastTile.getLevel() + 1 > maxLevel) {
	        // If so, increase both the max and min level by 1
	        maxLevel++;
	        minLevel++;
	        
	        // Show a dialog box indicating the new max level and min level
	        if (dialogListener != null) {
	            dialogListener.showDialog("New block " + Math.pow(2, maxLevel) + ", removing blocks " + Math.pow(1, minLevel - 1));
	        } else {
	            System.out.println("dialogListener is null"); // Add this line to help with debugging
	        }
	    }
	}

	/**
	 * Gets the selected tiles in the form of an array. This does not mean selected
	 * tiles must be stored in this class as a array.
	 * 
	 * @return the selected tiles in the form of an array
	 */
	public Tile[] getSelectedAsArray() {
		
		// Declare a new 1D array equal to a new tile object with the current size of the selected tiles
	    Tile[] selectedTilesArray = new Tile[tilesSelected.size()];
	    
	    // For every element within the selected tiles
	    for (int i = 0; i < tilesSelected.size(); i++) {
	    	// Set the element in the 1D array equal to the element in the corresponding arraylist
	        selectedTilesArray[i] = tilesSelected.get(i);
	    }
	    // Return this array.
	    return selectedTilesArray;
	    
	} 

	/**
	 * Removes all tiles of a particular level from the grid. When a tile is
	 * removed, the tiles above it drop down one spot and a new random tile is
	 * placed at the top of the grid.
	 * 
	 * @param level the level of tile to remove
	 */
	public void dropLevel(int level) {
		// Loop through each column in the grid
		for (int col = 0; col < getWidth(); col++) {
			// Loop through each row in the column
	        for (int row = 0; row < gameGrid.getHeight(); row++ ) {
	        	// Check if the tile at this location has the specified level
	            if (gameGrid.getTile(col, row).getLevel() == level) {
	            	// Shift all tiles above this one down by one
	                for (int y = row; y > 0; --y) {
	                    gameGrid.setTile(gameGrid.getTile(col, y-1), col, y);
	                    gameGrid.getTile(col,  y).setLocation(col, y);
	                }
	                // Place a new random tile at the top of the column
	                gameGrid.setTile(getRandomTile(), col, 0);
	                gameGrid.getTile(col, 0).setLocation(col, 0);
	                
	            }
	        }
	    }
	}
	
	/**
	 * Removes all selected tiles from the grid. When a tile is removed, the tiles
	 * above it drop down one spot and a new random tile is placed at the top of the
	 * grid.
	 */
	public void dropSelected() {
		// Loop through each selected tile
		for (int i = 0; i < tilesSelected.size(); i++) {
			// Get the current tile's row and column
	        Tile currentTile = tilesSelected.get(i);
	        int row = currentTile.getY();
	        int col = currentTile.getX();
	        
	        // Shift all tiles above this one down by one until gaps are filled
	        for (int y = row; y > 0; --y) {
	            gameGrid.setTile(gameGrid.getTile(col, y-1), col, y);
	            // Set the new location of the tile
	            gameGrid.getTile(col,  y).setLocation(col, y);
	        }
	        // Place a new random tile at the top of the column
	        gameGrid.setTile(getRandomTile(), col, 0);
	        // Set the location of the new random tile.
	        gameGrid.getTile(col, 0).setLocation(col, 0);
	     
	    }
	}
	
	/**
	 * Remove the tile from the selected tiles.
	 * 
	 * @param x column of the tile
	 * @param y row of the tile
	 */
	public void unselect(int x, int y) { 
		// Create an iterator for the selected tiles arrayList
		Iterator<Tile> iterator = tilesSelected.iterator();
		// Iterate through each selected tile
	    while (iterator.hasNext()) {
	    	// Get the next tile in the iterator
	        Tile tile = iterator.next();
	        // If the tile is at the given (x,y) location, unselect it and remove it from the list of selected tiles
	        if (tile.getX() == x && tile.getY() == y) {
	            tile.setSelect(false);
	            // Exit the loop once a tile has been unselected and removed
	            iterator.remove();
	            break;
	        }
	    }
	    // Update player score
	    scoreListener.updateScore(playerScore);
	}
	/**
	 * Gets the player's score.
	 * 
	 * @return The player's score
	 */
	public long getScore() {
		return playerScore;
	}
	/**
	 * Gets the game grid.
	 * 
	 * @return the grid
	 */
	public Grid getGrid() {
		return this.gameGrid;
	}
	/**
	 * Gets the minimum tile level.
	 * 
	 * @return the minimum tile level
	 */
	public int getMinTileLevel() {
		return minLevel;
	}
	/**
	 * Gets the maximum tile level.
	 * 
	 * @return the maximum tile level
	 */
	public int getMaxTileLevel() {
		return maxLevel;
	}
	/**
	 * Helper method for load/save
	 * @return grid width
	 */
	public int getWidth() {
		gridWidth = gameGrid.getWidth();
		return gridWidth;
	}
	/**
	 * Helper method for load/save
	 * @param x coordinate
	 * @param y coordinate
	 * @return tile at x, y
	 */
	public Tile getTile(int x, int y) {
		Tile tile = gameGrid.getTile(x, y);
		return tile;
	}
	/**
	 * Helper method for load/save
	 * @return gridHeight
	 */
	public int getHeight() {
		gridHeight = gameGrid.getHeight();
		return gridHeight;
	}
	/**
	 * Sets the game's grid.
	 * 
	 * @param grid game's grid
	 */
	public void setGrid(Grid grid) {
		this.gameGrid = grid;
	}
	/**
	 * Sets the minimum tile level.
	 * 
	 * @param minTileLevel the lowest level tile
	 */
	public void setMinTileLevel(int minTileLevel) {
		this.minLevel = minTileLevel;
	}
	/**
	 * Sets the maximum tile level.
	 * 
	 * @param maxTileLevel the highest level tile
	 */
	public void setMaxTileLevel(int maxTileLevel) {
		this.maxLevel = maxTileLevel;
	}
	/**
	 * Sets the player's score.
	 * 
	 * @param score number of points
	 */
	public void setScore(long score) {
		this.playerScore = score;
	}
	/**
	 * Helper method for save
	 * @param width
	 */
	public void setWidth(int width) {
		this.gridWidth = width;
	}
	/**
	 * Helper method for save
	 * @param height 
	 */
	public void setHeight(int height) {
		this.gridHeight = height;
	}
	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}
	/**
	 * Save the game to the given file path.
	 * 
	 * @param filePath location of file to save
	 * @throws IOException 
	 */
	public void save(String filePath) throws IOException {
		GameFileUtil.save(filePath, this);
	}
	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 * @throws FileNotFoundException 
	 */
	public void load(String filePath) throws FileNotFoundException {
		GameFileUtil.load(filePath, this);
	}
}
