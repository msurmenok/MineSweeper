package edu.sjsu.cs.cs151.controller;

import edu.sjsu.cs.cs151.model.Model;

public class GameInfo {
	private int[][] gameStatus;
	private final int CLOSED = -2;
	private final int FLAGGED = 10;
	private final int WRONGFLAG = 20;
	private int h;
	private int w;
	private int numOfMines;
	// didn't set values for other scenarios such as mine, empty cell etc/
	// will follow Inhee's code by setting mine be -1, empty cell be 0 and number be
	// 1-8(adjacent # of mines)

	/**
	 * Default GameInfo with h in height and w in width. By default, -2 represents
	 * closed cell.
	 * 
	 * @param h
	 *            height of the mineField
	 * @param w
	 *            width of the mineField
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
	 * @return gameStatus
	 */
	public int[][] getGameStatus(){
		return gameStatus;
	}
	
	
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
	
	public int getHeight() {
		return this.h;
	}
	public int getWidth() {
		return this.w;
	}
	public int getNumOfMines() {
		return this.numOfMines;
	}

}
