package edu.sjsu.cs.cs151.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

import edu.sjsu.cs.cs151.Message;
import edu.sjsu.cs.cs151.NewGameMessage;
import edu.sjsu.cs.cs151.controller.GameInfo;
import edu.sjsu.cs.cs151.model.Model;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class that will render visual representation of the game
 */
public class View extends JFrame {

	BlockingQueue<Message> queue;
	Model model;
	int numberOfColumns;
	int numberOfRows;

	// constructor
	public View(BlockingQueue<Message> queue) {
		this.queue = queue;

		// Replace with information from GameInfo
		int numberOfMines = 4;
		int numberOfRows = 8;
		int numberOfColumns = 8;

		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
		// CONTROLPANEL
		// Top row that holds mine counter, new game button, and timer
		JPanel controlPanel = new JPanel();

		// Create elements for control panel
		JLabel mineCounter = new JLabel("" + numberOfMines);
		JButton newGameButton = new JButton("New Game");
		// Add listener to button
		newGameButton.addActionListener(new NewGameListener());
		JLabel timer = new JLabel("00:00");
		controlPanel.add(mineCounter);
		controlPanel.add(newGameButton);
		controlPanel.add(timer);

		// FIELDPANEL
		// Panel for a mine field
		JPanel fieldPanel = new JPanel();
		fieldPanel.setSize(200, 500);
		fieldPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		fieldPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		for (int i = 0; i < numberOfRows * numberOfColumns; i++) {
			JButton cell = new JButton();

			cell.setName(i + ", ");
			cell.setPreferredSize(new Dimension(30, 30));
			cell.addMouseListener(new MineFieldListener());
			fieldPanel.add(cell);
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
	}

	public static View init(BlockingQueue<Message> queue) {
		return new View(queue);
	}

	// Inner class to handle click on NewGameButton
	private class NewGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println(e.getSource());
				System.out.println(e.getModifiers());
				queue.put(new NewGameMessage());
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

		}

	}

	// Inner class to handle right-click and left-click on cell button
	private class MineFieldListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// Get id of clicked number
			String buttonInfo = e.getSource().toString();
			buttonInfo = buttonInfo.replaceFirst("javax.swing.JButton\\[", "");
			buttonInfo = buttonInfo.split(",")[0];
			int buttonNumber = Integer.parseInt(buttonInfo);
			
			// Calculate row and column of the button
			int row = (int) buttonNumber / numberOfColumns;
			int column = buttonNumber - (row * numberOfColumns);
			System.out.println("Button with row: " + row + ", column: " + column);
			
			// Check if it was right or left click. Create appropriate Message for message queue
			if (SwingUtilities.isRightMouseButton(e)) {
				// Create RightClickMessage here
				System.out.println("Right button clicked");
			} else {
				// Create LeftClickMessage here
				System.out.println("Left button clicked");
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

	public void run() {
	}

}
