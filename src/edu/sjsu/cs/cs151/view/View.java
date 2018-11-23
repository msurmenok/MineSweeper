package edu.sjsu.cs.cs151.view;

import javax.swing.*;
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

	// constructor
	public View(BlockingQueue<Message> queue) {
		this.queue = queue;

		// Replace with information from GameInfo
		int numberOfMines = 4;
		int height = 8;
		int width = 8;

		// Top row that holds mine counter, new game button, and timer
		JPanel controlPanel = new JPanel();
		// Specify that elements should be from left to right
		BoxLayout boxLayout = new BoxLayout(controlPanel, BoxLayout.X_AXIS);
		controlPanel.setLayout(boxLayout);
		// Create elements for control panel
		JLabel mineCounter = new JLabel("" + numberOfMines);
		JButton newGameButton = new JButton("New Game");
		// Add listener to button
		newGameButton.addActionListener(new NewGameListener());
		JLabel timer = new JLabel("00:00");
		controlPanel.add(mineCounter);
		controlPanel.add(newGameButton);
		controlPanel.add(timer);

		this.add(controlPanel);
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
				queue.put(new NewGameMessage());
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

		}

	}

	public void run() {
	}

}
