package textexcel.parta;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import textexcel.Grid;
import textexcel.Spreadsheet;
import textexcel.SpreadsheetLocation;

public class Checkpoint1 {
  Grid grid;

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  @Test public void testGetRows() {
    assertEquals("getRows", 20, grid.getRows());
  }

  @Test public void testGetCols() {
    assertEquals("getCols", 12, grid.getCols());
  }

  @Test public void testProcessCommand() {
    String str = grid.processCommand("");
    assertEquals("output from empty command", "", str);
  }

  @Test public void testLongShortStringCell() {
    SpreadsheetLocation loc = new SpreadsheetLocation("L20");
    assertEquals("SpreadsheetLocation column", loc.getCol(), 11);
    assertEquals("SpreadsheetLocation row", loc.getRow(), 19);

    loc = new SpreadsheetLocation("D5");
    assertEquals("SpreadsheetLocation column", loc.getCol(), 3);
    assertEquals("SpreadsheetLocation row", loc.getRow(), 4);

    loc = new SpreadsheetLocation("A1");
    assertEquals("SpreadsheetLocation column", loc.getCol(), 0);
    assertEquals("SpreadsheetLocation row", loc.getRow(), 0);
  }

  @Test public void testProcessCommandNonliteralEmpty() {
    String input = " ".trim();
    String output = grid.processCommand(input);
    assertEquals("output from empty command", "", output);
  }
}
