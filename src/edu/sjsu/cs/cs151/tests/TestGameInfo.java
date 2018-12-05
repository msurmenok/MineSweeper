package edu.sjsu.cs.cs151.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.sjsu.cs.cs151.controller.Controller;
import edu.sjsu.cs.cs151.model.Difficulty;
import edu.sjsu.cs.cs151.model.Model;
import junit.framework.Assert;

public class TestGameInfo {
	
	@Test //this is testing if the GameInfo will get updated once a cell is clicked to open.
	public void gameInfoTest() {
		Model model = new Model(Difficulty.EASY);
		Controller controller = new Controller(model);
		controller.getGameInfo().print();
		model.openCell(0, 0);
		System.out.println("----------------------------------");
		controller.updateGameInfo();
		controller.getGameInfo().getGameStatus();
		controller.getGameInfo().print();
		System.out.println("----------------------------------");
		model.toggleCellFlag(3, 5);
		controller.updateGameInfo();
		assertEquals(controller.getGameInfo().getGameStatus()[3][5], 10);
		controller.getGameInfo().print();
	}
}
