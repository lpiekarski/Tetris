package game;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import properties.GameSettings;

/**
 * Class managing the part of the game where a new tetromino has to be spawned
 * at the top of the grid and has to be moved gradually down the grid.
 * 
 * @author £ukasz Piekarski [wookashp98@gmail.com]
 */
public class TetrominoSpawner {
	/**
	 * A list of tetromino types that possibly can be spawned.
	 */
	private static ArrayList<Tetromino> availableTetrominos = new ArrayList<>();

	/**
	 * A queue of tetrominos to spawn.
	 */
	private static Queue<Tetromino> tetrominoQueue = new LinkedList<>();

	/**
	 * Random number generator.
	 */
	private static Random random = new Random();

	/**
	 * Currently spawned tetromino that is moving through the grid.
	 */
	public static Tetromino spawned;

	/**
	 * X coordinate of the spawned tetromino.
	 */
	private static int spawnedX;

	/**
	 * Y coordinate of the spawned tetromino.
	 */
	private static int spawnedY;

	/**
	 * Adds a new tetromino type that can be possibly spawned.
	 * 
	 * @param t Tetromino
	 */
	public static void addTetrominoType(Tetromino t) {
		if (t == null)
			return;
		availableTetrominos.add(t.copy());
	}

	/**
	 * Clears the possible tetromino types list.
	 */
	public static void clearTetrominoTypes() {
		availableTetrominos.clear();
	}

	/**
	 * Checks if there is any spawned tetromino in the grid.
	 * 
	 * @return true if there is tetromino spawned, false otherwise.
	 */
	public static boolean isAnySpawned() {
		return !(spawned == null);
	}

	/**
	 * Sets the random number generator seed.
	 * 
	 * @param seed seed
	 */
	public static void setSeed(long seed) {
		random = new Random(seed);
	}

	/**
	 * Adds a new tetromino into the queue to spawn.
	 */
	public static void generateNextTetromino() {
		if (availableTetrominos.isEmpty())
			return;
		tetrominoQueue.add(availableTetrominos.get(random.nextInt(availableTetrominos.size())).copy());
	}

	/**
	 * Spawns a tetromino from the queue and removes it from there.
	 * 
	 * @return true if the tetromino was successfully spawned, false otherwise.
	 */
	public static boolean spawnTetromino() {
		if (tetrominoQueue.isEmpty())
			return false;
		int minH = 0, maxH = 0, minW = 0, maxW = 0;
		Tetromino t = tetrominoQueue.poll();
		int id = 0;
		while (true) {
			try {
				int x = t.getTileX(id);
				int y = t.getTileY(id);
				if (id == 0) {
					maxH = y;
					minH = y;
					maxW = x;
					minW = x;
				} else {
					maxH = Math.max(maxH, y);
					minH = Math.min(minH, y);
					maxW = Math.max(maxW, x);
					minW = Math.min(minW, x);
				}
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
		int offsetX, offsetY;
		offsetX = (GameSettings.GRID_WIDTH - maxW - minW) / 2;
		offsetY = -minH;
		id = 0;
		while (true) {
			try {
				int x = t.getTileX(id);
				int y = t.getTileY(id);
				Tile tile = Grid.getTile(x + offsetX, y + offsetY);
				if (tile == null || !tile.isEmpty())
					return false;
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
		spawned = t;
		spawnedX = offsetX;
		spawnedY = offsetY;
		id = 0;
		while (true) {
			try {
				int x = t.getTileX(id);
				int y = t.getTileY(id);
				Tile tile = t.getTile(id);
				Grid.getTile(x + offsetX, y + offsetY).fill(tile.getColor());
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
		TetrominoSpawner.hover();
		return true;
	}

	/**
	 * Moves spawned tetromino in specified direction if only possible.
	 * 
	 * @param dx          Change in x coordinate direction.
	 * @param dy          Change in y coordinate direction.
	 * @param changeHover determines whether or not function should change the
	 *                    tetromino hover location.
	 * @return true if tetromino was moved, false otherwise.
	 */
	public static boolean moveSpawned(int dx, int dy, boolean changeHover) {
		int id = 0;
		while (true) {
			try {
				int x = spawned.getTileX(id);
				int y = spawned.getTileY(id);
				Grid.setTile(x + spawnedX, y + spawnedY, new Tile(true, null, false));
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
		id = 0;
		while (true) {
			try {
				int x = spawned.getTileX(id);
				int y = spawned.getTileY(id);
				Tile tile = Grid.getTile(x + spawnedX + dx, y + spawnedY + dy);
				if (tile == null || !tile.isEmpty()) {
					id = 0;
					while (true) {
						try {
							x = spawned.getTileX(id);
							y = spawned.getTileY(id);
							Grid.setTile(x + spawnedX, y + spawnedY, spawned.getTile(id).copy());
							id++;
						} catch (UndefinedTileException e) {
							break;
						}
					}
					return false;
				}
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
		spawnedX += dx;
		spawnedY += dy;
		id = 0;
		while (true) {
			try {
				int x = spawned.getTileX(id);
				int y = spawned.getTileY(id);
				Grid.setTile(x + spawnedX, y + spawnedY, spawned.getTile(id).copy());
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
		if (changeHover)
			TetrominoSpawner.hover();
		return true;
	}

	/**
	 * Rotates spawned tetromino in specified direction if only it's possible.
	 * 
	 * @param counterclockwise direction of rotation.
	 * @return true if tetromino was rotated, false otherwise.
	 */
	public static boolean rotateSpawned(boolean counterclockwise) {
		if (spawned == null)
			return false;
		int id = 0;
		while (true) {
			try {
				int x = spawned.getTileX(id);
				int y = spawned.getTileY(id);
				Grid.setTile(x + spawnedX, y + spawnedY, new Tile(true, null, false));
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
		id = 0;
		Tetromino rotated = spawned.rotate(counterclockwise);
		while (true) {
			try {
				int x = rotated.getTileX(id);
				int y = rotated.getTileY(id);
				Tile tile = Grid.getTile(x + spawnedX, y + spawnedY);
				if (tile == null || !tile.isEmpty()) {
					id = 0;
					while (true) {
						try {
							x = spawned.getTileX(id);
							y = spawned.getTileY(id);
							Grid.setTile(x + spawnedX, y + spawnedY, spawned.getTile(id).copy());
							id++;
						} catch (UndefinedTileException e) {
							break;
						}
					}
					return false;
				}
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
		spawned = rotated;
		id = 0;
		while (true) {
			try {
				int x = spawned.getTileX(id);
				int y = spawned.getTileY(id);
				Grid.setTile(x + spawnedX, y + spawnedY, spawned.getTile(id).copy());
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
		TetrominoSpawner.hover();
		return true;
	}

	/**
	 * Moves spawned tetromino one tile left.
	 * 
	 * @return true if tetromino was successfully moved, false otherwise.
	 */
	public static boolean moveLeft() {
		if (spawned == null)
			return false;
		return moveSpawned(-1, 0, true);
	}

	/**
	 * Moves the spawned tetromino one tile right.
	 * 
	 * @return true if tetromino was successfully moved, false otherwise.
	 */
	public static boolean moveRight() {
		if (spawned == null)
			return false;
		return moveSpawned(1, 0, true);
	}

	/**
	 * Moves the spawned tetromino one tile down.
	 * 
	 * @param changeHover determines whether or not function should change the
	 *                    tetromino hover location.
	 * 
	 * @return true if tetromino was successfully moved, false otherwise.
	 */
	public static boolean moveDown(boolean changeHover) {
		if (spawned == null)
			return false;
		return moveSpawned(0, 1, changeHover);
	}

	/**
	 * Moves the spawned tetromino down until it can't move further down.
	 * 
	 * @param changeHover determines whether or not function should change the
	 *                    tetromino hover location.
	 */
	public static void skipDown(boolean changeHover) {
		while (moveDown(changeHover))
			;
	}

	public static void hover() {
		for (int y = 0; y < GameSettings.GRID_HEIGHT; y++) {
			for (int x = 0; x < GameSettings.GRID_WIDTH; x++) {
				Grid.getTile(x, y).setTetrominoHover(false);
			}
		}
		if (spawned == null)
			return;
		int sy = spawnedY;
		skipDown(false);
		Dimension res = new Dimension(spawnedX, spawnedY);
		moveSpawned(0, sy - spawnedY, false);
		int id = 0;
		while (true) {
			try {
				int x = spawned.getTileX(id);
				int y = spawned.getTileY(id);
				Grid.getTile(x + res.width, y + res.height).setTetrominoHover(true);
				id++;
			} catch (UndefinedTileException e) {
				break;
			}
		}
	}
}
