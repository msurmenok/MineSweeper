package edu.sjsu.cs.cs151.view;

import javax.swing.JFrame;

import edu.sjsu.cs.cs151.Message;
import edu.sjsu.cs.cs151.model.Model;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * Class that will render visual representation of the game
 */
public class View extends JFrame {

	BlockingQueue<Message> queue;
	Model model;

	// constructor
	private View(BlockingQueue<Message> queue) {
		this.queue = queue;
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
}
