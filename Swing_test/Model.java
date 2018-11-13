import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Model extends JPanel
{
  private final int CELL_SIZE = 50;
  private final int FIELD_WIDTH;
  private final int FIELD_HEIGHT;
  private final int width;
  private final int height;
  private final int mines;
  private Point mousePoint;
  private int N;
  private boolean[] mineBoolean;
  private boolean initGame;
  private int adjacentMines;
  private int[][] cells;

  public Model(int width, int height, int mines)
  {
    this.width = width; 
    this.height = height; 
    this.mines = mines;

    FIELD_WIDTH = CELL_SIZE * width;
    FIELD_HEIGHT = CELL_SIZE * height;

    N = height * width;
    mineBoolean = new boolean[N];

    adjacentMines = 0;
    
    generateMineField();
    initGame = true;
    calcAdjacentMines();
    addMouseListener(new GameMouseAdapter());
  }

   private void generateMineField() 
   {
     // set mines WITHOUT DUPLICATE RANDOM NUMBERS
     // 1st. set a cell True if the cell index < # of mines
     for (int i = 0; i < N; i++) {
       if (i < mines)
         mineBoolean[i] = true;
       else
         mineBoolean[i] = false;
     }
     // set mines WITHOUT DUPLICATE RANDOM NUMBERS
     // 2nd. swapping the randomly selected cells
     for (int i = 0; i < N; i++) {
       int index = new java.util.Random().nextInt(N - i) + 1;
       // swap two m[i] and m[index]
       boolean temp = mineBoolean[i];
       mineBoolean[i] = mineBoolean[index];
       mineBoolean[index] = temp;
     }
   }

  private int hasMine(int h, int w) 
  {
    if (h < 0 || height <= h || w < 0 || width <= w)
      return 0;
    int i = h * width + w;
    if (mineBoolean[i])
      return 1;
    else 
      return 0;
  }

  private void calcAdjacentMines()
  {
    cells = new int[height][width];
    for (int h = 0; h < height; h++) 
    {
      for (int w = 0; w < width; w++) 
      {
        int adjacentMines = -1; // center cell is a mine cell, set it -1
        int i = h * width + w;
        if (!mineBoolean[i]) 
        {
          adjacentMines  = hasMine(h - 1, w - 1); // 1
          adjacentMines += hasMine(h - 1, w + 0); // 2
          adjacentMines += hasMine(h - 1, w + 1); // 3
          adjacentMines += hasMine(h + 0, w - 1); // 4
          adjacentMines += hasMine(h + 0, w + 1); // 5
          adjacentMines += hasMine(h + 1, w - 1); // 6
          adjacentMines += hasMine(h + 1, w + 0); // 7
          adjacentMines += hasMine(h + 1, w + 1); // 8
        }
        cells[h][w] = adjacentMines;
      }
    }
  }


  private class GameMouseAdapter extends MouseAdapter
  {
    public void mousePressed(MouseEvent event)
    {
      int x = event.getX();
      int y = event.getY();

      int w = x/CELL_SIZE;
      int h = y/CELL_SIZE;

      boolean repaint = false;

      if ((x < FIELD_WIDTH) && (y < FIELD_HEIGHT))
      {
        if (SwingUtilities.isLeftMouseButton(event))
        {
          System.out.println("(w,h) = (" + w + "," + h + ")");
          if (mineBoolean[h * width + w])
            repaint = true;
          else 
          {
            if (cells[h][w] > 0)
              repaint = true;
          }
        }
      }
      if (repaint)
        repaint();
    }
  }

  public void paintComponent(Graphics g) 
  {
     Image icon;
     if (initGame)
     {
        for (int i = 0; i < width; i++)                                
          for (int j = 0; j < height; j++) 
          {
            icon = new ImageIcon("img/b.png").getImage();
            g.drawImage(icon, (i * CELL_SIZE), (j * CELL_SIZE), this);
          }
        initGame = false;
     }
     else 
     {
        for (int i = 0; i < width; i++)                                 
        {
          for (int j = 0; j < height; j++) 
          {
            if (cells[i][j] >= 0)
              icon = new ImageIcon("img/" + cells[i][j] + ".png").getImage();
            else // cells[i][j] = -1 
              icon = new ImageIcon("img/m.png").getImage();
            g.drawImage(icon, (i * CELL_SIZE), (j * CELL_SIZE), this);
          }
        }
     }
   } // EO-paintComponent
}
