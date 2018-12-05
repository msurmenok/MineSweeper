package edu.sjsu.cs.cs151.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestModel {
	
	
	@Test //testing if the mindField is correctly generate mines(true) 
	public void mineFieldTest() {
		final int MINES = 10;
		MineField mineField = new MineField(8, 8, MINES);
		boolean[] result = mineField.getMineBoolean();
		for(int i = 0; i<result.length; i++) {
			System.out.print(result[i] + " ");
			if ((i+1)%8 == 0) {
				System.out.println();
			}
		}
		int numOfMines = 0;
		for (int i = 0; i<result.length; i++) {
			
			if (result[i] == true) {  //counting total mines
				numOfMines++;
			}
		}
		assertEquals(numOfMines, MINES);
	}
}
