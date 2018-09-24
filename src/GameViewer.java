/*
 * GameViewer: "V" in MVC model
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
  Model model;

  // counter
  public GameView() {
    messageQueue = new Queue();
    initUI();
  }
 
  public void change(Model model) {
  }
}
