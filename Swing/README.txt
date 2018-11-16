
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


