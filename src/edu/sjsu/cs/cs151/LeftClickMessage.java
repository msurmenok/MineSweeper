package edu.sjsu.cs.cs151;

/**
 * Member of a BlockingQueue that provides information if a cell was clicked by
 * left-click
 */
public class LeftClickMessage extends Message {
	int height;
	int width;

	/**
	 * Constructor that keeps information of a cell's coordinates
	 * 
	 * @param height
	 *            is a row of the cell
	 * @param width
	 *            is a column of the cell
	 */
	public LeftClickMessage(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * Accessor to height variable
	 * 
	 * @return the row of a clicked cell
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Accessor to width variable
	 * 
	 * @return the column of a clicked cell
	 */
	public int getWidth() {
		return width;
	}
}
