import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


/**
 * We decided to use console for our MineSweeper game.
 * However, in Jave, to get an input in console mode,
 * we have to switch a terminal from line mode to character mode
 * for example, 
 *              UP('u')       
 *               /|\
 *                |
 *   ('l')LEFT<---|---->RIGHT('r')
 *                |
 *               \|/
 *             DOWN('d')
 * also, open a cell ('o') and toggle a flag ('f').
 *
 * Followed the skeleton code from the following:
 * https://www.darkcoding.net/software/non-blocking-console-io-is-not-possible/
 *
 * Real non-blocking console input
 * If your program must be console based, you have to switch your terminal out 
 * of line mode into character mode, and remember to restore it before your 
 * program quits. There is no portable way to do this across operating systems.
 */
public class Controller
{
  private static String ttyConfig;

  /***************************************************************
   ***************************************************************
   *** only in between switch-statement are our implementation ***
   *** other parts in the Controller are from skeleton code    ***
   ***************************************************************
   ***************************************************************/
  // constuctor
  private Model model;
  public Controller(Model model)
  {
    this.model = model;
  }

  public void start()
  {
    int h = 0;
    int w = 0;
    model.setPosition(h, w);

    try 
    {
        setTerminalToCBreak();

        while (true) 
        {
            if (System.in.available() == 0)
                continue;

            boolean done = false;
            int c = System.in.read();
            boolean isSet;

            switch (c) {
            case 'q': // quit
                done = true;
                break;
            case 'd': // 'd'OWN; h++
                isSet = model.setPosition(h + 1, w);
                if (isSet)
                    h++;
                break;
            case 'u': // 'u'P; h--
                isSet = model.setPosition(h - 1, w);
                if (isSet)
                    h--;
                break;
            case 'l': // 'l'EFT; w--
                isSet = model.setPosition(h, w - 1);
                if (isSet)
                    w--;
                break;
            case 'r': // 'r'IGHT; w++
                isSet = model.setPosition(h, w + 1);
                if (isSet)
                    w++;
                break;
            case 'o': // 'o'pen a selected cell
                isSet = model.isOpenCell(h, w);
                if (isSet) // already opened
                {
                  done = model.gameEnds();
                  if (done)
                    View.message("You win!\n");
                  // else
                  // do-nothing as it's already opened cell
                }
                else  // not yet opened
                {
                  // then open a cell 
                  model.openCells(); 
                  View.message("Mine detonated!\n");
                  done = true;
                }
                break;
            case 'f': // 'f'lag to set/unset toggle
                model.toggleFlag(h, w);
                done = model.gameEnds();
                if (done)
                  View.message("You win!\n");
                break;
            default:
                break;
            }
            if (done)
                break;
        } // end while
  /***************************************************************
   ***************************************************************
   *** only in between switch-statement are our implementation ***
   *** other parts in the Controller are from skeleton code    ***
   ***************************************************************
   ***************************************************************/

    /////////////////////////////////////////////////////                              
    ///// SKELETON CODE FROM 
    ///// https://www.darkcoding.net/software/non-blocking-console-io-is-not-possible/
    ///// Real non-blocking console input
    /////////////////////////////////////////////////////
    } 
    catch (IOException e) 
    {
        System.err.println("IOException");
    }
    catch (InterruptedException e) 
    {
        System.err.println("InterruptedException");
    }
    finally 
    {
        try 
        {
            stty( ttyConfig.trim() );
        }
        catch (Exception e) 
        {
            System.err.println("Exception restoring tty config");
        }
    }
    /////////////////////////////////////////////////////                              
    ///// SKELETON CODE FROM 
    ///// https://www.darkcoding.net/software/non-blocking-console-io-is-not-possible/
    ///// Real non-blocking console input
    /////////////////////////////////////////////////////
  } //EO-start_game()

  /////////////////////////////////////////////////////
  ///// SKELETON CODE FROM 
  ///// https://www.darkcoding.net/software/non-blocking-console-io-is-not-possible/
  ///// Real non-blocking console input
  /////////////////////////////////////////////////////
  private static void setTerminalToCBreak() throws IOException, InterruptedException 
  {
  
      ttyConfig = stty("-g");
  
      // set the console to be character-buffered instead of line-buffered
      stty("-icanon min 1");
  
      // disable character echoing
      stty("-echo");
  }
  
  /**
   *  Execute the stty command with the specified arguments
   *  against the current active terminal.
   */
  private static String stty(final String args)
                  throws IOException, InterruptedException {
      String cmd = "stty " + args + " < /dev/tty";
  
      return exec(new String[] {
                  "sh",
                  "-c",
                  cmd
              });
  }
  
  /**
   *  Execute the specified command and return the output
   *  (both stdout and stderr).
   */
  private static String exec(final String[] cmd)
                  throws IOException, InterruptedException {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
  
      Process p = Runtime.getRuntime().exec(cmd);
      int c;
      InputStream in = p.getInputStream();
  
      while ((c = in.read()) != -1) {
          bout.write(c);
      }
  
      in = p.getErrorStream();
  
      while ((c = in.read()) != -1) {
          bout.write(c);
      }
  
      p.waitFor();
  
      String result = new String(bout.toByteArray());
      return result;
  }
  /////////////////////////////////////////////////////
  ///// SKELETON CODE FROM 
  ///// https://www.darkcoding.net/software/non-blocking-console-io-is-not-possible/
  ///// Real non-blocking console input
  /////////////////////////////////////////////////////
} // EO-Model
