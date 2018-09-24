/**
 * GameView: "V" in MVC model
 * @author Park
 * Changes:
 * 1. Create fields JFrame frame and Queue messageQueue; 
 * 2. Instead of show(), update(), and displayTime(), 
 *    create method void change(Model model)
 */
import javax.swing.JFrame;
import java.util.Queue;

public class GameView extends Jframe {

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
