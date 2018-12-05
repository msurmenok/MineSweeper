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

	public Cell(int adjacentMines) {
		this.adjacentMines = adjacentMines;
		hasFlagSet = false;
		isOpened = false;
	}

	public int adjacentMines() {
		return this.adjacentMines;
	}

	public boolean toggleFlag() {
		// hasFlagSet => unset Flag (i.e. !hasFlagSet)
		// !hasFlagSet => set Flag (i.e. hasFlagSet)
		hasFlagSet = !hasFlagSet;
		return hasFlagSet;
	}

	public boolean isFlagged() {
		return hasFlagSet;
	}

	public void setOpen() {
		isOpened = true;
	}

	public boolean isOpened() {
		return this.isOpened;
	}
}