import java.awt.*;
import java.awt.event.*; // mouse listener
import java.awt.geom.*; // graphics
import javax.swing.*;

public class Game
{

  private static final int CELL_SIZE = 50;

  public static void main(String[] args)
  {
    int width = 8;
    int height = 8;
    int mines = 4;
    /**
     * A. Window - JFrame
     */
    int FIELD_WIDTH = CELL_SIZE * width;
    int FIELD_HEIGHT = CELL_SIZE * height + CELL_SIZE/2;
    JFrame frame = new JFrame();

    /**
     * B. Component - HouseComponent extends JComponent
     * C. frame.add(Jcomponent);
     */
    Model model = new Model(width, height, mines);
    frame.add(model);

    /** 
     * D. JFrame 3 rubrics
     * 1 frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
     * 2 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
     * 3 frame.setVisible(true)
     */
    frame.setSize(FIELD_WIDTH, FIELD_HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

  } // EO-main
}
