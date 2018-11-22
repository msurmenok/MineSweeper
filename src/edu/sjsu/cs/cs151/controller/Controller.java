package edu.sjsu.cs.cs151.controller;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs.cs151.Message;
import edu.sjsu.cs.cs151.model.Model;
import edu.sjsu.cs.cs151.view.View;

/**
 * Controls the game
 * @author msurmenok
 *
 */
public class Controller {
	BlockingQueue<Message> queue;
	Model model;
	View view;
	
	/**
	 * Initialize game controller
	 * @param view GUI representation of the game
	 * @param model model of the game
	 */
	public Controller(View view, Model model, BlockingQueue<Message> queue)
	{
		this.view = view;
		this.model = model;
		this.queue = queue;
	}
	
	/**
	 * Iterates through messageQueue and updates the model and the view
	 */
	void mainLoop()
	{
		
	}
	
	/**
	 * Method to stop a game.
	 * Stops timer.
	 */
	void endGame()
	{
		
	}
	
}
