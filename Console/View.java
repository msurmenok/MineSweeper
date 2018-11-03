public class View 
{
    private static final byte CSI = 0x1b;

    /**
     * https://en.wikipedia.org/wiki/ANSI_escape_code#CSI_sequences
     * ESC[ 2J : clears the screen
     */
    public static void clearScreen() 
    {
        System.out.print(String.format("%c[2J", CSI));
    }
    /**
     * https://en.wikipedia.org/wiki/ANSI_escape_code#CSI_sequences
     * CSI n;m H : moves the cursor to row n, column m. 
     */
    public static void setPosition(int row, int col) 
    {
        System.out.print(String.format("%c[%d;%dH", CSI, row, col));
    }

    public static void drawOneCell(int row, int col, char c) 
    {
        System.out.print(String.format("%c[%d;%dH%c", CSI, row, col, c));
        System.out.print(String.format("%c[%d;%dH", CSI, row, col));
    }

    public static void message(String s) 
    {
      setPosition(3, 1);
      System.out.print(s);
    }
}
