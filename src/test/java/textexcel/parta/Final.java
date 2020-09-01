package textexcel.parta;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import textexcel.Cell;
import textexcel.Grid;
import textexcel.Helper;
import textexcel.Spreadsheet;
import textexcel.TestLocation;

public class Final {
  private Grid grid;

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  private void assertListContains(Iterable<String> list, String text) {
    for (String line : list) {
      if (line.equals(text)) {
        return;
      }
    }

    assertEquals("Unable to find '" + text + "' in saved file", "0", "1");
  }

  @Test public void testSaveFormat() {
    // Generate the saved file
    grid.processCommand("A1 = 23.521822%");       // Percent
    grid.processCommand("B3 = -52.5");          // Value
    grid.processCommand("J6 = 2.888");          // Value
    grid.processCommand("L20 = 0");           // Value
    grid.processCommand("D10 = \"ChocolateChocolateChipCrustedCookie\"");  // Text
    grid.processCommand("F4 = ( 2 + 1 * 7 )");     // Formula
    grid.processCommand("save TestSaveFormat.csv");

    // Open the file manually with a scanner to inspect its contents
    Scanner file;
    try {
      file = new Scanner(new File("TestSaveFormat.csv"));
    } catch (FileNotFoundException e) {
      assertEquals("Unable to open TestSaveFormat.csv: " + e.getMessage(), "0", "1");
      return;
    }

    ArrayList<String> contents = new ArrayList<String>();
    while (file.hasNextLine()) {
      contents.add(file.nextLine());
    }
    file.close();

    assertListContains(contents, "A1,PercentCell,0.23521822");
    assertListContains(contents, "B3,ValueCell,-52.5");
    assertListContains(contents, "F4,FormulaCell,( 2 + 1 * 7 )");
    assertListContains(contents, "J6,ValueCell,2.888");
    assertListContains(contents, "D10,TextCell,\"ChocolateChocolateChipCrustedCookie\"");
    assertListContains(contents, "L20,ValueCell,0");
  }

  @Test public void testFileIoSimple() {
    Helper helper = new Helper();

    // Cells of each type (do formula in separate test, since can't compare
    // sheet texts with formulas until Part B)
    grid.processCommand("A1 = 1.021822%");        // Percent
    helper.setItem(0, 0, "1%");
    grid.processCommand("A2 = -5");           // Value
    helper.setItem(1, 0, "-5.0");
    grid.processCommand("K19 = 2.718");         // Value
    helper.setItem(18, 10, "2.718");
    grid.processCommand("L20 = 0");           // Value
    helper.setItem(19, 11, "0.0");
    String d8 = "ChocolateChocolateChipCrustedCookie";
    grid.processCommand("D8 = " + "\"" + d8 + "\"");  // Text
    helper.setItem(7, 3, d8.substring(0, 10));

    // Save and clear
    grid.processCommand("save TestFileIOSimple.csv");
    grid.processCommand("clear");

    // Verify grid is cleared
    Cell cell = grid.getCell(new TestLocation(0, 0));
    assertEquals("cell inspection after clear", "", cell.fullCellText());
    cell = grid.getCell(new TestLocation(1, 0));
    assertEquals("cell inspection after clear", "", cell.fullCellText());
    cell = grid.getCell(new TestLocation(18, 10));
    assertEquals("cell inspection after clear", "", cell.fullCellText());
    cell = grid.getCell(new TestLocation(19, 11));
    assertEquals("cell inspection after clear", "", cell.fullCellText());
    cell = grid.getCell(new TestLocation(7, 3));
    assertEquals("cell inspection after clear", "", cell.fullCellText());

    // Read back in the file, verify sheet looks correct
    String gridText = grid.processCommand("open TestFileIOSimple.csv");

    assertEquals("grid after save and open", helper.getText(), gridText);
  }

  private String getConstantFormulaString(int col) {
    String ret = "( 0.2";
    String[] operators = {" + ", " - ", " * ", " / "};
    int operator = 0;
    String colS = "" + col;

    for (int row = 1; row <= 18; row++) {
      ret += operators[operator] + colS + row;
      operator = (operator + 1) % 4;
    }

    ret += " )";
    return ret;
  }


  @Test public void testFileIoComplex() {
    // Fills out all cells, and tests them individually.  Includes formulas,
    // so grid text cannot be used for comparisons

    // Fill out all but last two rows with different double values
    double value = 0.1;
    for (int col = 0; col < 12; col++) {
      for (int row = 0; row < 18; row++) {
        grid.processCommand(((char) ('A' + col)) + "" + (row + 1) + " = " + value);
        value++;
      }
    }

    // Next row combines the columns via FormulaCell
    for (int col = 0; col < 12; col++) {
      grid.processCommand(((char) ('A' + col)) + "19 = " + getConstantFormulaString(col));
    }

    // Final row contains special strings (NOT formulas, but they look like them)
    String odds = "\"( 1 * 2 / 1 + 3 - 5 )\"";
    String evens = "\"B4 = ( avg A2-A3 )\"";
    for (int col = 0; col < 12; col++) {
      grid.processCommand(((char) ('A' + col)) + "20 = " + ((col % 2 == 0) ? evens : odds));
    }

    // Save and clear
    grid.processCommand("save TestFileIOComplex.csv");
    grid.processCommand("clear");

    // Verify grid is cleared
    for (int row = 0; row < 20; row++) {
      for (int col = 0; col < 12; col++) {
        Cell cell = grid.getCell(new TestLocation(row, col));
        assertEquals("cell inspection after clear", "", cell.fullCellText());
      }
    }

    // Read back in the file
    String gridText = grid.processCommand("open TestFileIOComplex.csv");

    // Redo the loops, this time verifying the cells' text

    // Doubles in all but last two rows
    value = 0.1;
    for (int col = 0; col < 12; col++) {
      for (int row = 0; row < 18; row++) {
        Cell cell = grid.getCell(new TestLocation(row, col));
        assertEquals("value cell inspection after reload", "" + value, cell.fullCellText());
        value++;
      }
    }

    // Next row's formulas that combine the columns
    for (int col = 0; col < 12; col++) {
      Cell cell = grid.getCell(new TestLocation(18, col));
      assertEquals("formula cell inspection after reload",
          "" + getConstantFormulaString(col), cell.fullCellText());
    }

    // Final row contains special strings
    for (int col = 0; col < 12; col++) {
      Cell cell = grid.getCell(new TestLocation(19, col));
      assertEquals("formula cell inspection after reload",
           ((col % 2 == 0) ? evens : odds), cell.fullCellText());
    }
  }
}
