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
	//public boolean isWin;
	public boolean isLose;

	/**
	 * Initialize state for a new game
	 */
	public Model(Difficulty difficulty) {
		if (difficulty == Difficulty.EASY) {
			this.height = 8;
			this.width = 8;
			this.numberOfMines = 4;
		}

		this.mineField = new MineField(height, width, numberOfMines);
		this.mineCounter = new MineCounter(this.numberOfMines);
		gameContinue = true;
		isLose = false;
		//isWin = false;
	}

	/**
	 * Reset state. Reinitialize field with mines
	 */
	public Model restartGame() {
		 return new Model(Difficulty.EASY);
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
			this.mineCounter.increaseMine();
		} else {
			this.mineCounter.decreaseMine();
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
	

}
