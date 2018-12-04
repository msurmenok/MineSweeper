package edu.sjsu.cs.cs151.model;

import java.util.Date;

/**
 * Model that contains state of all objects of the game
 */
public class Model {

	private MineField mineField;
	// private Date staringTime;
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
	 * Reset state. Reinitialize field with mines
	 */
	public Model restartGame() {
		return new Model(currDifficulty);
	}

	/**
	 * Handles user click on the mine field
	 * 
	 * @param x
	 *            x coordinate of clicked cell
	 * @param y
	 *            y coordinate of clicked cell
	 */
	public void openCell(int h, int w) {
		gameContinue = this.mineField.open(h, w);
	}

	/**
	 * If cell is already flagged, remove flag, otherwise set flag
	 * 
	 * @param h
	 * 
	 * @param w
	 *            y coordinate
	 */
	public void toggleCellFlag(int h, int w) {
		this.mineField.toggleFlag(h, w);
		if (this.mineField.cells[h][w].isFlagged()) {
			//this.mineCounter.increaseMine();
			this.mineCounter.decreaseMine(); // should crease
		} else {
			//this.mineCounter.decreaseMine();
			this.mineCounter.increaseMine(); // should increase
		}

	}

	/**
	 * Record the staring time of the first click
	 */
	public void setStartingTime() { // Thinking to move this part to game

	}

	public boolean getGameStatus() {
		return gameContinue;
	}

	public boolean isWin() {
		if (gameContinue) {
			if (this.mineField.getNumOfTotalCells() - this.mineField.getNumOfOpenedCells() == this.mineField
					.getNumOfMines()) {
				return true;
			}
		}
		return false;
	}

	public void setLose() {
		if (!gameContinue) {
			isLose = true;
		}
	}

	/**
	 * Get current mineField info
	 * 
	 * @return mindField information
	 */
	public MineField getMineField() {
		return mineField;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void gameOver() {
		System.out.println("Game over! Please start a new game!");
	}

	public void gameWin() {
		System.out.println("Bravo! You won!");
	}

  // initial number of mines
	public int getNumOfMines() {
		return this.numberOfMines;
	}

  // change in number of mines reflecting flags
	public int getNumOfMinesFromCounter() {
		return this.mineCounter.getCurrentNumOfMine();
	}

}
