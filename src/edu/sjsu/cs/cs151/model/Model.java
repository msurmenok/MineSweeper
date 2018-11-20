package edu.sjsu.cs.cs151.model;
import java.util.Date;

/**
 * Model that contains state of all objects of the game
 * 
 * @author msurmenok
 * 
 */
public class Model {
	private MineField mineField;
	//private Date staringTime;
	private MineCounter mineCounter;
	
	private int height;
	private int width;
	private int numberOfMines;

	/**
	 * Initialize state for a new game
	 */
	public Model(Difficulty difficulty) {
		if (difficulty == Difficulty.EASY)
		{
			this.height = 8;
			this.width = 8;
			this.numberOfMines = 4;
		}
		
		this.mineField = new MineField(height, width, numberOfMines);
		this.mineCounter = new MineCounter(this.numberOfMines);
	}

	/**
	 * Reset state. Reinitialize field with mines
	 */
	void restartGame() {

	}

	/**
	 * Handles user click on the mine field
	 * 
	 * @param x
	 *            x coordinate of clicked cell
	 * @param y
	 *            y coordinate of clicked cell
	 */
	void openCell(int x, int y) {

	}

	/**
	 * If cell is already flagged, remove flag, otherwise set flag
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	void toggleCellFlag(int x, int y) {

	}

	/**
	 * Record the staring time of the first click
	 */
	void setStartingTime() {

	}

}
