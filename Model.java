public class Model {
    private static final char CLOSED = '.';
    private static final char OPENED = ' ';
    private static final char FLAGGED = '?';
    private static final char MINE = 'M';

    private int top;
    private int left;
    private int height;
    private int width;

    private int adjacentMines;

    private int opened_counter;
    private int mine_counter;
    private int flagged_counter;
    private int init_mines;

    private Cell[][] cells;

    private int N;
    private boolean[] mineBoolean;

    public Model(int t, int l, int h, int w, int m) {
      top = t;                      
      left = l;
      height = h;
      width = w;
                                    
      opened_counter = 0;
      flagged_counter = 0;
      mine_counter = m;
      init_mines = m;
      adjacentMines = 0;
                                    
      N = height * width;
      mineBoolean = new boolean[N];
                                    
      generateMineField();
      calcAdjacentMines();
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

    public boolean setPosition(int h, int w) 
    {
        if (h < 0 || height <= h || w < 0 || width <= w) 
            return false;
        /**
         *      top                                        
         *     1+---+---+
         *     2|   |   W
         *     3+---+---+
         *     4|   |   |
         *     5+-H-+---+
         * left 123456789
         */
        View.setPosition(top + 2 * h + 1, left + 4 * w + 2);
        return true;
    }

    /**
     * same name method in both model and view
     * Model has a field coodinates, whereas View doesn't have.
     */
    public void drawOneCell(int h, int w, char c) 
    {
      View.drawOneCell(top + 2 * h + 1, left + 4 * w + 2, c);
    }

    public void toggleFlag(int h, int w) 
    {
      Cell cell = cells[h][w];
      if (cell.isOpened())
        return; // pass
      if (cell.toggleFlag()) // hasFlagSet
      {
        drawOneCell(h, w, FLAGGED);
        flagged_counter += 1;
        mine_counter -= 1;
      }
      else  // !hasFlagSet
      {
        drawOneCell(h, w, CLOSED);
        flagged_counter -= 1;
        mine_counter += 1;
      }
      minecounter_view();
      setPosition(h, w);
    }

    public void minecounter_view(String s) {
      View.setPosition(2, 1);
      System.out.print(s);
    }

    private void minecounter_view() 
    {
      minecounter_view(String.format("Flags(%d) Mines(%d) Opend(%d) Closed(%d)\n", 
            flagged_counter, mine_counter, opened_counter, N-opened_counter));
    } 

    public void displayField()
    {
      View.clearScreen();
      for (int h = 0; h < height; h++) 
      {
         // start of even row
         // +---+---+---+---+---+---+---+---
          View.setPosition(top + 2 * h, left);
          for (int w = 0; w < width; w++) 
              System.out.print("+---");
          // end of even row
          System.out.print('+');

          // start of odd row
          // | . | . | . | . | . | . | . | . 
          View.setPosition(top + 2 * h + 1, left);
          for (int w = 0; w < width; w++) 
              System.out.print(String.format("| %c ", CLOSED));
          System.out.print('|');
          // end of odd row
      }

      // start of final row 
      View.setPosition(top + 2 * height, left);
      for (int w = 0; w < width; w++) 
          System.out.print("+---");
      System.out.print('+');
      // end of final row
      View.setPosition(1, 1);
    }

    public void openCells() 
    {
      for (int h = 0; h < height; h++) 
      {
        for (int w = 0; w < width; w++) 
        {
          int adjacentMines = cells[h][w].adjacentMines();

          if (adjacentMines == -1) // mine cell
            drawOneCell(h, w, MINE);
          else
          {
            char c = (char) ('0' + adjacentMines);
            drawOneCell(h, w, c);
          }
        }
      }
    }

    /**
     * whenever isOpenCell is called
     * update the opened_counter
     */
    public boolean isOpenCell(int h, int w) 
    {
      Cell cell = cells[h][w];

      // if flagged, isOpenCell true, no change in opened_counter
      if (cell.isFlagged())
          return true;

      // check adjacentMines w/r/t cells[h][w]
      int adjacentMines = cell.adjacentMines();
      // if it's a mine cell, isOpenCell false
      if (adjacentMines == -1) 
          return false;

      // opened_counter starts
      int opened_counter = 0;
      if (adjacentMines > 0) 
      {
          // set this cell opened 
          // opened_counter + 1
          // show this cell
          cell.setOpen();
          opened_counter = 1;
          char c = (char) ('0' + adjacentMines);
          drawOneCell(h, w, c);
      } 
      else  // 0 (no mines around)
      {
          /** 
           * openZeroCounter(int h, int w) 
           * will recursively count opened cells 
           */
          opened_counter = openZeroCounter(h, w);
      }

      // accumulative opened_counter
      this.opened_counter += opened_counter;
      minecounter_view();
      setPosition(h, w);
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
      drawOneCell(h, w, OPENED);
      // AND 
      // now check this cell's adjacentMines
      // display the # of adjacentMines
      // and update opened_counter
      int adjacentMines = cell.adjacentMines();
      if (adjacentMines > 0) 
      {
          char c = (char) ('0' + adjacentMines);
          drawOneCell(h, w, c);
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

}
