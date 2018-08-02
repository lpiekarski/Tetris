package game;

import java.util.ArrayList;

/**
 * A class managing the grid tiles configurations referred to as the tetrominos.
 * 
 * @author £ukasz Piekarski [wookashp98@gmail.com]
 */
public class Tetromino {
	/**
	 * A list of x coordinates of corresponding tiles. 0 is the center with respect
	 * to which the tetromino can be rotated.
	 */
	private ArrayList<Integer> blockConfigurationX;

	/**
	 * A list of y coordinates of corresponding tiles. 0 is the center with respect
	 * to which the tetromino can be rotated.
	 */
	private ArrayList<Integer> blockConfigurationY;

	/**
	 * A list of Tile objects of corresponding tiles.
	 * 
	 * @see game.Tile
	 */
	private ArrayList<Tile> blockConfigurationTile;

	/**
	 * Creates a new tetromino from the given configuration.
	 * 
	 * @param blockConfigurationX    A value for
	 *                               {@link game.Tetromino#blockConfigurationX}.
	 * @param blockConfigurationY    A value for
	 *                               {@link game.Tetromino#blockConfigurationY}.
	 * @param blockConfigurationTile A value for
	 *                               {@link game.Tetromino#blockConfigurationTile}.
	 */
	public Tetromino(int[] blockConfigurationX, int[] blockConfigurationY, Tile[] blockConfigurationTile) {
		this.blockConfigurationX = new ArrayList<Integer>();
		this.blockConfigurationY = new ArrayList<Integer>();
		this.blockConfigurationTile = new ArrayList<Tile>();
		for (int i = 0; i < blockConfigurationTile.length; i++)
			addTile(blockConfigurationX[i], blockConfigurationY[i], blockConfigurationTile[i]);
	}

	/**
	 * Creates a new empty tetromino.
	 */
	public Tetromino() {
		this.blockConfigurationX = new ArrayList<Integer>();
		this.blockConfigurationY = new ArrayList<Integer>();
		this.blockConfigurationTile = new ArrayList<Tile>();
	}

	/**
	 * Gets the specific tile of the tetromino.
	 * 
	 * @param id Index of the tile to retrieve.
	 * @return A Tile object of the tetromino piece described at index
	 *         <code>id</code>.
	 * @throws UndefinedTileException when there is no tile with index
	 *                                <code>id</code>.
	 */
	public Tile getTile(int id) throws UndefinedTileException {
		if (id < 0 || id >= blockConfigurationTile.size())
			throw new UndefinedTileException();
		return blockConfigurationTile.get(id).copy();
	}

	/**
	 * Gets the x coordinate of the specific tetromino tile.
	 * 
	 * @param id Index of the tile.
	 * @return Tile's x coordinate.
	 * @throws UndefinedTileException when there is no tile with index
	 *                                <code>id</code>.
	 */
	public int getTileX(int id) throws UndefinedTileException {
		if (id < 0 || id >= blockConfigurationX.size())
			throw new UndefinedTileException();
		return blockConfigurationX.get(id);
	}

	/**
	 * Gets the y coordinate of the specific tetromino tile.
	 * 
	 * @param id Index of the tile.
	 * @return Tile's y coordinate or 0 if tile with index <code>id</code> doesn't
	 *         exist.
	 * @throws UndefinedTileException when there is no tile with index
	 *                                <code>id</code>.
	 */
	public int getTileY(int id) throws UndefinedTileException {
		if (id < 0 || id >= blockConfigurationY.size())
			throw new UndefinedTileException();
		return blockConfigurationY.get(id);
	}

	/**
	 * Adds a new tile to the tetromino configuration with <code>x</code> and
	 * <code>y</code> coordinates. If there was any tile with such coordinates the
	 * new one overrides it.
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param t The tile object.
	 */
	public void addTile(int x, int y, Tile t) {
		blockConfigurationX.add(x);
		blockConfigurationY.add(y);
		blockConfigurationTile.add(t.copy());
	}

	/**
	 * Creates a new tetromino object with the exact same field values.
	 * 
	 * @return A copy of the tetromino.
	 */
	public Tetromino copy() {
		return new Tetromino(blockConfigurationX.stream().mapToInt(Integer::intValue).toArray(),
				blockConfigurationY.stream().mapToInt(Integer::intValue).toArray(),
				blockConfigurationTile.toArray(new Tile[blockConfigurationTile.size()]));
	}

	/**
	 * Creates a rotated version of the tetromino.
	 * @param counterclockwise	determines the rotation direction.
	 * @return	Rotated version of the tetromino.
	 */
	public Tetromino rotate(boolean counterclockwise) {
		Tetromino t = new Tetromino();
		for (int i = 0; i < blockConfigurationX.size(); i++) {
			int x = blockConfigurationX.get(i);
			int y = blockConfigurationY.get(i);
			Tile tile = blockConfigurationTile.get(i);
			if(counterclockwise)
				t.addTile(y, -x, tile);
			else
				t.addTile(-y, x, tile);
		}
		return t;
	}
}
