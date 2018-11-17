
Problem
========

Trying to use Swing's repaint/paintImmediately method to update a specific region,
1) repaint() w/o argument resets the background, 
2) thus used repaint(x, y, size, size) to update specific part of the component, 
3) but repaint(x, y, size, size) call is not finished prior to another repaint call inside the loop
4) thus, used paintImmediatelyx, y, size, size) method instead inside the loop
5) but still not all repaint is finished completely.

So, it seems like considering Runnable interface must be a proper way to complete 
recursive calls of paintComponent (via repaint method)?

Possible workaround is to collect adjacent cell coordinate w/r/t the selected
empty cell, and then repaint with this collection object (ArrayList<Point>).


Tentatively Solved (dirty paint)

  public void paintComponent(Graphics g) {
    .....
    .....
    ///////////////////////////////////// 
    /**
     * when open the empty cell,
     * collect adjacent non-mine cells list
     * then repaint one by one
     * otherwise, repaint can't properly done
     */
    else if (repaintArray) {

      if (isLeftClick) {

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
              g.drawImage(icon, i * CELL_SIZE, j * CELL_SIZE, this);
            } 
            else { // not on the repaintArray
....
....
