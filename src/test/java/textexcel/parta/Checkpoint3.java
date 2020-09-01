package textexcel.parta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import textexcel.Cell;
import textexcel.Grid;
import textexcel.Helper;
import textexcel.Spreadsheet;
import textexcel.TestLocation;

public class Checkpoint3 {
  private Grid grid;

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  @Test public void testPercentCell() {
    String percent = "11.25%";
    grid.processCommand("A1 = " + percent);
    Cell dateCell = grid.getCell(new TestLocation(0,0));
    assertEquals("date cell text", "11%", dateCell.abbreviatedCellText().trim());
    assertEquals("date inspection text", "0.1125", dateCell.fullCellText());
  }

  @Test public void testBasicRealCell() {
    String real = "3.14";
    grid.processCommand("D18 = " + real);
    Cell realCell = grid.getCell(new TestLocation(17, 3));
    Assert.assertEquals("real cell text", Helper.format(real), realCell.abbreviatedCellText());
    assertEquals("real inspection text", real, realCell.fullCellText());
  }

  @Test public void testMoreRealCells() {
    String zero = "0.0";
    grid.processCommand("A1 = " + zero);
    Cell zeroCell = grid.getCell(new TestLocation(0, 0));
    assertEquals("real cell 0", Helper.format(zero), zeroCell.abbreviatedCellText());
    assertEquals("real inspection 0", zero, zeroCell.fullCellText());
    String negativeTwo = "-2.0";
    grid.processCommand("B1 = " + negativeTwo);
    Cell negativeTwoCell = grid.getCell(new TestLocation(0, 1));
    assertEquals("real cell -2", Helper.format(negativeTwo), negativeTwoCell.abbreviatedCellText());
    assertEquals("real inspection -2", negativeTwo, negativeTwoCell.fullCellText());
  }

  @Test public void testDifferentCellTypes() {
    grid.processCommand("H4 = 12.281998%");
    grid.processCommand("G3 = \"5\"");
    grid.processCommand("F2 = -123.456");
    Cell dateCell = grid.getCell(new TestLocation(3, 7));
    Cell stringCell = grid.getCell(new TestLocation(2, 6));
    Cell realCell = grid.getCell(new TestLocation(1, 5));
    Cell emptyCell = grid.getCell(new TestLocation(0, 4));
    Cell[] differentCells = { dateCell, stringCell, realCell, emptyCell };
    for (int i = 0; i < differentCells.length - 1; i++) {
      for (int j = i + 1; j < differentCells.length; j++) {
        assertTrue("percent, string, real, empty cells must all have different class types",
            !differentCells[i].getClass().equals(differentCells[j].getClass()));
      }
    }
  }

  @Test public void testProcessCommand() {
    Helper helper = new Helper();
    String first = grid.processCommand("A1 = 1.021822%");
    helper.setItem(0, 0, "1%");
    assertEquals("grid with date", helper.getText(), first);
    String second = grid.processCommand("B2 = -5");
    helper.setItem(1, 1, "-5.0");
    assertEquals("grid with date and number", helper.getText(), second);
    String third = grid.processCommand("C3 = 2.718");
    helper.setItem(2, 2, "2.718");
    assertEquals("grid with date and two numbers", helper.getText(), third);
    String fourth = grid.processCommand("D4 = 0");
    helper.setItem(3, 3, "0.0");
    assertEquals("grid with date and three numbers", helper.getText(), fourth);
  }

  @Test public void testRealCellFormat() {
    String[] realsEntered = { "3.00", "-74.05000", "400", "400.0" };
    String[] realsFormatted = { "3.0     ", "-74.05  ", "400.0   ", "400.0   " };
    Helper helper = new Helper();
    for (int col = 0; col < realsEntered.length; col++) {
      for (int row = 6; row < 20; row += 10) {
        String cellName = Character.toString((char)('A' + col)) + (row + 1);
        String sheet = grid.processCommand(cellName + " = " + realsEntered[col]);
        helper.setItem(row,  col, realsFormatted[col]);
        assertEquals("sheet after setting cell " + cellName, helper.getText(), sheet);
        String inspected = grid.getCell(new TestLocation(row, col)).fullCellText();
        double expected = Double.parseDouble(realsEntered[col]);
        double actual = Double.parseDouble(inspected);
        assertEquals("inspected real value", actual, expected, 1e-6);
      }
    }
    assertEquals("final sheet", helper.getText(), grid.getGridText());
  }

  @Test public void testRealCellTruncation() {
    String big = "-9876543212345";
    grid.processCommand("A1 = " + big);
    Cell bigCell = grid.getCell(new TestLocation(0, 0));
    assertEquals("real big cell length", 10, bigCell.abbreviatedCellText().length());
    assertEquals("real big inspection ",
         Double.parseDouble(big), Double.parseDouble(bigCell.fullCellText()), 1e-6);

    String precise = "3.14159265358979";
    grid.processCommand("A2 = " + precise);
    Cell preciseCell = grid.getCell(new TestLocation(1, 0));
    assertEquals("real precise cell length", 10,
         preciseCell.abbreviatedCellText().length());
    assertEquals("real precise cell", Double.parseDouble(precise),
         Double.parseDouble(preciseCell.abbreviatedCellText()), 1e-6);
    assertEquals("real precise inspection ", Double.parseDouble(precise),
         Double.parseDouble(preciseCell.fullCellText()), 1e-6);

    String moderate = "123456";
    grid.processCommand("A3 = " + moderate);
    Cell moderateCell = grid.getCell(new TestLocation(2, 0));
    assertEquals("real moderate cell length", 10, moderateCell.abbreviatedCellText().length());
    assertEquals("real moderate cell", moderate + ".0", moderateCell.abbreviatedCellText().trim());
    assertEquals("real moderate inspection", moderate, moderateCell.fullCellText());

    String precisePerc = "7.87878%";
    grid.processCommand("A4 = " + precisePerc);
    Cell precisePerCell = grid.getCell(new TestLocation(3, 0));
    assertEquals("real precise percent cell length", 10,
         precisePerCell.abbreviatedCellText().length());
    assertEquals("real precise percent cell", "7%", precisePerCell.abbreviatedCellText().trim());
    assertEquals("real precise percent inspection", "0.0787878", precisePerCell.fullCellText());
  }
}
