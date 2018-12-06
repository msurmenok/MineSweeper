package edu.sjsu.cs.cs151.tests;

import static org.junit.Assert.*;
import org.junit.*;

import edu.sjsu.cs.cs151.model.MineCounter;

public class TestMineCounter {
	@Test
	public void testGetCurrentNumOfMine() {
		MineCounter counter = new MineCounter(8);
		assertEquals(counter.getCurrentNumOfMine(), 8);
	}

	@Test
	public void testDecreaseMine() {
		MineCounter counter = new MineCounter(8);
		counter.decreaseMine();
		assertEquals(counter.getCurrentNumOfMine(), 7);
	}

	@Test
	public void testIncreaseMine() {
		MineCounter counter = new MineCounter(5);
		counter.increaseMine();
		assertEquals(counter.getCurrentNumOfMine(), 6);
	}
}
