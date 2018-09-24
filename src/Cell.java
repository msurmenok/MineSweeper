/**
 * Cell is a unit on the mine field that stores all kinds of cell.
 * @author jing
 *
 */
public class Cell {
	private boolean isFlagged;
	private boolean isOpen;
	private boolean isMine;
	
	/**
	 * By default, a cell is not flagged and is not visible when initiate at the beginning.
	 */
	public Cell() {
		isFlagged = false;
		isOpen = false;
	}
	/**
	 * The method to flag a cell
	 */
	public void setFlag() {
		isFlagged = true;
	}
	
	/**
	 * The method to unflag a cell
	 */
	public void removeFlag() {
		isFlagged = false;
	}
	
	/**
	 * The method to open a cell
	 */
	public void openCell() {
		isOpen = true;
	}
}
