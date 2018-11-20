import java.util.Queue;

/**
 * Controls the game
 * @author msurmenok
 *
 */
public class Controller {
	Queue messageQueue;
	Model model;
	View view;
	
	/**
	 * Initialize game controller
	 * @param view GUI representation of the game
	 * @param model model of the game
	 */
	public Controller(View view, Model model)
	{
		this.view = view;
		this.model = model;
		// TODO: create field messageQueue in GameView
		//this.messageQueue = view.messageQueue;
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
