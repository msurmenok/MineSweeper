import java.util.ArrayList;

/**
 * MineField class is a 2d array that represents a field of mines
 * @author msurmenok
 *
 */
public class MineField
{
	Cell[][] cells;
	int height;
	int width;
	
	/**
	 * Generate mine field for the first time
	 */
	public MineField()
	{
		int defaultHeight = 10;
		int defaultWidth = 20;
		int numberOfMines = 5;
		generateField(defaultHeight, defaultWidth, numberOfMines);
	}
	
	/**
	 * Create mine field and put mines in random cells
	 * @param height number of rows
	 * @param width number of columns
	 * @param numberOfMines number of mines
	 */
	public void generateField(int height, int width, int numberOfMines)
	{
		cells = new Cell[height][width];
	}
	
	/**
	 * Check out how many mines near the specific cell 
	 * @param x x coordinate of the cell
	 * @param y y coordinate of the cell
	 * @return number of mines nearby
	 */
	public int calculateAdjacentMineNumbers(int x, int y)
	{
		int mineCounter = 0;
		// y - rows, x - columns.
		for (int i = y - 1; i < y + 2; i++) {
			for (int j = x - 1; j < x + 2; j++) {
				// Check if index is out of bounds.
				// If it is outside of the array, skip this iteration.
				if ( x == -1 || y == -1 || x == cells[i].length || y == cells.length) {
					continue;
				}
				// Increase counter if the specified cell is mine.
				if (this.getCell(i, j).isMine()) {
					mineCounter++;
				}
			}
		}
		return mineCounter;
	}
	
	/**
	 * Method to get a specific cell
	 * @param x x coordinate of returning cell
	 * @param y y coordinate of returning cell
	 * @return return cell with x y coordinate
	 */
	public Cell getCell(int x, int y)
	{
		return cells[x][y];
	}
}
