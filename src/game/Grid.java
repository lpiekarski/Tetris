package game;

import java.awt.Graphics2D;

import properties.GameSettings;

/**
 * Class used for both managing and drawing the grid of tiles. All of the fields
 * and methods are static.
 * 
 * @author £ukasz Piekarski [wookashp98@gmail.com]
 */
public class Grid {
	/**
	 * Two dimensional grid of tiles. Size is determined in GameSettings.
	 * 
	 * @see properties.GraphicsSettings#GAME_GRID_WIDTH
	 * @see properties.GraphicsSettings#GAME_GRID_HEIGHT
	 * @see game.Tile
	 */
	private static Tile[][] tiles = new Tile[GameSettings.GRID_WIDTH][GameSettings.GRID_HEIGHT];

	/**
	 * Method constructing all the tiles in the grid as empty tiles with null color
	 * and with no tetromino hover.
	 * 
	 * @see game.Tile
	 * @see game.Tile#Tile(boolean, java.awt.Color, boolean)
	 */
	public static void initTiles() {
		for (int x = 0; x < GameSettings.GRID_WIDTH; x++) {
			for (int y = 0; y < GameSettings.GRID_HEIGHT; y++) {
				tiles[x][y] = new Tile(true, null, false);
			}
		}
	}

	/**
	 * Returns the tile at x, y.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @return The tile at x, y position or null if there is no tile at position x,
	 *         y.
	 */
	public static Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= GameSettings.GRID_WIDTH || y >= GameSettings.GRID_HEIGHT)
			return null;
		return tiles[x][y];
	}

	/**
	 * Changes the tile at position x, y.
	 * 
	 * @param x X coordinate of the tile to change.
	 * @param y Y coordinate of the tile to change.
	 * @param t The new tile.
	 */
	public static void setTile(int x, int y, Tile t) {
		if (x < 0 || y < 0 || x >= GameSettings.GRID_WIDTH || y >= GameSettings.GRID_HEIGHT)
			return;
		tiles[x][y] = t;
	}

	/**
	 * Removes rows filled with tiles.
	 * 
	 * @return number of rows removed.
	 */
	public static int removeFullRows() {
		int rowsRemoved = 0;
		for (int y = GameSettings.GRID_HEIGHT - 1; y >= 0; y--) {
			boolean full = true;
			for (int x = 0; x < GameSettings.GRID_WIDTH; x++) {
				if (tiles[x][y].isEmpty()) {
					full = false;
					break;
				}
			}
			if (full) {
				rowsRemoved++;
				for (int yy = y; yy > 0; yy--) {
					for (int xx = 0; xx < GameSettings.GRID_WIDTH; xx++) {
						tiles[xx][yy] = tiles[xx][yy - 1];
					}
				}
				for (int xx = 0; xx < GameSettings.GRID_WIDTH; xx++) {
					tiles[xx][0] = new Tile(true, null, false);
				}
				y++;
			}
		}
		return rowsRemoved;
	}

	/**
	 * Draws the grid.
	 * 
	 * @param g       Graphical context.
	 * @param offsetX Distance in pixels between the left edge of the frame and the
	 *                left edge of the grid.
	 * @param offsetY Distance in pixels between the top edge of the frame and the
	 *                top edge of the grid.
	 * @param width   Distance in pixels between left and right edges of the grid.
	 * @param height  Distance in pixels between top and bottom edges of the grid.
	 */
	public static void draw(Graphics2D g, int offsetX, int offsetY, int width, int height) {
		for (int x = 0; x < GameSettings.GRID_WIDTH; x++) {
			for (int y = 0; y < GameSettings.GRID_HEIGHT; y++) {
				if (tiles[x][y] == null)
					continue;
				tiles[x][y].draw(g, offsetX + x * width / GameSettings.GRID_WIDTH,
						offsetY + y * height / GameSettings.GRID_HEIGHT, width / GameSettings.GRID_WIDTH,
						height / GameSettings.GRID_HEIGHT);
			}
		}
	}
}
