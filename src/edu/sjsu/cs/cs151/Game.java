package edu.sjsu.cs.cs151;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.sjsu.cs.cs151.controller.Controller;
import edu.sjsu.cs.cs151.model.Difficulty;
import edu.sjsu.cs.cs151.model.Model;
import edu.sjsu.cs.cs151.view.View;

/**
 * Creates Model, View, and Controller and starts mainLoop from controller
 */
public class Game {
	private static BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
	private static View view;
	private static Model model;

	public static void main(String[] args) {
		view = View.init(queue);
		model = new Model(Difficulty.EASY);

		Controller game = new Controller(view, model, queue);
		try {
			game.mainLoop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//view.dispose();
		queue.clear();
	}
}
