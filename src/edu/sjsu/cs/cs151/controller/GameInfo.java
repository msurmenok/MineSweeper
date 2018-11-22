package edu.sjsu.cs.cs151.controller;

import edu.sjsu.cs.cs151.model.Model;

public class GameInfo {
	private int[][] gameStatus;
	private final int CLOSED = -2;
	private final int FLAGGED = 10;
	private final int isMine = -1;
	
	/**
	 * Default GameInfo with h in height and w in width. 
	 * By default, -2 represents closed cell.
	 * @param h height of the mineField
	 * @param w width of the mineField
	 */
	public GameInfo(Model model) {
		int h = model.getHeight();
		int w = model.getWidth();
		gameStatus = new int[h][w];
		for(int i = 0; i< h; i++) {
			for(int j = 0;j<w; j++) {
				gameStatus[i][j] = CLOSED;
			}
		}
		
	}
	
	public void gameInfoUpdate(int i, int j, int updateValue) {
		gameStatus[i][j] = updateValue;
	}
	
	public int flag() {
		return FLAGGED;
	}
	
	public int mine() {
		return isMine;
	}
	
	public void print() { //for debugging use only
		int h = gameStatus.length;
		int w = gameStatus[0].length;
		for(int i = 0; i< h; i++) {
			System.out.print("[");
			for(int j = 0;j<w; j++) {
				System.out.print(" " + gameStatus[i][j] + ",");
			}
			System.out.print("]\n");
		}
	}
	
	
}
