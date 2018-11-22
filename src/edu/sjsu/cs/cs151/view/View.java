package edu.sjsu.cs.cs151.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import edu.sjsu.cs.cs151.Message;
import edu.sjsu.cs.cs151.model.Model;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class that will render visual representation of the game
 */
public class View {

	BlockingQueue<Message> queue;
	Model model;

	// constructor
	public View(BlockingQueue<Message> queue) {
		this.queue = queue;

		// Replace with information obtained from... I don't know, from Game class?
		int numberOfMines = 4;

		// Top row that holds mine counter, new game button, and timer
		JPanel controlPanel = new JPanel();
		// Specify that elements should be from left to right
		BoxLayout boxLayout = new BoxLayout(controlPanel, BoxLayout.X_AXIS);
		controlPanel.setLayout(boxLayout);
		// Create elements for control panel
		JLabel mineCounter = new JLabel("" + numberOfMines);
		JButton newGameButton = new JButton("New Game");
		JLabel timer = new JLabel("00:00");
		controlPanel.add(mineCounter);
		controlPanel.add(newGameButton);
		controlPanel.add(timer);

		JFrame frame = new JFrame("MineSweeper");
		frame.add(controlPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Send updated model to render in GameView
	 * 
	 * @param model
	 *            is updated model of the game
	 */
	public void change(Model model) {
	}

	public static View init(BlockingQueue<Message> queue) {
		return new View(queue);
	}

	public void run() {
	}

}
