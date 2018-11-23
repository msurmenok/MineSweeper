package edu.sjsu.cs.cs151.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs.cs151.LeftClickMessage;
import edu.sjsu.cs.cs151.Message;
import edu.sjsu.cs.cs151.NewGameMessage;
import edu.sjsu.cs.cs151.model.Cell;
import edu.sjsu.cs.cs151.model.Difficulty;
import edu.sjsu.cs.cs151.model.MineField;
import edu.sjsu.cs.cs151.model.Model;
import edu.sjsu.cs.cs151.view.View;

/**
 * Controls the game
 * 
 */
public class Controller {
	BlockingQueue<Message> queue;
	Model model;
	View view;
	GameInfo gameInfo;
	private List<Valve> valves = new LinkedList<Valve>();

	/**
	 * Initialize game controller
	 * 
	 * @param view
	 *            GUI representation of the game
	 * @param model
	 *            model of the game
	 */
	public Controller(View view, Model model, BlockingQueue<Message> queue) {
		this.view = view;
		this.model = model;
		this.queue = queue;
		this.gameInfo = new GameInfo(model);
		this.valves.add(new NewGameValve());
		this.valves.add(new LeftClickValve());

	}

	// testing constructor, can be deleted later
	public Controller(Model model) {
		this.model = model;
		this.gameInfo = new GameInfo(model);
	}

	/**
	 * Iterates through messageQueue and updates the model and the view
	 */
	public void mainLoop() throws Exception {
		ValveResponse response = ValveResponse.EXECUTED;
		Message message = null;
		while (response != ValveResponse.FINISH) {
			try {
				message = (Message) queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (Valve valve : valves) {
				response = valve.execute(message);
				if (response != ValveResponse.MISS)
					break;
			}
		}

	}

	/**
	 * update GameInfo closed cell: -2 mine cell: -1 opened empty cell: 0 open cell
	 * with surrounding mine: adjacentMnes flagged cell: 10
	 */
	public void updateGameInfo() {
		Cell[][] currentCells = model.getMineField().getCell();// get the cells info from model
		int h = model.getHeight();
		int w = model.getWidth();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if ((currentCells[i][j]).isFlagged()) { // if the cells has been flagged
					gameInfo.gameInfoUpdate(i, j, gameInfo.flag()); // change gameinfo Matrix
				} else {
					if (currentCells[i][j].isOpened()) {
						int currCell = (currentCells[i][j]).adjacentMines();
						gameInfo.gameInfoUpdate(i, j, currCell);
					} else {
						continue;
					}

				}
			}
		}
	}

	/**
	 * Method to stop a game. Stops timer.
	 */
	void endGame() {

	}

	/**
	 * NewGameValve handles NewGameMessage
	 * 
	 * @author jing
	 *
	 */
	private class NewGameValve implements Valve {

		@Override
		public ValveResponse execute(Message message) {
			if (message.getClass() != NewGameMessage.class) {
				return ValveResponse.MISS;
			}
			queue.clear();
			model = model.restartGame();
			gameInfo = new GameInfo(model);
			view.change(gameInfo);
			gameInfo.print();// for testing purpose
			return ValveResponse.EXECUTED;

		}

	}

	/*
	 * LeftClickValve handles LeftClickMessage
	 */
	private class LeftClickValve implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if (message.getClass() != LeftClickMessage.class) {
				return ValveResponse.MISS;
			}
			LeftClickMessage leftClick = (LeftClickMessage) message;
			model.openCell(leftClick.getHeight(), leftClick.getWidth()); // model open cell
			updateGameInfo();
			gameInfo.print();// for testing purpose
			view.change(gameInfo);
			return ValveResponse.EXECUTED;
		}
	}
	private class RightClickValve implements Valve{
		@Override
		public ValveResponse execute(Message message) {
			if (message.getClass() != RightClickMessage.class) {
				return ValveResponse.MISS;
			}
			RightClickMessage rightClick = (RightClickMessage) message;
			model.toggleCellFlag(rightClick.getHeight(), rightClick.getWidth());
			updateGameInfo();
			gameInfo.print();// for testing purpose
			view.change(gameInfo);
			return ValveResponse.EXECUTED;
		}
	}

}
