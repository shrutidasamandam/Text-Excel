package textexcel.extracredit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import textexcel.Grid;
import textexcel.Spreadsheet;
import textexcel.TestLocation;

public class ExtraCreditHeterogeneousSorting {
  Grid grid;

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  private static String getCellName(int row, int col) {
    return "" + (Character.toString((char) ('A' + col))) + (row + 1);
  }

  @Test public void testSortaHeterogeneous() {
    grid.processCommand("H14 = \"fiddle\"");
    grid.processCommand("I14 = \"arduous\"");
    // empty
    grid.processCommand("K14 = \"zaza\"");
    grid.processCommand("L14 = -0.02");
    grid.processCommand("H15 = \"zazzaz\"");
    grid.processCommand("I15 = -13.2");
    // empty
    grid.processCommand("K15 = -190.1");
    grid.processCommand("L15 = \"yaya\"");
    grid.processCommand("H16 = 19.2");
    // empty
    // empty
    // empty
    grid.processCommand("L16 = \"azteca\"");
    grid.processCommand("H17 = 1%");
    grid.processCommand("I17 = \"boos\"");
    grid.processCommand("J17 = 2.2");
    // empty
    grid.processCommand("L17 = 60710%");
    // empty
    grid.processCommand("I18 = \"rara\"");
    grid.processCommand("J18 = 88.2");
    grid.processCommand("K18 = \"aardvark\"");
    grid.processCommand("L18 = 101%");
    // empty
    grid.processCommand("I19 = 607.2");
    // empty
    grid.processCommand("K19 = \"boo\"");
    // empty

    grid.processCommand("SoRTA H14-L19");

    String[] sortedVals = {
      "", "", "", "", "", "", "", "", "", "",
      "\"aardvark\"", "\"arduous\"", "\"azteca\"", "\"boo\"", "\"boos\"", "\"fiddle\"",
        "\"rara\"", "\"yaya\"", "\"zaza\"", "\"zazzaz\"",
      "-190.1", "-13.2", "-0.02", "0.01", "1.01", "2.2", "19.2", "88.2", "607.1", "607.2"
    };

    int ndxSortedVals = 0;

    for (int row = 13; row <= 18; row++) {
      for (int col = 7; col <= 11; col++) {
        assertEquals("Inspecting cell " + getCellName(row, col),
                sortedVals[ndxSortedVals++],
                grid.getCell(new TestLocation(row,col)).fullCellText()
        );
      }
    }
  }
}
