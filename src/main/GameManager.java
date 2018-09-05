package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import game.Grid;
import game.TetrominoSpawner;
import properties.GameSettings;

/**
 * Class managing the course of the game.
 * Class is implemented in the singleton convention.
 * @author £ukasz Piekarski [wookashp98@gmail.com]
 */
public class GameManager implements ActionListener {
	/**
	 * Singleton
	 */
	private static GameManager instance = null;
	
	/**
	 * Number of player's points acquired in the current game.
	 */
	private int points = 0;

	/**
	 * The static method for global class object access.
	 * @return	The singleton.
	 */
	public static GameManager getInstance() {
		if (instance == null)
			instance = new GameManager();
		return instance;
	}

	/**
	 * Timer triggering the singleton when the new game step has to be performed.
	 */
	private Timer gameTimer;

	/**
	 * Creates a new GameManager object. Notice that this is the only constructor 
	 * and it is private therefore there cannot be any other object of this class besides the singleton.
	 */
	private GameManager() {
		gameTimer = new Timer(GameSettings.GAME_TEMPO, this);
		Grid.initTiles();
	}

	/**
	 * Checks if the GameManager notifying timer is running.
	 * @return true if timer is stopped, false otherwise.
	 */
	public boolean isPaused() {
		return !gameTimer.isRunning();
	}

	/**
	 * Stops the GameManager notifying timer.
	 */
	public void pauseGame() {
		if (gameTimer.isRunning())
			gameTimer.stop();
	}

	/**
	 * Starts the GameManager notifying timer.
	 */
	public void resumeGame() {
		if (!gameTimer.isRunning())
			gameTimer.start();
	}
	
	public void gameOver() {
		System.out.println("you lost!");
		points = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(TetrominoSpawner.isAnySpawned()) {
			if (TetrominoSpawner.moveDown(true)) {
				System.out.println("Tetromino moved down.");
			}
			else {
				TetrominoSpawner.spawned = null;
				System.out.println("Tetromino stopped.");
				points += Math.pow(4, Grid.removeFullRows());
				System.out.println("points: "+points);
			}
		}
		if(!TetrominoSpawner.isAnySpawned()) {
			TetrominoSpawner.generateNextTetromino();
			if (!TetrominoSpawner.spawnTetromino())
				gameOver();
			else
				System.out.println("New tetromino spawned.");
			
		}
	}

	/**
	 * Get player's current points number.
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

}
