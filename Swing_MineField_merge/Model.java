import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

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
    private boolean haltGame;

    private boolean isLeftClick;
    private boolean isRightClick;
    private boolean repaintArray;
    
    private Point xyPair;
    private List<Point> pointArray;

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
      haltGame = false;
      isLeftClick = false;
      isRightClick = false;
      repaintArray = false;

      MineField field = new MineField(height, width, init_mines);
      field.generateMineField();                      
      field.calcAdjacentMines();
      //cells = field.getCell();
      addMouseListener(new GameMouseAdapter());
    }

    /*
    private int hasMine(int h, int w)  // Model:Method
    {
      if (h < 0 || height <= h || w < 0 || width <= w)
        return 0;
      int i = h * width + w;
      if (mineBoolean[i])
        return 1;
      else 
        return 0;
    }

    private void generateMineField() // Model:Method 
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
    private void calcAdjacentMines() // Model:Method 
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
    */

  private class GameMouseAdapter extends MouseAdapter { // Model:Class
      public void mousePressed(MouseEvent event) {      // Model:Method
        mousePoint = event.getPoint();    
        int x = mousePoint.x;
        int y = mousePoint.y;
        int w = x/CELL_SIZE;
        int h = y/CELL_SIZE;
                                                                                                  
        Cell cell = cells[h][w];
        adjacentMines = cell.adjacentMines();

        boolean done = false;
        boolean isSet;
        /**
         * LEFT click
         */
        if ((x < FIELD_WIDTH) && (y < FIELD_HEIGHT) 
            && (SwingUtilities.isLeftMouseButton(event)))
        {
          System.out.println("Left-Click: cells["+h+"]["+w+"]="+adjacentMines);
          isLeftClick = true;
          isRightClick = false;
          isSet = isOpenCell(h, w);
          if (isSet) // either already opened or open it
          {
            done = gameEnds();
            if (done)
              System.out.println("***********You win!***********");
            // else
            // do-nothing as it's already opened cell
          }
          else  // it's a mine cell
          {
            //openCells(); 
            haltGame = true;
            repaint();
            done = true;
          }
        } // EO-if-(SwingUtilities.isLeftMouseButton(event))
        /**
         * RIGHT click
         */
        else if ((x < FIELD_WIDTH) && (y < FIELD_HEIGHT) && 
            (SwingUtilities.isRightMouseButton(event)))
        {
          System.out.println("Right-Click: cells["+h+"]["+w+"].isOpened()=" + cell.isOpened());
          isLeftClick = false;
          isRightClick = true;
          if (!cell.isOpened())  // closed
          {
            repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            done = gameEnds();
            if (done)
              System.out.println("***********You win!***********");
          }
        } // EO-if-(SwingUtilities.isRightMouseButton(event)))
        else
          ; 
      } // EO-mousePressed
  } // EO-class


    public void toggleFlag(int h, int w)  // Model:Method
    {
      Cell cell = cells[h][w];
      if (cell.isOpened())
        return; // pass
      if (cell.toggleFlag()) // hasFlagSet
      {
        // !cell.hasFlagSet => set Flag (i.e. hasFlagSet)
        // repaint with ?
        flagged_counter += 1;
        mine_counter -= 1;
      }
      else  // !hasFlagSet
      {
        // cell.hasFlagSet => unset Flag (i.e. !hasFlagSet)
        // repaint with closed
        //drawOneCell(h, w, CLOSED);
        flagged_counter -= 1;
        mine_counter += 1;
      }
      minecounter_view();
      //setPosition(h, w);
    }

    private void minecounter_view()  // Model:Method
    {
      System.out.println(String.format("Flags(%d) Mines(%d) Opend(%d) Closed(%d)\n", 
            flagged_counter, mine_counter, opened_counter, N-opened_counter));
    } 

    /**
     * whenever isOpenCell is called
     * update the opened_counter
     *
     * cells[h][w] 
     * values: -1 (mine), 0 (empty), 1-8 (adjacent cells)
     * state: isOpened (including isFlagged), !isOpened (mineBoolean[h*width+w])
     */
    public boolean isOpenCell(int h, int w)  // Model:Method
    {
      Cell cell = cells[h][w];

      // if flagged, do-nothing, no change in opened_counter
      if (cell.isFlagged())
          return true;

      if (cell.isOpened())
          return true;

      int adjacentMines = cell.adjacentMines();

      // if it's a mine cell, must be !isOpenCell, 
      // otherwise game must be over.
      if (adjacentMines == -1) 
          return false;

      // down below !cell.isOpened(), i.e. closed
      // opened_counter starts
      int opened_counter = 0;
      if (adjacentMines > 0) // && !cell.isOpened())
      {
          // set this cell opened 
          // opened_counter + 1
          // show this cell
          cell.setOpen();
          //////////////////
          opened_counter = 1;
          repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
      } 
      else  // empty cell, then don't open this cell yet, rather open areas
      {
          /** 
           * openZeroArea(int h, int w) 
           * will recursively count opened cells 
           */
          //openZeroArea(h, w);
          repaintArray = false;
          pointArray = new ArrayList<Point>();
          opened_counter = openZeroArea(h, w);
          repaintArray = true;
          if (repaintArray) {
            repaint();
          }
      }

      // accumulative opened_counter
      this.opened_counter += opened_counter;
      minecounter_view();
      //setPosition(h, w);
      return true;
    }

    public boolean gameEnds()  // Model:Method
    {
      return flagged_counter == init_mines && (opened_counter + flagged_counter) == N;
    }


    /**
     * used flood-fill algorithm
     * https://en.wikipedia.org/wiki/Flood_fill
     *
     * cells[h][w] 
     * values: -1 (mine), 0 (empty), 1-8 (adjacent cells)
     *                    *********
     * state: isOpened (including isFlagged), !isOpened (mineBoolean[h*width+w])
     *
     */
    private int openZeroArea(int h, int w) // Model:Method
    {
      // boundary check for adjacentMines areas by recursion
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
      // display current cell: cells[h][w]
      //System.out.println(cell.adjacentMines());
      xyPair = new Point(w, h);
      pointArray.add(xyPair);
      //repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
      //paintImmediately(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
      //
      // now recursive calls for adjacent cells
      // display the # of adjacentMines
      // and update opened_counter
      int adjacentMines = cell.adjacentMines();
      if (adjacentMines > 0) 
      {
        xyPair = new Point(w, h);
        pointArray.add(xyPair);
        //paintImmediately(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        return opened_counter;
      }
      // counts opened cells recursively from adjacent cells
      opened_counter += openZeroArea(h - 1, w - 1);  // 1
      opened_counter += openZeroArea(h - 1, w + 0);  // 2
      opened_counter += openZeroArea(h - 1, w + 1);  // 3
      opened_counter += openZeroArea(h + 0, w - 1);  // 4
      opened_counter += openZeroArea(h + 0, w + 1);  // 5
      opened_counter += openZeroArea(h + 1, w - 1);  // 6
      opened_counter += openZeroArea(h + 1, w + 0);  // 7
      opened_counter += openZeroArea(h + 1, w + 1);  // 8
      return opened_counter;
    }

  public void paintComponent(Graphics g) { // Model:Method
    Image icon;
    int h, w;
    ///////////////////////////////////// 
    if (initGame) {
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          icon = new ImageIcon("img/c.png").getImage(); // closed cell img
          g.drawImage(icon, (i * CELL_SIZE), (j * CELL_SIZE), this);
        }
      }
      initGame = false;
    } 
    ///////////////////////////////////// 
    else if (haltGame) {
     for (int i = 0; i < width; i++) {
       for (int j = 0; j < height; j++) {
         adjacentMines = cells[j][i].adjacentMines(); 
         if (adjacentMines == -1)
           icon = new ImageIcon("img/m.png").getImage(); // mine
         else if (adjacentMines > 0)
           icon = new ImageIcon("img/" + adjacentMines + ".png").getImage(); // number cell
         else
           icon = new ImageIcon("img/0.png").getImage(); // empty cell
         g.drawImage(icon, (i * CELL_SIZE), (j * CELL_SIZE), this);
       }
     }
     System.out.println(">>>>>>>>>>> Mine Detonated!!! <<<<<<<<<<");
    }
    ///////////////////////////////////// 
    /**
     * when open the empty cell,
     * collect adjacent non-mine cells list
     * then repaint one by one
     * otherwise, repaint can't properly done
     */
    else if (repaintArray && isLeftClick) {

        /**
         * loop over entire cell and
         * trace whether cell is in repaintArray
         * update[h][w] = 1 : repaint needed
         * otherwise, 0
         */
        int[][] update = new int[height][width];

        // initialization
        for (int i = 0; i < width; i++)                                
          for (int j = 0; j < height; j++) 
            update[j][i] = 0;


        // set 1 if from the repaintArray
        for (Point xy: pointArray) { 
          w = (int) xy.getX();    
          h = (int) xy.getY();    
          update[h][w] = 1;  
        }

        for (int i = 0; i < width; i++) {                                
          for (int j = 0; j < height; j++) {
            Cell cell = cells[j][i];                                                             
            adjacentMines = cell.adjacentMines();
            /** 
             * update[h][w] = 1 : repaint needed
             * or
             * cell is already opened state
             */
            if (update[j][i] == 1 || cell.isOpened()) {
              if (adjacentMines == -1)                                                            
                icon = new ImageIcon("img/m.png").getImage(); // mine
              else if (adjacentMines > 0)
                icon = new ImageIcon("img/" + adjacentMines + ".png").getImage(); // number cell
              else // (adjacentMines  ==  0)
                icon = new ImageIcon("img/0.png").getImage(); // empty cell
            } 
            else { // not on the repaintArray
              if (cell.isFlagged())
                icon = new ImageIcon("img/f.png").getImage(); // empty cell
              else 
                icon = new ImageIcon("img/c.png").getImage(); // empty cell
            }
            g.drawImage(icon, i * CELL_SIZE, j * CELL_SIZE, this);
          }
        }
    }
    else {
        w = mousePoint.x/CELL_SIZE;
        h = mousePoint.y/CELL_SIZE;
        //System.out.println("paintComponent("+h+","+w+")");

        Cell cell = cells[h][w];
        adjacentMines = cell.adjacentMines();

        if (isLeftClick)
        {
          if (adjacentMines == -1)                                                           
            icon = new ImageIcon("img/m.png").getImage(); // mine
          else if (adjacentMines > 0)
            icon = new ImageIcon("img/" + adjacentMines + ".png").getImage(); // number cell
          else // (adjacentMines  ==  0)
            icon = new ImageIcon("img/0.png").getImage(); // empty cell
        } // isLeftClick
        else // (isRightClick)
        {
          toggleFlag(h, w);
          if (cell.isFlagged()) 
            icon = new ImageIcon("img/f.png").getImage(); 
          else 
            icon = new ImageIcon("img/c.png").getImage();
        } // isRightClick
        g.drawImage(icon, w * CELL_SIZE, h * CELL_SIZE, this);
     }// EO-else(!initGame)
   } // EO-paintComponent
}
