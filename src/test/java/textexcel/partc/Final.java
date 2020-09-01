package textexcel.partc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import textexcel.Cell;
import textexcel.Grid;
import textexcel.Spreadsheet;
import textexcel.TestLocation;

public class Final {
  private Grid grid;

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  private static String getCellName(int row, int col) {
    return "" + (Character.toString((char) ('A' + col))) + (row + 1);
  }

  @Test public void testSortaCol() {
    grid.processCommand("A1 = 19.1");
    grid.processCommand("A2 = 2.1");
    grid.processCommand("A3 = 607.1");
    grid.processCommand("A4 = 0.01");

    grid.processCommand("soRTa A1-A4");

    assertEquals("0.01", grid.getCell(new TestLocation(0,0)).fullCellText());
    assertEquals("2.1", grid.getCell(new TestLocation(1,0)).fullCellText());
    assertEquals("19.1", grid.getCell(new TestLocation(2,0)).fullCellText());
    assertEquals("607.1", grid.getCell(new TestLocation(3,0)).fullCellText());
  }


  @Test public void testSortaRow() {
    grid.processCommand("A1 = 19.2");
    grid.processCommand("B1 = 2.2");
    grid.processCommand("C1 = 607.2");
    grid.processCommand("D1 = 0.01");

    grid.processCommand("soRTa A1-D1");

    assertEquals("0.01", grid.getCell(new TestLocation(0,0)).fullCellText());
    assertEquals("2.2", grid.getCell(new TestLocation(0,1)).fullCellText());
    assertEquals("19.2", grid.getCell(new TestLocation(0,2)).fullCellText());
    assertEquals("607.2", grid.getCell(new TestLocation(0,3)).fullCellText());
  }

  @Test public void testSortaRowExtraValues() {
    grid.processCommand("A1 = 19.4");
    grid.processCommand("B1 = 2.4");
    grid.processCommand("C1 = 607.4");
    grid.processCommand("D1 = 0.01");
    grid.processCommand("E1 = 17.4");
    grid.processCommand("A2 = 3.14159");
    grid.processCommand("B2 = \"extras!\"");

    grid.processCommand("soRTa A1-D1");

    assertEquals("0.01", grid.getCell(new TestLocation(0,0)).fullCellText());
    assertEquals("2.4", grid.getCell(new TestLocation(0,1)).fullCellText());
    assertEquals("19.4", grid.getCell(new TestLocation(0,2)).fullCellText());
    assertEquals("607.4", grid.getCell(new TestLocation(0,3)).fullCellText());

    assertEquals("17.4", grid.getCell(new TestLocation(0,4)).fullCellText());
    assertEquals("3.14159", grid.getCell(new TestLocation(1,0)).fullCellText());
    assertEquals("\"extras!\"", grid.getCell(new TestLocation(1,1)).fullCellText());
  }

  @Test public void testSortaTwoDimensionalOffset() {
    grid.processCommand("C3 = 19.5");
    grid.processCommand("C4 = 2.5");
    grid.processCommand("D3 = 607.5");
    grid.processCommand("D4 = 0.01");
    grid.processCommand("E1 = 17.5");
    grid.processCommand("A2 = 3.14159");

    grid.processCommand("soRTa C3-D4");

    assertEquals("0.01", grid.getCell(new TestLocation(2,2)).fullCellText());
    assertEquals("2.5", grid.getCell(new TestLocation(2,3)).fullCellText());
    assertEquals("19.5", grid.getCell(new TestLocation(3,2)).fullCellText());
    assertEquals("607.5", grid.getCell(new TestLocation(3,3)).fullCellText());

    assertEquals("17.5", grid.getCell(new TestLocation(0,4)).fullCellText());
    assertEquals("3.14159", grid.getCell(new TestLocation(1,0)).fullCellText());
  }

  @Test public void testSortaTwoDimensional() {
    grid.processCommand("A1 = 19.3");
    grid.processCommand("A2 = 2.3");
    grid.processCommand("B1 = 607.3");
    grid.processCommand("B2 = 0.01");

    grid.processCommand("SoRTa A1-B2");

    assertEquals("0.01", grid.getCell(new TestLocation(0,0)).fullCellText());
    assertEquals("2.3", grid.getCell(new TestLocation(0,1)).fullCellText());
    assertEquals("19.3", grid.getCell(new TestLocation(1,0)).fullCellText());
    assertEquals("607.3", grid.getCell(new TestLocation(1,1)).fullCellText());
  }

  @Test public void testSortdCol() {
    grid.processCommand("A1 = 19.2");
    grid.processCommand("A2 = 2.2");
    grid.processCommand("A3 = 607.2");
    grid.processCommand("A4 = 0.01");

    grid.processCommand("SoRTd A1-A4");

    assertEquals("0.01", grid.getCell(new TestLocation(3,0)).fullCellText());
    assertEquals("2.2", grid.getCell(new TestLocation(2,0)).fullCellText());
    assertEquals("19.2", grid.getCell(new TestLocation(1,0)).fullCellText());
    assertEquals("607.2", grid.getCell(new TestLocation(0,0)).fullCellText());
  }

  @Test public void testSortdMultidigit2DValue() {
    grid.processCommand("A9 = -13.2");
    grid.processCommand("A10 = 19.2");
    grid.processCommand("A11 = 2.2");
    grid.processCommand("A12 = 607.1");
    grid.processCommand("A13 = 0.01");
    grid.processCommand("B9 = 88.2");
    grid.processCommand("B10 = -190.1");
    grid.processCommand("B11 = 1.2");
    grid.processCommand("B12 = 607.2");
    grid.processCommand("B13 = -0.02");

    grid.processCommand("SoRTd A9-B13");

    String[] sortedVals = {
      "-190.1", "-13.2", "-0.02", "0.01", "1.2", "2.2", "19.2", "88.2", "607.1", "607.2"
    };

    int ndxSortedVals = 0;

    for (int row = 12; row >= 8; row--) {
      for (int col = 1; col >= 0; col--) {
        assertEquals("Inspecting cell " + getCellName(row, col),
            sortedVals[ndxSortedVals++], grid.getCell(new TestLocation(row,col)).fullCellText());
      }
    }
  }

  @Test public void testSortDMultiType2D() {
    grid.processCommand("A9 = -13.2");
    grid.processCommand("A10 = 19.2");
    grid.processCommand("A11 = 2.2");
    grid.processCommand("A12 = 60710%");
    grid.processCommand("A13 = 1%");
    grid.processCommand("B9 = 88.2");
    grid.processCommand("B10 = -190.1");
    grid.processCommand("B11 = 101%");
    grid.processCommand("B12 = 607.2");
    grid.processCommand("B13 = -0.02");

    grid.processCommand("SoRTd A9-B13");

    String[] sortedVals = {
      "-190.1", "-13.2", "-0.02", "0.01", "1.01", "2.2", "19.2", "88.2", "607.1", "607.2"
    };

    int ndxSortedVals = 0;

    for (int row = 12; row >= 8; row--) {
      for (int col = 1; col >= 0; col--) {
        assertEquals("Inspecting cell " + getCellName(row, col),
            sortedVals[ndxSortedVals++], grid.getCell(new TestLocation(row,col)).fullCellText());
      }
    }
  }


  @Test public void testSortMultiType2DMultipleTimes() {
    grid.processCommand("A9 = -13.2");
    grid.processCommand("A10 = 19.2");
    grid.processCommand("A11 = 2.2");
    grid.processCommand("A12 = 60710%");
    grid.processCommand("A13 = 1%");
    grid.processCommand("B9 = 88.2");
    grid.processCommand("B10 = -190.1");
    grid.processCommand("B11 = 101%");
    grid.processCommand("B12 = 607.2");
    grid.processCommand("B13 = -0.02");

    grid.processCommand("SoRTa A11-B12");
    grid.processCommand("SoRTd A12-B13");
    grid.processCommand("sorta A9-B10");
    grid.processCommand("SoRTd A9-B13");

    String[] sortedVals = {
      "-190.1", "-13.2", "-0.02", "0.01", "1.01", "2.2", "19.2", "88.2", "607.1", "607.2"
    };

    int ndxSortedVals = 0;

    for (int row = 12; row >= 8; row--) {
      for (int col = 1; col >= 0; col--) {
        assertEquals("Inspecting cell " + getCellName(row, col),
            sortedVals[ndxSortedVals++], grid.getCell(new TestLocation(row,col)).fullCellText());
      }
    }
  }

  @Test public void testComparableText() {
    grid.processCommand("A1 = \"chocolate\"");
    grid.processCommand("B1 = \"chocolate\"");
    grid.processCommand("C1 = \"sauce\"");

    Cell a1 = grid.getCell(new TestLocation(0, 0));
    Cell b1 = grid.getCell(new TestLocation(0, 1));
    Cell c1 = grid.getCell(new TestLocation(0, 2));

    // If any of these CRASH (RED), then you did not properly implement the
    // Comparable interface on your TextCell class
    Comparable<Object> comparableA1 = (Comparable<Object>) a1;
    Comparable<Object> comparableC1 = (Comparable<Object>) c1;

    assertEquals("Comparing string with itself should give 0",
        0, comparableA1.compareTo(b1));
    assertTrue("Comparing string with later string should return negative",
        comparableA1.compareTo(c1) < 0);
    assertTrue("Comparing string with earlier string should return positive",
        comparableC1.compareTo(a1) > 0);
  }

  @Test public void testComparableValue() {
    grid.processCommand("A1 = 5.5");
    grid.processCommand("B1 = 5.5");
    grid.processCommand("C1 = 5.6");
    grid.processCommand("D1 = -5.7");

    Cell[] cells = {
      grid.getCell(new TestLocation(0, 0)),
      grid.getCell(new TestLocation(0, 1)),
      grid.getCell(new TestLocation(0, 2)),
      grid.getCell(new TestLocation(0, 3))
    };

    // If any of these CRASH (RED), then you did not properly implement the
    // Comparable interface on your ValueCell class
    Comparable<Object> comparableA1 = (Comparable<Object>) cells[0];
    Comparable<Object> comparableC1 = (Comparable<Object>) cells[2];

    assertEquals("Comparing value with itself should give 0",
        0, comparableA1.compareTo(cells[1]));
    assertTrue("Comparing value with larger value should return negative",
        comparableA1.compareTo(cells[2]) < 0);
    assertTrue("Comparing value with smaller value should return positive",
        comparableC1.compareTo(cells[0]) > 0);
    assertTrue("Comparing value with smaller value should return positive",
        comparableA1.compareTo(cells[3]) > 0);
  }

  @Test public void testComparablePercent() {
    grid.processCommand("A1 = 55%");
    grid.processCommand("B1 = 55%");
    grid.processCommand("C1 = 56%");
    grid.processCommand("D1 = 157%");

    Cell a1 = grid.getCell(new TestLocation(0, 0));
    Cell b1 = grid.getCell(new TestLocation(0, 1));
    Cell c1 = grid.getCell(new TestLocation(0, 2));
    Cell d1 = grid.getCell(new TestLocation(0, 3));

    // If any of these CRASH (RED), then you did not properly implement the
    // Comparable interface on your PercentCell class
    Comparable<Object> comparableA1 = (Comparable<Object>) a1;
    Comparable<Object> comparableC1 = (Comparable<Object>) c1;
    Comparable<Object> comparableD1 = (Comparable<Object>) d1;

    assertEquals("Comparing percent with itself should give 0",
        0, comparableA1.compareTo(b1));
    assertTrue("Comparing percent with larger percent should return negative",
        comparableA1.compareTo(c1) < 0);
    assertTrue("Comparing percent with smaller percent should return positive",
        comparableD1.compareTo(a1) > 0);
  }

  @Test public void testComparableFormula() {
    grid.processCommand("A1 = ( 1 + 2 + 2 )");
    grid.processCommand("B1 = ( 5 * 1 )");
    grid.processCommand("C1 = ( 0 * 7 + 100 )");

    Cell a1 = grid.getCell(new TestLocation(0, 0));
    Cell b1 = grid.getCell(new TestLocation(0, 1));
    Cell c1 = grid.getCell(new TestLocation(0, 2));

    // If any of these CRASH (RED), then you did not properly implement the
    // Comparable interface on your FormulaCell class
    Comparable<Object> comparableA1 = (Comparable<Object>) a1;
    Comparable<Object> comparableC1 = (Comparable<Object>) c1;

    assertEquals("Comparing formulas with same value should give 0",
        0, comparableA1.compareTo(b1));
    assertTrue("Comparing formula to one with larger value should return negative",
        comparableA1.compareTo(c1) < 0);
    assertTrue("Comparing formula to one with smaller value should return positive",
        comparableC1.compareTo(a1) > 0);
  }

  @Test public void testComparableRealMixed() {
    grid.processCommand("A1 = ( 1 + 2 + 2 )");
    grid.processCommand("B1 = 5");
    grid.processCommand("C1 = 500%");
    grid.processCommand("A2 = ( 3 * 2 )");
    grid.processCommand("B2 = 100");
    grid.processCommand("C2 = 501%");

    final Cell a1 = grid.getCell(new TestLocation(0, 0));
    final Cell b1 = grid.getCell(new TestLocation(0, 1));
    final Cell c1 = grid.getCell(new TestLocation(0, 2));
    final Cell a2 = grid.getCell(new TestLocation(1, 0));
    final Cell b2 = grid.getCell(new TestLocation(1, 1));
    final Cell c2 = grid.getCell(new TestLocation(1, 2));

    // If any of these CRASH (RED), then you did not properly implement the
    // Comparable interface on one or more of your RealCell subclasses
    final Comparable<Object> comparableA1 = (Comparable<Object>) a1;
    final Comparable<Object> comparableB1 = (Comparable<Object>) b1;
    final Comparable<Object> comparableC1 = (Comparable<Object>) c1;
    final Comparable<Object> comparableA2 = (Comparable<Object>) a2;
    final Comparable<Object> comparableB2 = (Comparable<Object>) b2;
    final Comparable<Object> comparableC2 = (Comparable<Object>) c2;

    // RealCells with same getDoubleValue

    assertEquals("Comparing formula with matching value should give 0",
        0, comparableA1.compareTo(b1));
    assertEquals("Comparing value with matching formula should give 0",
        0, comparableB1.compareTo(a1));
    assertEquals("Comparing formula with matching percent should give 0",
        0, comparableA1.compareTo(c1));
    assertEquals("Comparing percent with matching formula should give 0",
        0, comparableC1.compareTo(a1));
    assertEquals("Comparing value with matching percent should give 0",
        0, comparableB1.compareTo(c1));
    assertEquals("Comparing percent with matching value should give 0",
        0, comparableC1.compareTo(b1));

    // RealCells with different getDoubleValue

    assertTrue("Comparing formula with larger value should return negative",
        comparableA1.compareTo(b2) < 0);
    assertTrue("Comparing value with smaller formula should return positive",
        comparableB2.compareTo(a1) > 0);

    assertTrue("Comparing formula with larger percent should return negative",
        comparableA1.compareTo(c2) < 0);
    assertTrue("Comparing percent with smaller formula should return positive",
        comparableC2.compareTo(a1) > 0);

    assertTrue("Comparing value with larger percent should return negative",
        comparableB1.compareTo(c2) < 0);
    assertTrue("Comparing percent with smaller value should return positive",
        comparableC2.compareTo(b1) > 0);

    assertTrue("Comparing formula with smaller value should return positive",
        comparableA2.compareTo(b1) > 0);
    assertTrue("Comparing value with larger formula should return negative",
        comparableB1.compareTo(a2) < 0);

    assertTrue("Comparing formula with smaller percent should return positive",
        comparableA2.compareTo(c1) > 0);
    assertTrue("Comparing percent with larger formula should return negative",
        comparableC1.compareTo(a2) < 0);

    assertTrue("Comparing value with smaller percent should return positive",
        comparableB2.compareTo(c1) > 0);
    assertTrue("Comparing percent with larger value should return negative",
        comparableC1.compareTo(b2) < 0);
  }
}
