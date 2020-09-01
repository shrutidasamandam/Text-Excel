package textexcel.extracredit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import textexcel.Grid;
import textexcel.Spreadsheet;

public class ExtraCreditCommandErrors {
  private Grid grid;

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  @Test public void testInvalidCommand() {
    String before = grid.processCommand("A1 = \"thrang\"");
    String error = grid.processCommand("lesnerize");
    String after = grid.getGridText();
    assertTrue("error message starts with ERROR: ", error.startsWith("ERROR: "));
    assertEquals("grid contents unchanged", before, after);
  }

  @Test public void testInvalidCellAssignment() {
    String before = grid.processCommand("A1 = \"hello\"");
    String[] errors = {
      grid.processCommand("A37 = 5"),
      grid.processCommand("M1 = 3"),
      grid.processCommand("A-5 = 2"),
      grid.processCommand("A0 = 17")
    };
    String after = grid.getGridText();
    for (int i = 0; i < errors.length; i++) {
      assertTrue("error" + i + "message starts with ERROR: ", errors[i].startsWith("ERROR: "));
    }
    assertEquals("grid contents unchanged", before, after);
  }

  @Test public void testInvalidConstants() {
    String before = grid.processCommand("A1 = \"hello\"");
    String[] errors = {
      grid.processCommand("A2 = 5..."),
      grid.processCommand("A3 = 4p"),
      grid.processCommand("A4 = \"he"),
      grid.processCommand("A5 = 1/2/aughtfour"),
      grid.processCommand("A6 = *9")
    };
    String after = grid.getGridText();
    for (int i = 0; i < errors.length; i++) {
      assertTrue("error" + i + " message starts with ERROR: ", errors[i].startsWith("ERROR: "));
    }
    assertEquals("grid contents unchanged", before, after);
  }

  @Test public void testInvalidFormulaAssignment() {
    grid.processCommand("A1 = 1");
    String before = grid.processCommand("A2 = 2");
    String[] errors = {
      grid.processCommand("A3 = 5 + 2"),
      grid.processCommand("A4 = ( avs A1-A2 )"),
      grid.processCommand("A5 = ( sum A0-A2 )"),
      grid.processCommand("A6 = ( 1 + 2"),
      grid.processCommand("A7 = ( avg A1-B )"),
      grid.processCommand("A8 = M80")
    };
    String after = grid.getGridText();
    for (int i = 0; i < errors.length; i++) {
      assertTrue("error" + i + " message starts with ERROR: ", errors[i].startsWith("ERROR: "));
    }
    assertEquals("grid contents unchanged", before, after);
  }

  @Test public void testWhitespaceTolerance() {
    final String before = grid.getGridText();
    grid.processCommand("L20=5");
    grid.processCommand(" A1  =   -14 ");
    grid.processCommand("A1=-14");
    grid.processCommand("A1=(3+5*4/2)");
    grid.processCommand("A1=(sum L20-L20)");
    grid.processCommand("clear  A1");
    String after = grid.processCommand("clear");
    assertEquals("end with empty grid", before, after);
  }
}
