/**
 * Model that contains state of all objects of the game
 * @author msurmenok
 * 
 */
public class GameModel 
{
	private MineField mineField;
	private Timer timer;
	private MineCounter mineCounter;
	
	/**
	 * Initialize state for a new game
	 */
	public GameModel()
	{
		
	}
	
	/**
	 * Reset state. Reinitialize field with mines
	 */
	void restartGame()
	{
		
	}
	
	/**
	 * Handles user click on the mine field
	 * @param x x coordinate of clicked cell
	 * @param y y coordinate of clicked cell
	 */
	void openCell(int x, int y)
	{
		
	}
	
	/** 
	 * If cell is already flagged, remove flag, otherwise set flag
	 */
	void toggleCellFlag(int x, int y)
	{
		
	}
}
