package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Class for grid tile management and drawing.
 * 
 * @author £ukasz Piekarski [wookashp98@gmail.com]
 */
public class Tile {
	/**
	 * Determines whether or not the tile is empty i.e. does it allow tetrominos to
	 * pass through.
	 */
	private boolean isEmpty;

	/**
	 * Determines the color of the tile.
	 */
	private Color color;

	/**
	 * Determines whether or not some tetromino tile will eventually fall down onto
	 * this tile.
	 */
	private boolean tetrominoHover;

	/**
	 * Creates a new tile.
	 * 
	 * @param isEmpty        A value for {@link game.Tile#isEmpty}.
	 * @param color          A value for {@link game.Tile#color}.
	 * @param tetrominoHover A value for {@link game.Tile#tetrominoHover}.
	 */
	public Tile(boolean isEmpty, Color color, boolean tetrominoHover) {
		this.isEmpty = isEmpty;
		this.color = color;
		this.tetrominoHover = tetrominoHover;
	}

	/**
	 * @return the tetrominoHover
	 */
	public boolean isTetrominoHover() {
		return tetrominoHover;
	}

	/**
	 * @param tetrominoHover the tetrominoHover to set
	 */
	public void setTetrominoHover(boolean tetrominoHover) {
		this.tetrominoHover = tetrominoHover;
	}

	/**
	 * @return the isEmpty
	 */
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Marks the tile as not empty and changes its color to <code>color</code>.
	 * 
	 * @param color New tile color.
	 */
	public void fill(Color color) {
		this.color = color;
		isEmpty = false;
	}

	/**
	 * Draws the tile.
	 * 
	 * @param g       Graphical context.
	 * @param offsetX The x coordinate of the tile's left-top corner in pixels.
	 * @param offsetY The y coordinate of the tile's left-top corner in pixels.
	 * @param width   Width of the tile in pixels.
	 * @param height  Height of the tile in pixels.
	 */
	public void draw(Graphics2D g, int offsetX, int offsetY, int width, int height) {
		if (isEmpty) {
			g.setColor(new Color(85, 85, 85));
			g.drawRect(offsetX + 1, offsetY + 1, width - 2, height - 2);
			g.setColor(new Color(45, 45, 45));
			g.fillRect(offsetX + 3, offsetY + 3, width - 4, height - 4);
		}
		else {
			g.setColor(new Color(210, 210, 210));
			g.drawRect(offsetX + 1, offsetY + 1, width - 4, height - 4);
			g.setColor(new Color(60, 60, 60));
			g.drawRect(offsetX + 2, offsetY + 2, width - 3, height - 3);
			g.setColor(color);
			g.fillRect(offsetX + 2, offsetY + 2, width - 4, height - 4);
		}
		if (tetrominoHover) {
			g.setColor(Color.white);
			g.drawRect(offsetX + 1, offsetY + 1, width - 2, height - 2);
		}
	}

	/**
	 * Creates a new tile with the exact same fields' values.
	 * 
	 * @return A copy of the tile.
	 */
	public Tile copy() {
		return new Tile(isEmpty, color, tetrominoHover);
	}
}
