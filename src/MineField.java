import java.util.ArrayList;

/**
 * MineField class is a 2d array that represents a field of mines
 * 
 * @author msurmenok
 *
 */
public class MineField {
	Cell[][] cells;
	int height;
	int width;
	int numberOfMines;
	private int numberOfCells;
	private boolean[] mineBoolean;

	/**
	 * Generate mine field for the first time
	 */
	// public MineField()
	public MineField(int height, int width, int mines) {
		// int defaultHeight = 10;
		// int defaultWidth = 20;
		// int numberOfMines = 5;
		// generateField(defaultHeight, defaultWidth, numberOfMines);

		numberOfCells = height * width;
		mineBoolean = new boolean[numberOfCells];
		generateMineField();
		this.calcAdjacentMines();
		cells = new Cell[height][width];
		// addMouseListener(new GameMouseAdapter());

	}

	/**
	 * Create mine field and put mines in random cells
	 * 
	 * @param height
	 *            number of rows
	 * @param width
	 *            number of columns
	 * @param numberOfMines
	 *            number of mines public void generateField(int height, int width,
	 *            int numberOfMines) { cells = new Cell[height][width]; }
	 */

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
				if (x == -1 || y == -1 || x == cells[i].length || y == cells.length) {
					continue;
				}
				// Increase counter if the specified cell is mine.
				// if (this.getCell(i, j).isMine()) {
				if (hasMine(i, j) > 0) {
					mineCounter++;
				}
			}
		}
		return mineCounter;
	}

	private int hasMine(int h, int w) // Model:Method
	{
		if (h < 0 || height <= h || w < 0 || width <= w)
			return 0;
		int i = h * width + w;
		if (mineBoolean[i])
			return 1;
		else
			return 0;
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
			int index = new java.util.Random().nextInt(numberOfCells - i) + 1;
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
	 * Method to get a specific cell
	 * 
	 * @param x
	 *            x coordinate of returning cell
	 * @param y
	 *            y coordinate of returning cell
	 * @return return cell with x y coordinate
	 */
	// public Cell getCell(int x, int y)
	public Cell[][] getCell() {
		// return cells[y][x];
		return cells;
	}
}
