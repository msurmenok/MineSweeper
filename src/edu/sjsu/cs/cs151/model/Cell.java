package edu.sjsu.cs.cs151.model;

/**
 * Represents one cell in a field. Keep information about surrounding mines, its
 * flag, and if it was opened.
 *
 */
public class Cell {
	private int adjacentMines;
	private boolean hasFlagSet;
	private boolean isOpened;

	/**
	 * Create a cell with specified number of adjacent mines
	 * 
	 * @param adjacentMines
	 */
	public Cell(int adjacentMines) {
		this.adjacentMines = adjacentMines;
		hasFlagSet = false;
		isOpened = false;
	}

	/**
	 * Accessor method for adjacentMines variable
	 * 
	 * @return number of mines surrounding this cell
	 */
	public int getAdjacentMines() {
		return this.adjacentMines;
	}

	/**
	 * Set flag if a cell was not previously flagged, otherwise unflag the cell
	 * 
	 * @return true if flag was set
	 */
	public boolean toggleFlag() {
		// hasFlagSet => unset Flag (i.e. !hasFlagSet)
		// !hasFlagSet => set Flag (i.e. hasFlagSet)
		hasFlagSet = !hasFlagSet;
		return hasFlagSet;
	}

	/**
	 * Accessor for hasFlagSet variable
	 * 
	 * @return true if the cell is flagged
	 */
	public boolean isFlagged() {
		return hasFlagSet;
	}

	/**
	 * Mutator to variable isOpen. Indicates if a cell was clicked by user
	 */
	public void setOpen() {
		isOpened = true;
	}

	/**
	 * Accessor to isOpened variable
	 * 
	 * @return true if cell was opened by user
	 */
	public boolean isOpened() {
		return this.isOpened;
	}
}