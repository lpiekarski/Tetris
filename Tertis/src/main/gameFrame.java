package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.Timer;

import game.Grid;
import game.TetrominoSpawner;

import static properties.GameSettings.*;

import static properties.GraphicsSettings.*;

/**
 * Class managing the main game window.
 * @author £ukasz Piekarski [wookashp98@gmail.com]
 */
public class gameFrame extends JFrame implements ActionListener, KeyListener{
	
	private BufferedImage buffer;
	
	/**
	 * Sets up the frame and starts the timer used for refreshing the graphics in the frame.
	 */
	public gameFrame() {
		this.setSize(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addKeyListener(this);
		buffer = new BufferedImage(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
		new Timer(GAME_TARGET_FRAME_DELAY, this).start();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics bg = buffer.getGraphics();
		bg.setColor(Color.black);
		bg.fillRect(0, 0, GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT);
		
		Grid.draw((Graphics2D)bg, GAME_GRID_OFFSET_X, GAME_GRID_OFFSET_Y, GAME_GRID_WIDTH, GAME_GRID_HEIGHT);
		bg.drawString("points: " + GameManager.getInstance().getPoints(), 100, 100);
		g.drawImage(buffer, 0, 0, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			TetrominoSpawner.moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			TetrominoSpawner.moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			TetrominoSpawner.moveDown(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			TetrominoSpawner.skipDown(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			TetrominoSpawner.rotateSpawned(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
