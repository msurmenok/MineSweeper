package edu.sjsu.cs.cs151.model;

import java.util.ArrayList;

/**
 * MineField class is a 2d array that represents a field of mines
 */
//public class MineField {
public class MineField implements BoundaryChecker { // proxy pattern 
	Cell[][] cells;// visibility to package so Model.java can visit
	private int height;
	private int width;
	private int numberOfMines;
	private int numberOfCells;
	private boolean[] mineBoolean;
	private int numberOfOpenedCells;

	/**
	 * Generate mine field for the first time
	 * 
	 * @param height
	 *            vertical number of cells
	 * @param width
	 *            horizontal number of cells
	 * @param mines
	 *            number of mines randomly placed in the field
	 */
	public MineField(int height, int width, int mines) {
		this.height = height;
		this.width = width;
		numberOfCells = height * width;
		mineBoolean = new boolean[numberOfCells];
		this.numberOfMines = mines;
		// generateMineField();
		cells = new Cell[height][width];
		this.calcAdjacentMines();
		this.numberOfOpenedCells = 0;
		// addMouseListener(new GameMouseAdapter());
	}

	/**
	 * Check out how many mines near the specific cell
	 * 
	 * @param x
	 *            x coordinate of the cell
	 * @param y
	 *            y coordinate of the cell
	 * @return number of mines nearby
	 */
	public int calculateAdjacentMineNumbers(int x, int y) {
		int mineCounter = 0;
		// y - rows, x - columns.
		for (int i = y - 1; i < y + 2; i++) {
			for (int j = x - 1; j < x + 2; j++) {
				// Check if index is out of bounds.
				// If it is outside of the array, skip this iteration.
				if (x == -1 || y == -1 || x == cells[0].length || y == cells.length) {
					continue;
				}
				// Increase counter if the specified cell is mine.
				// if (this.getCell(i, j).isMine()) {
				if (hasMine(i, j)) {
					mineCounter++;
				}
			}
		}
		return mineCounter;
	}

	/**
	 * Check if the specific cell is a mine
	 * 
	 * @param h
	 *            height of the cell (row)
	 * @param w
	 *            width of the cell (column)
	 * @return true if the cell is a mine
	 */
	private boolean hasMine(int h, int w) {
		if (!boundaryProxy(h, w)) 
			return false;
		//if (h < 0 || height <= h || w < 0 || width <= w)
		//	return false;
		int i = h * width + w;
		if (mineBoolean[i])
			return true;
		else
			return false;
	}

	public void generateMineField() // Model:Method
	{
		// set mines WITHOUT DUPLICATE RANDOM NUMBERS
		// 1st. set a cell True if the cell index < # of mines
		for (int i = 0; i < numberOfCells; i++) {
			if (i < numberOfMines)
				mineBoolean[i] = true;
			else
				mineBoolean[i] = false;
		}
		// set mines WITHOUT DUPLICATE RANDOM NUMBERS
		// 2nd. swapping the randomly selected cells
		for (int i = 0; i < numberOfCells; i++) {
			int index = new java.util.Random().nextInt(numberOfCells - i);
			// swap two m[i] and m[index]
			boolean temp = mineBoolean[i];
			mineBoolean[i] = mineBoolean[index];
			mineBoolean[index] = temp;
		}
	}

	/**
	 * Calculate mines in eight adjacent cells w/r/t (i, j) (i - 1, j - 1), (i - 1,
	 * j), (i - 1, j + 1) (i , j - 1), [SET -1] (i , j + 1) (i + 1, j - 1), (i + 1,
	 * j), (i + 1, j + 1)
	 *
	 * -1 if mine is set at the cell 0, 1, ..., 8 otherwise
	 *
	 */
	public void calcAdjacentMines() // Model:Method
	{
		// cells = new Cell[height][width];
		generateMineField();
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				int adjacentMines = -1; // center cell is a mine cell, set it -1
				int i = h * width + w;
				if (!mineBoolean[i]) {
					adjacentMines = calculateAdjacentMineNumbers(w, h);
					/*
					 * adjacentMines = hasMine(h - 1, w - 1); // 1 adjacentMines += hasMine(h - 1, w
					 * + 0); // 2 adjacentMines += hasMine(h - 1, w + 1); // 3 adjacentMines +=
					 * hasMine(h + 0, w - 1); // 4 adjacentMines += hasMine(h + 0, w + 1); // 5
					 * adjacentMines += hasMine(h + 1, w - 1); // 6 adjacentMines += hasMine(h + 1,
					 * w + 0); // 7 adjacentMines += hasMine(h + 1, w + 1); // 8
					 */
				}
				cells[h][w] = new Cell(adjacentMines);
			}
		}
	}

	/**
	 * Accessor to cells variable
	 * 
	 * @return all cells of the mineField
	 */
	public Cell[][] getCells() {
		// return cells[y][x];
		return cells;
	}

	/**
	 * Toggle flag on the cell
	 * 
	 * @param h
	 *            vertical position of cell
	 * @param w
	 *            horizontal position of cell
	 */
	public void toggleFlag(int h, int w) // Model:Method
	{
		Cell cell = cells[h][w];
		if (cell.isOpened()) {
			return;
		}
		cell.toggleFlag();
	}

	/**
	 * Open cell at position(int h, int w)
	 * 
	 * @param h
	 *            vertical position of cell
	 * @param w
	 *            horizontal position of cell
	 * @return false if it is a mine or true otherwise
	 */
	public boolean open(int h, int w) {
		Cell cell = cells[h][w];
		if (cell.isFlagged() || cell.isOpened()) {
			return true;
		}
		// closed cell
		int adjacentMines = cell.getAdjacentMines();
		if (adjacentMines == -1) { // stepped on the mines (lose)
			cell.setOpen();
			return false;
		}
		if (adjacentMines > 0) {
			cell.setOpen();
			numberOfOpenedCells++;
			return true;
		} else { // empty cell
			numberOfOpenedCells += openZeroArea(h, w);
			return true;
		}
	}

	private int openZeroArea(int h, int w) {
		if (!boundaryProxy(h, w)) 
			return 0;
		//if (h < 0 || height <= h || w < 0 || width <= w)
		//	return 0;

		Cell cell = cells[h][w];

		if (cell.isFlagged() || cell.isOpened())
			return 0;
		cell.setOpen();
		int opened_counter = 1;

		int adjacentMines = cell.getAdjacentMines();
		if (adjacentMines > 0) {
			return opened_counter;
		}
		// counts opened cells recursively from adjacent cells
		opened_counter += openZeroArea(h - 1, w - 1); // 1
		opened_counter += openZeroArea(h - 1, w + 0); // 2
		opened_counter += openZeroArea(h - 1, w + 1); // 3
		opened_counter += openZeroArea(h + 0, w - 1); // 4
		opened_counter += openZeroArea(h + 0, w + 1); // 5
		opened_counter += openZeroArea(h + 1, w - 1); // 6
		opened_counter += openZeroArea(h + 1, w + 0); // 7
		opened_counter += openZeroArea(h + 1, w + 1); // 8
		return opened_counter;

	}

	public int getNumOfOpenedCells() {
		return numberOfOpenedCells;
	}

	public int getNumOfTotalCells() {
		return numberOfCells;
	}

	public int getNumOfMines() {
		return numberOfMines;
	}

	// for testing only. Can delete later
	public boolean[] getMineBoolean() {
		return mineBoolean;
	}

	public boolean boundaryProxy(int h, int w) {
		if (h < 0 || height <= h || w < 0 || width <= w)
			return false;
		return true;
		}

}
