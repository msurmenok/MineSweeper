package edu.sjsu.cs.cs151.controller;

import edu.sjsu.cs.cs151.Message;

/**
 * Interface for Valves that handle messages from a queue
 */
public interface Valve {

	/**
	 * Performs certain action in response to message
	 * 
	 * @param message
	 *            message from the View
	 * @return the information if message was processed properly
	 */
	public ValveResponse execute(Message message);
}
