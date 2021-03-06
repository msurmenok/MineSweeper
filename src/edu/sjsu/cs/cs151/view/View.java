package edu.sjsu.cs.cs151.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.text.SimpleDateFormat;

import java.awt.*;
import java.awt.event.*;

import edu.sjsu.cs.cs151.LeftClickMessage;
import edu.sjsu.cs.cs151.Message;
import edu.sjsu.cs.cs151.NewGameMessage;
import edu.sjsu.cs.cs151.RightClickMessage;
import edu.sjsu.cs.cs151.controller.GameInfo;
import edu.sjsu.cs.cs151.model.Difficulty;
import edu.sjsu.cs.cs151.model.DifficultyLevel;
import edu.sjsu.cs.cs151.model.Model;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.List;
import java.util.ArrayList;

/**
 * Class that will render visual representation of the game
 */
public class View extends JFrame {

	JPanel controlPanel;
	JPanel fieldPanel;
	JLabel mineCounter;

	BlockingQueue<Message> queue;
	Model model;
	int numberOfColumns;
	int numberOfRows;
	Difficulty difficulty;

	List<JButton> CellButtonList;
	long initTime;
	Timer gameTimer;
	JLabel statusBar;
	String statusMsg;

	final static int FRAME_WIDTH = 200;
	final static int FRAME_HEIGHT = 500;
	final static int BUTTON_SIZE = 30;

	/**
	 * Create an instance of View and pass queue that will store messages about user
	 * actions
	 * 
	 * @param queue
	 *            synchronized queue to store Messages, shared with Controller
	 */
	public View(BlockingQueue<Message> queue) {
		this.queue = queue;

		// Replace with information from GameInfo
		int numberOfMines = DifficultyLevel.EASY_MINES;
		int numberOfRows = DifficultyLevel.EASY;
		int numberOfColumns = DifficultyLevel.EASY;

		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;

		// CONTROLPANEL
		// Top row that holds mine counter, new game button, and timer
		controlPanel = new JPanel();

		// Create elements for control panel
		mineCounter = new JLabel("" + numberOfMines);
		JButton newGameButton = new JButton("New Game");
		// Add listener to button
		newGameButton.addActionListener(new NewGameListener());

		JLabel timer = new JLabel("00:00");
		// Game Timer
		initTime = -1;
		// speed 1sec = 1000 millisec
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// Timer gameTimer = new Timer(1000, new ActionListener() {
				gameTimer = new Timer(1000, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (initTime < 0)
							initTime = System.currentTimeMillis();
						long now = System.currentTimeMillis();
						long timeElapsed = now - initTime;
						timer.setText(new SimpleDateFormat("mm:ss").format(timeElapsed));
					} // EO-actionPerformed
				}); // EO-Timer
				gameTimer.start(); // should stop when game is over
			} // EO-run()
		}); // EO-invokeLater

		controlPanel.add(mineCounter);
		controlPanel.add(newGameButton);
		controlPanel.add(timer);

		// FIELDPANEL
		// Panel for a mine field
		fieldPanel = new JPanel();
		fieldPanel.setSize(FRAME_HEIGHT, FRAME_WIDTH);
		fieldPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		fieldPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns));

		// statusBar
		JPanel messagePanel = new JPanel();
		// messagePanel.setSize(200, 20);
		statusMsg = "<html><center>Light-click: Open; Right-click: Flag</center></html>";
		statusBar = new JLabel(statusMsg);
		messagePanel.add(statusBar);

		CellButtonList = new ArrayList<>();

		for (int i = 0; i < numberOfRows * numberOfColumns; i++) {
			JButton cell = new JButton();

			cell.setName(i + ", ");
			cell.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
			cell.addMouseListener(new MineFieldListener());
			cell.addActionListener(new MineFieldActionListener());
			fieldPanel.add(cell);
			CellButtonList.add(cell);
		}

		this.setTitle("Group1: MineSweeper"); // JFrame Title
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(fieldPanel, BorderLayout.CENTER);
		this.add(messagePanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Send updated model to render in GameView
	 * 
	 * @param gameInfo
	 *            updated information about Model status
	 */
	public void change(GameInfo gameInfo) {
		/*
		 * SwingUtilities.invokeLater(new Runnable() { public void run() {
		 */
		boolean mineBlowed = false; // marker to enable button. If mine is found, disable all button
		int buttonNumber = 0;

		// mineCounter update
		mineCounter.setText("" + gameInfo.getNumOfMines());

		for (JButton jb : CellButtonList) {
			int row = (int) buttonNumber / numberOfColumns;
			int column = buttonNumber - (row * numberOfColumns);

			int adjacentMines = gameInfo.getGameStatus()[row][column];

			// TODO:
			// setBackground and setForeground do not work

			if (adjacentMines == GameInfo.MINE) // mine
			{
				// jb.setBackground(Color.DARK_GRAY);
				jb.setText("M");
				mineBlowed = true;
			} else if (adjacentMines == GameInfo.FLAGGED) // flag
			{
				jb.setText("?");
				// jb.setIcon(new ImageIcon());
				jb.setEnabled(false);
			} else if (adjacentMines > 0 && adjacentMines < 10)// number cell
			{
				jb.setText(adjacentMines + "");
				// jb.setBackground(Color.DARK_GRAY);
				jb.setEnabled(false);

			} else if (adjacentMines == GameInfo.EMPTY) // empty cell
			{
				// jb.setBackground(Color.DARK_GRAY);
				jb.setText("0");
				jb.setEnabled(false);
			} else if (adjacentMines == GameInfo.WRONGFLAG) // wrong flag
			{
				jb.setText("X");
				jb.setEnabled(false);
			} else if (adjacentMines == GameInfo.CLOSED && mineBlowed == false) // closed
			{
				jb.setEnabled(true);
				jb.setText("");
			}

			buttonNumber++;
		} // EO-for
		if (mineBlowed) {
			disableAll();
			statusBar.setText("Game over! Please start a new game!");
			gameTimer.stop();
		}
		// winning case
		if (gameInfo.isWin()) {
			statusBar.setText("Bravo! You won!");
			gameTimer.stop();
			disableAll();
		}
		/*
		 * } // EO-run() }); // EO-invokeLater
		 */
	} // EO-change

	/**
	 * Helper method to disable all buttons.
	 */
	private void disableAll() {
		for (JButton jb : CellButtonList) {
			jb.setEnabled(false);
		}

	}

	/**
	 * Initialize new View with specified queue
	 * 
	 * @param queue
	 *            data structure to keep track of user actions
	 * @return new view with shared queue
	 */
	public static View init(BlockingQueue<Message> queue) {
		return new View(queue);
	}

	/**
	 * Inner class to handle click on NewGameButton
	 */
	private class NewGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						initTime = -1;
						statusBar.setText(statusMsg);
						gameTimer.start();
					}// EO-run()
				}); // EO-invokeLater
				queue.put(new NewGameMessage());
				//printQueue();
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}

	}

	/**
	 * Inner class to handle left-click on a cell button
	 */
	private class MineFieldActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String buttonInfo = e.getSource().toString();
			buttonInfo = buttonInfo.replaceFirst("javax.swing.JButton\\[", "");
			buttonInfo = buttonInfo.split(",")[0];
			int buttonNumber = Integer.parseInt(buttonInfo);

			// Calculate row (height) and column (width) of the button
			int row = (int) buttonNumber / numberOfColumns;
			int column = buttonNumber - (row * numberOfColumns);
			//System.out.println("Button with row: " + row + ", column: " + column);

			try {
				// Create message for Left-click
				queue.put(new LeftClickMessage(row, column));
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
			//System.out.println("Left button clicked");
		}
	}

	private class MineFieldListener extends MouseAdapter {
		public void mousePressed(MouseEvent event) {

			// Get id of a clicked button
			// There are a lot of information, we need to clean it
			// to get just the first number
			String buttonInfo = event.getSource().toString();
			buttonInfo = buttonInfo.replaceFirst("javax.swing.JButton\\[", "");
			buttonInfo = buttonInfo.split(",")[0];
			int buttonNumber = Integer.parseInt(buttonInfo);

			// Calculate row (height) and column (width) of the button
			int row = (int) buttonNumber / numberOfColumns;
			int column = buttonNumber - (row * numberOfColumns);
			//System.out.println("Button with row: " + row + ", column: " + column);

			// Check if it was right or left click. Create appropriate Message
			// for message queue
			if (SwingUtilities.isRightMouseButton(event)) {
				try {
					// Create message for Right-click
					queue.put(new RightClickMessage(row, column));
					replaceFlag(event); // toggle a flag for display update
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
				//System.out.println("Right button clicked");
			}
		}
	}

	/**
	 * Inner class to handle right-click on a cell button
	 */
	// private class MineFieldListener implements MouseListener {
	// @Override
	// public void mouseClicked(MouseEvent e) {
	// // Get id of a clicked button
	// // There are a lot of information, we need to clean it
	// // to get just the first number
	// String buttonInfo = e.getSource().toString();
	// buttonInfo = buttonInfo.replaceFirst("javax.swing.JButton\\[", "");
	// buttonInfo = buttonInfo.split(",")[0];
	// int buttonNumber = Integer.parseInt(buttonInfo);
	//
	// // Calculate row (height) and column (width) of the button
	// int row = (int) buttonNumber / numberOfColumns;
	// int column = buttonNumber - (row * numberOfColumns);
	// System.out.println("Button with row: " + row + ", column: " + column);
	//
	// // Check if it was right or left click. Create appropriate Message
	// // for message queue
	// if (SwingUtilities.isRightMouseButton(e)) {
	// try {
	// // Create message for Right-click
	// queue.put(new RightClickMessage(row, column));
	// replaceFlag(e); // toggle a flag for display update
	// } catch (InterruptedException exception) {
	// exception.printStackTrace();
	// }
	// System.out.println("Right button clicked");
	// }
	// }
	//
	// @Override
	// public void mousePressed(MouseEvent e) {
	// }
	//
	// @Override
	// public void mouseReleased(MouseEvent e) {
	// }
	//
	// @Override
	// public void mouseEntered(MouseEvent e) {
	// }
	//
	// @Override
	// public void mouseExited(MouseEvent e) {
	// }
	// }

	/**
	 * Helper function to test the content of message queue
	 */
	public void printQueue() {
		System.out.println("\nPrinting everything in queue!");
		for (Message message : queue) {
			System.out.println(message);
		}
	}

	public void run() {
	}

	// toggle a flag for display update
	/**
	 * Remove flag if user pressed right click on flagged cell
	 * 
	 * @param e
	 *            the event to obtain the specific button
	 */
	public void replaceFlag(MouseEvent e) {
		JButton jb = (JButton) e.getSource();
		jb.setText("");
	}

}
