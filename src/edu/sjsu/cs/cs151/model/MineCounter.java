package edu.sjsu.cs.cs151.model;

/**
 * Keeps track of remaining mines. This number doesn't reflect the real
 * situation because it decrease number of mines when user flags a cell.
 */
public class MineCounter {

	private int numOfMines;

	public MineCounter(int numbOfMines) {
		this.numOfMines = numbOfMines;
	}

	/**
	 * Number of mines is decreased by 1 upon setFlag()
	 */
	public void decreaseMine() {
		numOfMines--;
	}

	/**
	 * Number of mines is increased by 1 when removeFlag()
	 */
	public void increaseMine() {
		numOfMines++;
	}

	/**
	 * Accessor to number of mines.
	 * 
	 * @return current number of mines instead of initial number
	 */
	public int getCurrentNumOfMine() {
		return this.numOfMines;
	}
}
