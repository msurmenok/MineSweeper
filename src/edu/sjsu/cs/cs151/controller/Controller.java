package edu.sjsu.cs.cs151.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs.cs151.LeftClickMessage;
import edu.sjsu.cs.cs151.Message;
import edu.sjsu.cs.cs151.NewGameMessage;
import edu.sjsu.cs.cs151.RightClickMessage;
import edu.sjsu.cs.cs151.model.Cell;
import edu.sjsu.cs.cs151.model.Difficulty;
import edu.sjsu.cs.cs151.model.MineField;
import edu.sjsu.cs.cs151.model.Model;
import edu.sjsu.cs.cs151.view.View;

/**
 * Controls the game. Process the events obtained from view and updates state of
 * the model
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
	 * @param queue
	 *            data structure to keep information about user activities
	 */
	public Controller(View view, Model model, BlockingQueue<Message> queue) {
		this.model = model;
		this.gameInfo = new GameInfo(model);
		this.view = view;
		this.queue = queue;
		this.valves.add(new NewGameValve());
		this.valves.add(new LeftClickValve());
		this.valves.add(new RightClickValve());

	}

	// testing constructor, can be deleted later
	public Controller(Model model) {
		this.model = model;
		this.gameInfo = new GameInfo(model);
	}

	/**
	 * Iterates through messageQueue and updates the model and the view
	 * 
	 * @throws Exception
	 *             if it's impossible to obtain message from the queue
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
		Cell[][] currentCells = model.getMineField().getCells();// get the cells info from model
		int h = model.getHeight();
		int w = model.getWidth();
		if (model.getGameStatus()) {
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					if (!currentCells[i][j].isOpened()) {// closed
						if (!(currentCells[i][j]).isFlagged()) {
							gameInfo.gameInfoUpdate(i, j, gameInfo.close());
						} else {
							gameInfo.gameInfoUpdate(i, j, gameInfo.flag());
						}
					} else {
						int currCell = (currentCells[i][j]).getAdjacentMines();
						gameInfo.gameInfoUpdate(i, j, currCell);
					}
				}
			}
		} else {
			showAllMines(gameInfo);
		}
		gameInfo.updateNumOfMines(model.getNumOfMinesFromCounter()); // update number of mines in gameInfo
	}

	/**
	 * Helper method to show all mines
	 * 
	 * @param gameInfo
	 */
	private void showAllMines(GameInfo gameInfo) {
		Cell[][] currentCells = model.getMineField().getCells();// get the cells info from model
		int h = model.getHeight();
		int w = model.getWidth();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (currentCells[i][j].getAdjacentMines() == -1) {
					if (currentCells[i][j].isFlagged()) {
						gameInfo.gameInfoUpdate(i, j, gameInfo.wrongFlag());
					} else {
						gameInfo.gameInfoUpdate(i, j, currentCells[i][j].getAdjacentMines());
					}
				}

			}
		}
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

	/**
	 * LeftClickValve handles LeftClickMessage
	 *
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
			if (model.getGameStatus()) {
				if (!model.isWin()) {
					gameInfo.print();// for testing purpose
					view.change(gameInfo);
				} else {
					gameInfo.print();
					model.gameWin();
					gameInfo.setWin();
					view.change(gameInfo);
					// TODO: Add winning message on VIEW

				}

			} else {
				gameInfo.print();// for testing purpose
				model.gameOver();
				view.change(gameInfo);
				// TODO: Add losing message on VIEW

			}

			return ValveResponse.EXECUTED;
		}
	}

	/**
	 * RightClickValve handles RightClickMessage
	 *
	 */
	private class RightClickValve implements Valve {
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
