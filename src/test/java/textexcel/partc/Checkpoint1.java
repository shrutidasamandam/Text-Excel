package textexcel.partc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import textexcel.Grid;
import textexcel.Spreadsheet;
import textexcel.TestLocation;

public class Checkpoint1 {
  private Grid grid;

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  private static String getCellName(int row, int col) {
    return "" + ((char) ('A' + col)) + (row + 1);
  }

  @Test public void testSortaTextCol() {
    grid.processCommand("A1 = \"rocks\"");
    grid.processCommand("A2 = \"babies\"");
    grid.processCommand("A3 = \"zest\"");
    grid.processCommand("A4 = \"airplanes\"");

    grid.processCommand("soRTa A1-A4");

    assertEquals("\"airplanes\"", grid.getCell(new TestLocation(0,0)).fullCellText());
    assertEquals("\"babies\"", grid.getCell(new TestLocation(1,0)).fullCellText());
    assertEquals("\"rocks\"", grid.getCell(new TestLocation(2,0)).fullCellText());
    assertEquals("\"zest\"", grid.getCell(new TestLocation(3,0)).fullCellText());
  }


  @Test public void testSortaTextRow() {
    grid.processCommand("A1 = \"rocking\"");
    grid.processCommand("B1 = \"bae\"");
    grid.processCommand("C1 = \"xylophone\"");
    grid.processCommand("D1 = \"aerospace\"");

    grid.processCommand("soRTa A1-D1");

    assertEquals("\"aerospace\"", grid.getCell(new TestLocation(0,0)).fullCellText());
    assertEquals("\"bae\"", grid.getCell(new TestLocation(0,1)).fullCellText());
    assertEquals("\"rocking\"", grid.getCell(new TestLocation(0,2)).fullCellText());
    assertEquals("\"xylophone\"", grid.getCell(new TestLocation(0,3)).fullCellText());
  }

  @Test public void testSortaTextRowExtraValues() {

    grid.processCommand("A1 = \"branded\"");
    grid.processCommand("B1 = \"acting\"");
    grid.processCommand("C1 = \"branding\"");
    grid.processCommand("D1 = \"acted\"");
    grid.processCommand("E1 = 17.4");
    grid.processCommand("A2 = 3.14159");
    grid.processCommand("B2 = \"extras!\"");

    grid.processCommand("soRTa A1-D1");

    assertEquals("\"acted\"", grid.getCell(new TestLocation(0,0)).fullCellText());
    assertEquals("\"acting\"", grid.getCell(new TestLocation(0,1)).fullCellText());
    assertEquals("\"branded\"", grid.getCell(new TestLocation(0,2)).fullCellText());
    assertEquals("\"branding\"", grid.getCell(new TestLocation(0,3)).fullCellText());

    assertEquals("17.4", grid.getCell(new TestLocation(0,4)).fullCellText());
    assertEquals("3.14159", grid.getCell(new TestLocation(1,0)).fullCellText());
    assertEquals("\"extras!\"", grid.getCell(new TestLocation(1,1)).fullCellText());
  }

  @Test public void testSortaMultidigit2DText() {
    grid.processCommand("A9 = \"fiddle\"");
    grid.processCommand("A10 = \"arduous\"");
    grid.processCommand("A11 = \"zaza\"");
    grid.processCommand("A12 = \"boos\"");
    grid.processCommand("A13 = \"zazzaz\"");
    grid.processCommand("B9 = \"aardvark\"");
    grid.processCommand("B10 = \"rara\"");
    grid.processCommand("B11 = \"yaya\"");
    grid.processCommand("B12 = \"azteca\"");
    grid.processCommand("B13 = \"boo\"");

    grid.processCommand("SoRTA A9-B13");

    String[] sortedVals = {
      "\"aardvark\"", "\"arduous\"", "\"azteca\"", "\"boo\"",
      "\"boos\"", "\"fiddle\"", "\"rara\"", "\"yaya\"", "\"zaza\"", "\"zazzaz\""
    };

    int ndxSortedVals = 0;

    for (int row = 8; row <= 12; row++) {
      for (int col = 0; col <= 1; col++) {
        assertEquals("Inspecting cell " + getCellName(row, col), sortedVals[ndxSortedVals++],
            grid.getCell(new TestLocation(row,col)).fullCellText());
      }
    }
  }
}
