To-Do
1) Paint with 'x' for the incorrectly flagged cell when game ends.
2) Timer view
3) Mine counter view
4) Split Model.java into Controller.java, View.java

CS151 project requirements 
1) use enum instead of final value 
2) incorporate one pattern  

Procedure Calls
===============
STEP1) Game.java => main()
  // model instantination with arguments
  Model model = new Model(width, height, mines);
  frame.add(model); ========================================> STEP2)

STEP2) Model.java  => model construction
  public class Model extends JPanel {
      ...
    generateMineField();                      
    calcAdjacentMines();
    addMouseListener(new GameMouseAdapter()); ==============> STEP3)
  }

STEP3) User mouse click event 
private class GameMouseAdapter extends MouseAdapter { // Model:Class
  public void mousePressed(MouseEvent event) { 
        /**
         * LEFT click
         */
        if ((x < FIELD_WIDTH) && (y < FIELD_HEIGHT) 
            && (SwingUtilities.isLeftMouseButton(event)))
        {
          isLeftClick = true;
          isRightClick = false;
          isSet = isOpenCell(h, w); ========================> STEP4-openCell)
          if (isSet) // either already opened or open it
          ...
          else  // it's a mine cell
          {
            ...
            haltGame = true;
            repaint(); =====================================> STEP4-mineCell)
            done = true;
          }
        ...
        /**
         * RIGHT click
         */
        else if ((x < FIELD_WIDTH) && (y < FIELD_HEIGHT) && 
            (SwingUtilities.isRightMouseButton(event)))
        ...

STEP4-openCell)
 /**
  * cells[h][w] 
  * values: -1 (mine), 0 (empty), 1-8 (adjacent cells)
  * state: isOpened (including isFlagged), !isOpened (mineBoolean[h*width+w])
  */
 public boolean isOpenCell(int h, int w) {
 ////////////////////////////////////////
 ...
 if (adjacentMines > 0) // OPEN & REPAINT 1 cell 
 {
  cell.setOpen(); // open a cell
  repaint(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE); ==>STEP5-repaintOneCell)
 } 
 else  // empty cell, then don't open this cell yet, rather open areas
 {
     /** 
      * openZeroArea(int h, int w) 
      * will recursively count opened cells 
      */
     repaintArray = false;
     pointArray = new ArrayList<Point>();
     opened_counter = openZeroArea(h, w);===========================>STEP5-openZeroArea)
     repaintArray = true;
     if (repaintArray) { // COLLECT CELL AREA TO BE OPEND
       repaint();        // AND REPAINT
     }
 }
  
STEP4-mineCell)
  public void paintComponent(Graphics g) { // Model:Method
    ...
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


STEP5-repaintOneCell)
  public void paintComponent(Graphics g) { // Model:Method
 ////////////////////////////////////////
    ...
    else {
        ...
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
    }

STEP5-openZeroArea)
  /**
  * cells[h][w] 
  * values: -1 (mine), 0 (empty), 1-8 (adjacent cells)
  *                    *********
  * state: isOpened (including isFlagged), !isOpened (mineBoolean[h*width+w])
  *
  */
 private int openZeroArea(int h, int w) // Model:Method
 ////////////////////////////////////////
  ...
   cell.setOpen(); // OPEN a CELL

   // COLLECT ALL NON-MINE CELLS TO BE OPEND
   xyPair = new Point(w, h);
   pointArray.add(xyPair);
   ...
   int adjacentMines = cell.adjacentMines();
   if (adjacentMines > 0) 
   {
     xyPair = new Point(w, h);
     pointArray.add(xyPair);
      ...
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
   ....


LIST of METHOD in Model.java
============================
  private int hasMine(int h, int w)  // Model:Method
  private void generateMineField() // Model:Method 
  private void calcAdjacentMines() // Model:Method 
private class GameMouseAdapter extends MouseAdapter { // Model:Class
  public void mousePressed(MouseEvent event) {      // Model:Method
  public void toggleFlag(int h, int w)  // Model:Method
  private void minecounter_view()  // Model:Method
  public boolean isOpenCell(int h, int w)  // Model:Method
  public boolean gameEnds()  // Model:Method
  private int openZeroArea(int h, int w) // Model:Method
  public void paintComponent(Graphics g) { // Model:Method
