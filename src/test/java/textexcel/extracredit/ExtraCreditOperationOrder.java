package textexcel.extracredit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import textexcel.Grid;
import textexcel.Spreadsheet;
import textexcel.TestLocation;

public class ExtraCreditOperationOrder {
  Grid grid;

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  @Test public void testSimplePrecedence() {
    String formula = "( 1 + 2 * 3 )";
    grid.processCommand("A1 = " + formula);
    String result = grid.getCell(new TestLocation(0, 0)).abbreviatedCellText();
    assertEquals(formula, "7     ", result);
  }

  @Test public void testComplexPrecedence() {
    String formula = "( 1 - 3.0 / 5 + 7 / 2 - 4 * -18.5 + 1 )";
    grid.processCommand("L20 = " + formula);
    String result = grid.getCell(new TestLocation(19, 11)).abbreviatedCellText();
    assertEquals(formula, 78.9, Double.parseDouble(result), 1e-6);
  }

  @Test public void testReferencePrecedence() {
    String formulaA1 = "( 1 - 3 / -2 )";
    String formulaA2 = "( 4 * A1 / 2.5 - 3 / A1 )";
    String formulaA3 = "( A2 - A1 * 1.2 )";
    grid.processCommand("A1 = " + formulaA1);
    grid.processCommand("A2 = " + formulaA2);
    grid.processCommand("A3 = " + formulaA3);
    String result = grid.getCell(new TestLocation(2, 0)).abbreviatedCellText();
    assertEquals("formula with references and precedence", -0.2, Double.parseDouble(result), 1e-6);
  }

}
