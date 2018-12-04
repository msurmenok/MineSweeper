package edu.sjsu.cs.cs151.model;
/**
 * MineCounter: 
 * number of mines is decreased by 1 upon setFlag() 
 * number of mines is increased by 1 when removeFlag()
 */
public class MineCounter {

  private int numOfMines;

  // constructor
  public MineCounter(int numbOfMines) {
	  this.numOfMines = numbOfMines;
  }

  /**
  * number of mines is decreased by 1 upon setFlag() 
   */
 public void decreaseMine() {
    numOfMines--;
  }
  /**
   * number of mines is increased by 1 when removeFlag()
   */
  public void increaseMine() {
    numOfMines++;
  }

  /**
   * return current number of mines  instead of initial number
   */
  public int getCurrentNumOfMine() {
    return this.numOfMines;
  }
}
