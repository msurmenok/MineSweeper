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
		//update numOfAdjMines
	}
	/**
	 * Count the number of adjacent mines
	 * @return
	 */
	private int countAdjMines() {
		//count mines
		return numOfAdjMines;
	}
}
