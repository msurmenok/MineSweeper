
Problem1
========
Currently having difficulty update one cell inside a for loop.
Tried repaint(x, y, size, size), but didn't update immediately.
Now used paintImmediately(x, y, size, size), but still not correctly update
image by calling paintComponet method.

Maybe we need to use Runnable/Thread mechanism in our textbook in Ch9.
Concurrent programming?


///////////////////////////////////
//  Model.java
///////////////////////////////////
 /**
  * cells[h][w] 
  * values: -1 (mine), 0 (empty), 1-8 (adjacent cells)
  *                    *********
  * state: isOpened (including isFlagged), !isOpened (mineBoolean[h*width+w])
  */
 private void openZeroArea(int h, int w) 
 {
   Cell cell  = cells[h][w];
   paintImmediately(w * CELL_SIZE, h * CELL_SIZE, CELL_SIZE, CELL_SIZE);
   ///////////////////////////////////
   cell.setOpen();
   for (int i = h - 1; i <= h + 1; i++) 
     for (int j = w - 1; j <= w + 1; j++) 
       if ((i >= 0 || i <= height) && (j >= 0 || j <= width))
       {
         System.out.println(i + ", "+ j);
         cell  = cells[i][j];
         if (!cell.isOpened() && !cell.isFlagged() && cell.adjacentMines() > 0)
         {
           cell.setOpen();
           //repaint(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
           paintImmediately(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
           ///////////////////////////////////
           System.out.println("Loop: openZeroArea("+i+","+j+")");
         }
       }
 }

