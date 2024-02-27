package hw3;

import api.Tile;

/**
 * Represents the game's grid.
 */
public class Grid {
	Tile[][] gridOfTiles;

	/**
	 * Creates a new grid.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public Grid(int width, int height) {
		if (width > 0 && height > 0) {
            gridOfTiles = new Tile[height][width];
        }
	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width of grid
	 */
	public int getWidth() {
		if (gridOfTiles != null) {
			return gridOfTiles[0].length;
		}
		return 0;
	}
	
	/**
	 * Get the grid's height.
	 * 
	 * @return height of grid
	 */
	public int getHeight() {
		if (gridOfTiles != null) {
			return gridOfTiles.length;
		}
		return 0;
	}

	/**
	 * Gets the tile for the given column and row.
	 * 
	 * @param x 	the column of the grid
	 * @param y		the row of the grid
	 * @return the tile at this given x and y coordinate
	 */
	public Tile getTile(int x, int y) {
		 // Check if the specified location is within the bounds of the grid
	    if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
	        // Return the tile at the specified location
	        return gridOfTiles[y][x];
	    }
		return null;
	  
	}
  
	/**
	 * Sets the tile for the given column and row. Calls tile.setLocation().
	 * 
	 * @param tile the tile to set
	 * @param x    the column of the grid
	 * @param y    the row of the grid
	 */
	public void setTile(Tile tile, int x, int y) {
		// Check if the given x and y values are within the bounds of the grid
	    if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
	        // Set the tile at the specified x and y coordinates in the grid
	        gridOfTiles[y][x] = tile;
	        // Set the location of the tile to the specified coordinates
	        tile.setLocation(x, y);
	    }
	} 
	
	@Override
	public String toString() {
		String str = "";
		for (int y=0; y<getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			str += "[";
			for (int x=0; x<getWidth(); x++) {
				if (x > 0) {
					str += ",";
				}
				str += getTile(x, y);
			}
			str += "]";
		}
		return str;
	}


	
}
