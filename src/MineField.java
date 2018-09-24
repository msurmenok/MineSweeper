
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
	int calculateAdjacentMineNumbers(int x, int y)
	{
		return 0;
	}
}
