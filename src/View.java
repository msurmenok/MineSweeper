import javax.swing.JFrame;
import java.util.Queue;

/**
 * Class that will render visual representation of the game
 * @author Park
 * Changes:
 * 1. Create fields JFrame frame and Queue messageQueue; 
 * 2. Instead of show(), update(), and displayTime(), 
 *    create method void change(Model model)
 */
public class View extends JFrame {

  Queue messageQueue;
  GameModel model;

  // constructor
  public GameView() {
    messageQueue = new Queue();
    initUI();
  }
 
  /**
   * Send updated model to render in GameView
   * @param model is updated model of the game
   */
  public void change(GameModel model) {
  }
}
