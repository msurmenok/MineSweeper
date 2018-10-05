/**
 * Cell is a unit on the mine field that stores all kinds of cell.
 * @author jing
 *
 */
public class Cell {
	private boolean flagged;
	private boolean open;
	private boolean mine;
	
	/**
	 * By default, a cell is not flagged and is not visible when initiate at the beginning.
	 */
	public Cell() {
		flagged = false;
		open = false;
	}
	/**
	 * The method to flag a cell
	 */
	public void setFlag() {
		flagged = true;
	}
	
	/**
	 * The method to unflag a cell
	 */
	public void removeFlag() {
		flagged = false;
	}
	
	/**
	 * The method to open a cell
	 */
	public void openCell() {
		open = true;
	}
	
	public boolean isOpen() {
		return this.open;
	}
	
	public boolean isFlagged() {
		return this.flagged;
	}
	
	public boolean isMine() {
		return this.mine;
	}
}
