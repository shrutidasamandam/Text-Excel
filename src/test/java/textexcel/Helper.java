package textexcel;

public class Helper {
  private String[][] items;

  /**
   * General Helper class to help test the excel objects.
   */
  public Helper() {
    items = new String[20][12];
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 12; j++) {
        items[i][j] = format("");
      }
    }
  }

  /**
   * Creates a general format for each of the to help check
   * cell contents.
   * @param s the string to format.
   * @return A newly formated string.
   */
  public static String format(String s) {
    return String.format(String.format("%%-%d.%ds", 10, 10),  s);
  }

  /**
   * Sets a particular cell so we can refer back to it for
   * expectation purposes.
   * @param row the row to set.
   * @param col the column to set.
   * @param text The expected text to be shown.
   */
  public void setItem(int row, int col, String text) {
    items[row][col] = format(text);
  }

  /**
   * Returns the text at a given cell index for expectation
   * purposes.
   * @return The expected text to be shown.
   */
  public String getText() {
    String ret = "   |";
    for (int j = 0; j < 12; j++) {
      ret = ret + format(Character.toString((char)('A' + j))) + "|";
    }

    ret = ret + "\n";
    for (int i = 0; i < 20; i++) {
      ret += String.format("%-3d|", i + 1);
      for (int j = 0; j < 12; j++) {
        ret += items[i][j] + "|";
      }
      ret += "\n";
    }

    return ret;
  }
}
