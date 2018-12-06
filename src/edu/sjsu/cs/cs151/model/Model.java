package edu.sjsu.cs.cs151.model;

import java.util.Date;

/**
 * Model that contains state of all objects of the game
 */
public class Model {

	private MineField mineField;
	private MineCounter mineCounter;

	private int height;
	private int width;
	private int numberOfMines;

	public boolean gameContinue;
	// public boolean isWin;
	public boolean isLose;
	private Difficulty currDifficulty;

	/**
	 * Initialize state for a new game
	 * 
	 * @param difficulty
	 *            can be EASY, MEDIUM, or HARD
	 */
	public Model(Difficulty difficulty) {
		if (difficulty == Difficulty.EASY) {
			this.height = DifficultyLevel.EASY;
			this.width = DifficultyLevel.EASY;
			this.numberOfMines = DifficultyLevel.EASY_MINES;

		} else if (difficulty == Difficulty.MEDIUM) {
			this.height = DifficultyLevel.MEDIUM;
			this.width = DifficultyLevel.MEDIUM;
			this.numberOfMines = DifficultyLevel.MEDIUM_MINES;

		} else if (difficulty == Difficulty.HARD) {
			this.height = DifficultyLevel.HARD;
			this.width = DifficultyLevel.HARD;
			this.numberOfMines = DifficultyLevel.HARD_MINES;
		}

		this.mineField = new MineField(height, width, numberOfMines);
		this.mineCounter = new MineCounter(this.numberOfMines);
		this.currDifficulty = difficulty;
		gameContinue = true;
		isLose = false;
		// isWin = false;
	}

	/**
	 * Reset state. Reinitialize model and all its variables
	 * 
	 * @return new model with specified difficulty level
	 */
	public Model restartGame() {
		return new Model(currDifficulty);
	}

	/**
	 * Handles user click on the mine field
	 * 
	 * @param h
	 *            height of the clicked cell (its row)
	 * @param w
	 *            width of the clicked cell (its column)
	 */
	public void openCell(int h, int w) {
		gameContinue = this.mineField.open(h, w);
	}

	/**
	 * If cell is already flagged, remove flag, otherwise set flag
	 * 
	 * @param h
	 *            height of the clicked cell (its row)
	 * 
	 * @param w
	 *            width of the clicked cell (its column)
	 */
	public void toggleCellFlag(int h, int w) {
		this.mineField.toggleFlag(h, w);
		if (this.mineField.cells[h][w].isFlagged()) {
			// this.mineCounter.increaseMine();
			this.mineCounter.decreaseMine(); // should crease
		} else {
			// this.mineCounter.decreaseMine();
			this.mineCounter.increaseMine(); // should increase
		}

	}

	/**
	 * Check if game still on
	 * 
	 * @return true if game continues
	 */
	public boolean getGameStatus() {
		return gameContinue;
	}

	/**
	 * Check if user has won. Conditions to win: game is still continuing, all cells
	 * that are not mines are opened
	 * 
	 * @return true if user has won
	 */
	public boolean isWin() {
		if (gameContinue) {
			if (this.mineField.getNumOfTotalCells() - this.mineField.getNumOfOpenedCells() == this.mineField
					.getNumOfMines()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set isLose to true if user has clicked on mine cell
	 */
	public void setLose() {
		if (!gameContinue) {
			isLose = true;
		}
	}

	/**
	 * Get current mineField info
	 * 
	 * @return mineField information
	 */
	public MineField getMineField() {
		return mineField;
	}

	/**
	 * Accessor to field height
	 * 
	 * @return the number of rows in mineField
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Accessor to field width
	 * 
	 * @return the number of columns in mineField
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Method for debugging, prints on console if the game is over
	 */
	public void gameOver() {
		System.out.println("Game over! Please start a new game!");
	}

	/**
	 * Method for debugging, prints on console if user has won
	 */
	public void gameWin() {
		System.out.println("Bravo! You won!");
	}

	/**
	 * Accessor to numberOfMines
	 * 
	 * @return how many mines are hidden on the field
	 */
	public int getNumOfMines() {
		return this.numberOfMines;
	}

	/**
	 * Return the estimated number of mines, which is number of real mines - number
	 * of flags
	 * 
	 * @return estimated number of remaining mines
	 */
	public int getNumOfMinesFromCounter() {
		return this.mineCounter.getCurrentNumOfMine();
	}

}
