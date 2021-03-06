package edu.sjsu.cs.cs151.controller;

import edu.sjsu.cs.cs151.model.Model;

/**
 * Simplified representation of model. Controller passes it to the View to
 * redraw GUI
 * 
 *
 */
public class GameInfo {
	private int[][] gameStatus;
	public static final int CLOSED = -2;
	public static final int FLAGGED = 10;
	public static final int WRONGFLAG = 20;
	public static final int MINE = -1;
	public static final int EMPTY = 0;
	private int h;
	private int w;
	private int numOfMines;
	private boolean isWin;
	// didn't set values for other scenarios such as mine, empty cell etc/
	// will follow Inhee's code by setting mine be -1, empty cell be 0 and number be
	// 1-8(adjacent # of mines)

	/**
	 * Generates initial gameInfo with provided model
	 * 
	 * @param model
	 *            initial model
	 */
	public GameInfo(Model model) {
		h = model.getHeight();
		w = model.getWidth();
		numOfMines = model.getNumOfMines();
		gameStatus = new int[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				gameStatus[i][j] = CLOSED;
			}
		}
		isWin = false;

	}

	/**
	 * Update value at gameStatus[i][j]
	 * 
	 * @param i
	 *            row value
	 * @param j
	 *            column value
	 * @param updateValue
	 *            the to be updated value
	 */
	public void gameInfoUpdate(int i, int j, int updateValue) {
		gameStatus[i][j] = updateValue;
	}

	public int flag() {
		return FLAGGED;
	}

	public int close() {
		return CLOSED;
	}

	public int wrongFlag() {
		return WRONGFLAG;
	}

	/**
	 * Getter for gameStatus
	 * 
	 * @return gameStatus
	 */
	public int[][] getGameStatus() {
		return gameStatus;
	}

	/**
	 * Debugging method. Print gameStatus values
	 */
	public void print() { // for debugging use only
		int h = gameStatus.length;
		int w = gameStatus[0].length;
		for (int i = 0; i < h; i++) {
			System.out.print("[");
			for (int j = 0; j < w; j++) {
				System.out.print(" " + gameStatus[i][j] + ",");
			}
			System.out.print("]\n");
		}
	}

	/**
	 * Accessor to height
	 * 
	 * @return the number of rows in mineField
	 */
	public int getHeight() {
		return this.h;
	}

	/**
	 * Accessor to width
	 * 
	 * @return the number of columns in mineField
	 */
	public int getWidth() {
		return this.w;
	}

	/**
	 * Accessor to get the updated numOfMines
	 * 
	 * @return number of mines
	 */
	public int getNumOfMines() {
		return this.numOfMines;
	}

	/**
	 * Setter to set numOfmines according to the change of the model
	 * 
	 * @param mines
	 *            number of mines
	 */
	public void updateNumOfMines(int mines) {
		this.numOfMines = mines;
	}

	/**
	 * Mutator for isWin variable
	 */
	public void setWin() {
		isWin = true;
	}

	/**
	 * Accessor for isWin variable
	 * 
	 * @return if user has win
	 */
	public boolean isWin() {
		return isWin;
	}

}
