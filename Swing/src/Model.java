import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;

public class Model extends JPanel 
{
    private final int CELL_SIZE = 50;
    private final int FIELD_WIDTH;
    private final int FIELD_HEIGHT;

    private int height;
    private int width;

    private int adjacentMines;
    private Point mousePoint;

    private int opened_counter;
    private int mine_counter;
    private int flagged_counter;
    private int init_mines;

    private Cell[][] cells;

    private int N;
    private boolean[] mineBoolean;

    private boolean initGame;

    private boolean paintFlag;
    private boolean paintClosed;
    private boolean isLeftClick;
    private boolean isRightClick;
    private boolean done;
    private boolean isSet;

    public Model(int height, int width, int m) {

      this.height = height;
      this.width = width;
                                    
      FIELD_WIDTH = CELL_SIZE * width;
      FIELD_HEIGHT = CELL_SIZE * height;

      opened_counter = 0;
      flagged_counter = 0;
      mine_counter = m;
      init_mines = m;
      adjacentMines = 0;
                                    
      N = height * width;
      mineBoolean = new boolean[N];
                                    
      initGame = true;
      paintFlag = false;
      paintClosed = true;
      isLeftClick = false;
      isRightClick = false;
      done = false;
      isSet = false;

      generateMineField();                      
      calcAdjacentMines();
      //addMouseListener(new GameMouseAdapter());
      drawOneCell();
      System.out.println(initGame);
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

    private void generateMineField() 
    {
      // set mines WITHOUT DUPLICATE RANDOM NUMBERS
      // 1st. set a cell True if the cell index < # of mines
      for (int i = 0; i < N; i++) {
        if (i < init_mines)
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

    /**
     * Calculate mines in eight adjacent cells w/r/t (i, j)
     *  (i - 1, j - 1), (i - 1, j), (i - 1, j + 1)
     *  (i    , j - 1),  [SET -1]   (i    , j + 1)
     *  (i + 1, j - 1), (i + 1, j), (i + 1, j + 1)
     *
     * -1 if mine is set at the cell
     * 0, 1, ..., 8 otherwise
     *
     */
    private void calcAdjacentMines()
    {
      cells = new Cell[height][width];
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
          cells[h][w] = new Cell(adjacentMines);
        }
      }
    }

  //private class GameMouseAdapter extends MouseAdapter
  public void drawOneCell() { 
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent event)                                                  
      {
        mousePoint = event.getPoint();    
        int x = mousePoint.x;
        int y = mousePoint.y;
        int w = x/CELL_SIZE;
        int h = y/CELL_SIZE;
                                                                                                  
        /**
         * LEFT click
         */
        if ((x < FIELD_WIDTH) && (y < FIELD_HEIGHT) 
            && (SwingUtilities.isLeftMouseButton(event)))
        {
          System.out.print("Left-Click: (h,w) = (" + h + "," + w + ");   ");
          System.out.println("cells[h][w]=" + cells[h][w].adjacentMines());
          isLeftClick = true;
          isRightClick = false;
          //repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);

          //case 'o': // 'o'pen a selected cell
          isSet = isOpenCell(h, w);
          if (isSet) // already opened
          {
            done = gameEnds();
            if (done)
              System.out.println("******You win!******\n");
            // else
            // do-nothing as it's already opened cell
          }
         else  // not yet opened
         {
           // then open a cell 
           openCells(); 
           System.out.println(">>>>>>>>>>>Mine detonated!<<<<<<<<<<\n");
           done = true;
         }
        } // EO-if-(SwingUtilities.isLeftMouseButton(event))
        /**
         * RIGHT click
         */
        else if ((x < FIELD_WIDTH) && (y < FIELD_HEIGHT) && 
            (SwingUtilities.isRightMouseButton(event)))
        {
          isLeftClick = false;
          isRightClick = true;
          System.out.println("Right-Click: (h,w) = (" + h + "," + w + ");   ");
          //case 'f': // 'f'lag to set/unset toggle
          toggleFlag(h, w);
          done = gameEnds();
          repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
          if (done)
            System.out.println("******You win!******\n");
        } // EO-if-(SwingUtilities.isRightMouseButton(event)))
        /**
         * MIDDLE click
         */
        else // middle mouse button?
          ;  // do-nothing
      } // EO-mousePressed
    });
  } // EO-drawOneCell()


    public void toggleFlag(int h, int w) 
    {
      Cell cell = cells[h][w];
      if (cell.isOpened())
        return; // pass
      if (cell.toggleFlag()) // hasFlagSet
      {
        // !cell.hasFlagSet => set Flag (i.e. hasFlagSet)
        // repaint with ?
        paintFlag = true;
        paintClosed = false;
        repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        flagged_counter += 1;
        mine_counter -= 1;
      }
      else  // !hasFlagSet
      {
        paintFlag = false;
        paintClosed = true;
        // cell.hasFlagSet => unset Flag (i.e. !hasFlagSet)
        // repaint with closed
        repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        //drawOneCell(h, w, CLOSED);
        flagged_counter -= 1;
        mine_counter += 1;
      }
      minecounter_view();
      //setPosition(h, w);
    }

    public void minecounter_view(String s) {
      //View.setPosition(2, 1);
      System.out.print(s);
    }

    private void minecounter_view() 
    {
      //minecounter_view(String.format("Flags(%d) Mines(%d) Opend(%d) Closed(%d)\n", 
      //      flagged_counter, mine_counter, opened_counter, N-opened_counter));
      ;
    } 

    public void openCells() 
    {
      for (int h = 0; h < height; h++) 
        for (int w = 0; w < width; w++) 
            repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    /**
     * whenever isOpenCell is called
     * update the opened_counter
     */
    public boolean isOpenCell(int h, int w) 
    {
      Cell cell = cells[h][w];

      // if flagged, do-nothing, no change in opened_counter
      if (cell.isFlagged())
          return true;

      // check adjacentMines w/r/t cells[h][w]
      int adjacentMines = cell.adjacentMines();
      // if it's a mine cell, isOpenCell false
      if (adjacentMines == -1) 
          return false;

      // opened_counter starts
      int opened_counter = 0;
      if (adjacentMines > 0 && !cell.isOpened())
      {
          // set this cell opened 
          // opened_counter + 1
          // show this cell
          cell.setOpen();
          opened_counter = 1;
          repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
          //char c = (char) ('0' + adjacentMines);
          //drawOneCell(h, w, c);
      } 
      else  // 0 (no mines around)
      {
          /** 
           * openZeroCounter(int h, int w) 
           * will recursively count opened cells 
           */
          opened_counter = openZeroCounter(h, w);
          repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
      }

      // accumulative opened_counter
      this.opened_counter += opened_counter;
      minecounter_view();
      //setPosition(h, w);
      return true;
    }

    public boolean gameEnds() 
    {
      return flagged_counter == init_mines && (opened_counter + flagged_counter) == N;
    }


    private int openZeroCounter(int h, int w) 
    {
      // boundary check
      if (h < 0 || height <= h || w < 0 || width <= w)
          return 0;

      Cell cell = cells[h][w];

      // IF 
      //////////////////////////////////
      // this cell is flagged or opened
      // do-nothing
      if (cell.isFlagged())
          return 0;
      if (cell.isOpened())
          return 0;

      // ELSE
      //////////////////////////////////
      // now set this cell opened
      cell.setOpen();
      // increase opened_counter
      int opened_counter = 1;
      // display this empty open cell
      repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
      //drawOneCell(h, w, OPENED);
      // AND 
      // now check this cell's adjacentMines
      // display the # of adjacentMines
      // and update opened_counter
      int adjacentMines = cell.adjacentMines();
      if (adjacentMines > 0) 
      {
          repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
          //char c = (char) ('0' + adjacentMines);
          //drawOneCell(h, w, c);
          return opened_counter;
      }
      // counts opened cells recursively from adjacent cells
      opened_counter += openZeroCounter(h - 1, w - 1);  // 1
      opened_counter += openZeroCounter(h - 1, w + 0);  // 2
      opened_counter += openZeroCounter(h - 1, w + 1);  // 3
      opened_counter += openZeroCounter(h + 0, w - 1);  // 4
      opened_counter += openZeroCounter(h + 0, w + 1);  // 5
      opened_counter += openZeroCounter(h + 1, w - 1);  // 6
      opened_counter += openZeroCounter(h + 1, w + 0);  // 7
      opened_counter += openZeroCounter(h + 1, w + 1);  // 8
      return opened_counter;
    }

  public void paintComponent(Graphics g) 
  {
     Image icon;
     int h, w;
     if (initGame)
     {
      for (int i = 0; i < width; i++)                                
        for (int j = 0; j < height; j++) 
        {
          icon = new ImageIcon("src/img/c.png").getImage();
          g.drawImage(icon, (i * CELL_SIZE), (j * CELL_SIZE), this);
        }
        initGame = false;
     }
     else // !initGame
     {
        w = mousePoint.x/CELL_SIZE;
        h = mousePoint.y/CELL_SIZE;
        if (isLeftClick)
        {
          if (mineBoolean[h * width + w]) // mineCell
            icon = new ImageIcon("src/img/m.png").getImage();
          else // !mineCell 
            icon = new ImageIcon("src/img/" + cells[h][w].adjacentMines() + ".png").getImage();
        } // isLeftClick
        else // (isRightClick)
        {
          if (cells[h][w].isFlagged()) // isFlagged, paint with closed icon
            icon = new ImageIcon("src/img/c.png").getImage();
          else // !isFlagged, paint with flag icon "?"
            icon = new ImageIcon("src/img/f.png").getImage();
        } // isRightClick
        g.drawImage(icon, w * CELL_SIZE, h * CELL_SIZE, this);
     }// EO-else(!initGame)
   } // EO-paintComponent
}

