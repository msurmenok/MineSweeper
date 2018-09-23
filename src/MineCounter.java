/**
 * MineCounter: 
 * number of mines is decreased by 1 upon setFlag() 
 * number of mines is increased by 1 when removeFlag()
 */
public class MineCounter {

  private int numOfMines;

  // counter
  public MineCounter() {
  }

  /* 
  * number of mines is decreased by 1 upon setFlag() 
   */
  private void decreaseMine() {
    numOfMines--;
  }
  /*
   * number of mines is increased by 1 when removeFlag()
   */
  private void increaseMine() {
    numOfMines++;
  }
}
