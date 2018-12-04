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

	/**
	 * Create instance of View and pass queue that will store messages about user
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

				Timer gameTimer = new Timer(1000, new ActionListener() {
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
		fieldPanel.setSize(200, 500);
		fieldPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		fieldPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns));

		CellButtonList = new ArrayList<>();

		for (int i = 0; i < numberOfRows * numberOfColumns; i++) {
			JButton cell = new JButton();

			cell.setName(i + ", ");
			cell.setPreferredSize(new Dimension(30, 30));
			cell.addMouseListener(new MineFieldListener());
			cell.addActionListener(new MineFieldActionListener());
			fieldPanel.add(cell);
			CellButtonList.add(cell);
		}

		// this.setSize(200, 600);
		this.add(controlPanel, BorderLayout.CENTER);
		this.add(fieldPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Send updated model to render in GameView
	 * 
	 * @param model
	 *            is updated model of the game
	 */

	public void change(GameInfo gameInfo) {
		// TODO: Iterate over integer representation of mine field in gameInfo
		// gameInfo.getGameStatus();
		// Populate JPanel fieldPanel with buttons.
		// Assign integer from 0 to N (where N = width*height) number to each button.
		// height = row, width = column => buttonNumber = width + (height * 8)
		// Update number of remaining mines in JLabel mineCounter.
		//

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				boolean mineBlowed = false; // marker to enable button. If mine is found, disable all button
				int buttonNumber = 0;
				
				// Update mine counter
				// TODO: check why getNumbOfMines return the same value
				mineCounter.setText("" + gameInfo.getNumOfMines());
				System.out.println(gameInfo.getNumOfMines());
				
				for (JButton jb : CellButtonList) {
					int row = (int) buttonNumber / numberOfColumns;
					int column = buttonNumber - (row * numberOfColumns);

					int adjacentMines = gameInfo.getGameStatus()[row][column];


					if (adjacentMines == GameInfo.MINE) // mine
					{
						jb.setText("M");
						mineBlowed = true;
						disableAll();
					} else if (adjacentMines == GameInfo.FLAGGED) // flag
					{
						jb.setText("?");
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
			} // EO-run()
		}); // EO-invokeLater
	} // EO-change
	
	//helper method to disable all button 
	private void disableAll() {
		for(JButton jb:CellButtonList) {
			jb.setEnabled(false);
		}
	}

	/**
	 * Initialize new View with specified queue
	 * 
	 * @param queue
	 * @return
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
				queue.put(new NewGameMessage());
				printQueue();
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
			System.out.println("Button with row: " + row + ", column: " + column);

			try {
				// Create message for Left-click
				queue.put(new LeftClickMessage(row, column));
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
			System.out.println("Left button clicked");
		}
	}

	/**
	 * Inner class to handle right-click on a cell button
	 */
	private class MineFieldListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// Get id of a clicked button
			// There are a lot of information, we need to clean it
			// to get just the first number
			String buttonInfo = e.getSource().toString();
			buttonInfo = buttonInfo.replaceFirst("javax.swing.JButton\\[", "");
			buttonInfo = buttonInfo.split(",")[0];
			int buttonNumber = Integer.parseInt(buttonInfo);

			// Calculate row (height) and column (width) of the button
			int row = (int) buttonNumber / numberOfColumns;
			int column = buttonNumber - (row * numberOfColumns);
			System.out.println("Button with row: " + row + ", column: " + column);

			// Check if it was right or left click. Create appropriate Message
			// for message queue
			if (SwingUtilities.isRightMouseButton(e)) {
				try {
					// Create message for Right-click
					queue.put(new RightClickMessage(row, column));
					replaceFlag(e); // toggle a flag for display update
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
				System.out.println("Right button clicked");
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

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
	public void replaceFlag(MouseEvent e) {
		JButton jb = (JButton) e.getSource();
		jb.setText("");
	}

}
