package edu.sjsu.cs.cs151.tests;

import static org.junit.Assert.*;
import org.junit.*;

import edu.sjsu.cs.cs151.model.Cell;

public class TestCell {

	/**
	 * Test that toggleFlag function can set a flag to a cell
	 */
	@Test
	public void testToggleFlagSet() {
		// flagged = true;
		Cell cell = new Cell(3);
		cell.toggleFlag();
		assertTrue(cell.isFlagged() == true);
	}

	/**
	 * Test that toggleFlag function can remove a flag if it was previously set
	 */
	@Test
	public void testToggleFlagRemove() {
		// flagged = false;
		Cell cell = new Cell(3);
		cell.toggleFlag();
		cell.toggleFlag();
		assertTrue(cell.isFlagged() == false);
	}

	@Test
	public void testSetOpen() {
		// open = true;
		Cell cell = new Cell(3);
		cell.setOpen();
		assertTrue(cell.isOpened());
	}
}
