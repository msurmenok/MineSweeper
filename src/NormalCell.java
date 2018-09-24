/**
 * A subclass of Cell that stores empty cell
 * @author jing
 *
 */
public class NormalCell extends Cell{
	private int numOfAdjMines;
	
	
	public NormalCell() {
		numOfAdjMines = 0;
	}
	
	/**
	 * Override openCell of super class to open the cell and update the number of 
	 * adjacent cent mines
	 */
	@Override
	public void openCell() {
		super.openCell();
		//TODO: call countAdjMines()
	}
	/**
	 * Count the number of adjacent mines
	 * @return
	 */
	private int countAdjMines() {
		//TODO: count mines and update numOfAdjMines
		return numOfAdjMines;
	}
}
