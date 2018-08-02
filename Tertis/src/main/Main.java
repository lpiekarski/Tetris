package main;

import java.awt.Color;

import game.Tetromino;
import game.TetrominoSpawner;
import game.Tile;

/**
 * Main function class of a basic tetris game.
 * @see		<a href="https://en.wikipedia.org/wiki/Tetris">https://en.wikipedia.org/wiki/Tetris</a>
 * @author	£ukasz Piekarski [wookashp98@gmail.com]
 */
public class Main {
	/**
	 * Main game function. Creates game window, starts the game.
	 * @param args	command line arguments.
	 */
	public static void main(String[] args) {
		new gameFrame(); 
		TetrominoSpawner.addTetrominoType(
				new Tetromino(new int[] {0, 1, -1, 2}, new int[] {0, 0, 0, 0},
					new Tile[] {
							new Tile(false, Color.red, false),
							new Tile(false, Color.red, false),
							new Tile(false, Color.red, false),
							new Tile(false, Color.red, false)
					}));
		TetrominoSpawner.addTetrominoType(
				new Tetromino(new int[] {0, 0, 1, -1}, new int[] {0, 1, 0, 0}, 
					new Tile[] {
							new Tile(false, Color.gray, false),
							new Tile(false, Color.gray, false),
							new Tile(false, Color.gray, false),
							new Tile(false, Color.gray, false)
					}));
		TetrominoSpawner.addTetrominoType(
				new Tetromino(new int[] {0, 1, 0, 1}, new int[] {0, 0, 1, 1},
					new Tile[] {
							new Tile(false, Color.cyan, false),
							new Tile(false, Color.cyan, false),
							new Tile(false, Color.cyan, false),
							new Tile(false, Color.cyan, false)
					}));
		TetrominoSpawner.addTetrominoType(
				new Tetromino(new int[] {0, 1, -1, -1}, new int[] {0, 0, 0, 1},
					new Tile[] {
							new Tile(false, Color.yellow, false),
							new Tile(false, Color.yellow, false),
							new Tile(false, Color.yellow, false),
							new Tile(false, Color.yellow, false)
					}));
		TetrominoSpawner.addTetrominoType(
				new Tetromino(new int[] {0, 1, -1, 1}, new int[] {0, 0, 0, 1},
					new Tile[] {
							new Tile(false, Color.magenta, false),
							new Tile(false, Color.magenta, false),
							new Tile(false, Color.magenta, false),
							new Tile(false, Color.magenta, false)
					}));
		TetrominoSpawner.addTetrominoType(
				new Tetromino(new int[] {0, 1, -1, 0}, new int[] {0, 0, 1, 1},
					new Tile[] {
							new Tile(false, Color.blue, false),
							new Tile(false, Color.blue, false),
							new Tile(false, Color.blue, false),
							new Tile(false, Color.blue, false)
					}));
		TetrominoSpawner.addTetrominoType(
				new Tetromino(new int[] {0, 1, -1, 0}, new int[] {1, 1, 0, 0},
					new Tile[] {
							new Tile(false, Color.green, false),
							new Tile(false, Color.green, false),
							new Tile(false, Color.green, false),
							new Tile(false, Color.green, false)
					}));
		GameManager.getInstance().resumeGame();
	}
}
