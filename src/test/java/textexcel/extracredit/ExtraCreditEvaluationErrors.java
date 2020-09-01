package textexcel.extracredit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import textexcel.Cell;
import textexcel.Grid;
import textexcel.Spreadsheet;
import textexcel.TestLocation;

public class ExtraCreditEvaluationErrors {
  private Grid grid;
  private final String expectedError = "#ERROR  ";

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  @Test public void testSimpleError() {
    String formula = "( A2 )";
    grid.processCommand("A1 = " + formula);
    Cell cell = grid.getCell(new TestLocation(0, 0));
    assertEquals("evaluation error", expectedError, cell.abbreviatedCellText());
    assertEquals("formula", formula, cell.fullCellText());
  }

  @Test public void testDivideByZero() {
    String formula = "( 1 / 0 )";
    grid.processCommand("A1 = " + formula);
    Cell cell = grid.getCell(new TestLocation(0, 0));
    assertEquals("evaluation error", expectedError, cell.abbreviatedCellText());
    assertEquals("formula", formula, cell.fullCellText());
  }

  private void assertEvalError(int row, int col, String formula, String description) {
    Cell cell = grid.getCell(new TestLocation(row, col));
    assertEquals(description, expectedError, cell.abbreviatedCellText());
    assertEquals("formula", formula, cell.fullCellText());
  }

  private void assertEvalOk(int row, int col, String expected, String formula, String description) {
    Cell cell = grid.getCell(new TestLocation(row, col));
    assertEquals(description, expected, cell.abbreviatedCellText());
    assertEquals("formula", formula, cell.fullCellText());
  }

  @Test public void testSimpleTypeErrors() {
    String formula = "( avg A1-A1 )";
    grid.processCommand("A2 = " + formula);
    assertEvalError(1, 0, formula, "empty ref error");
    grid.processCommand("A1 = 1");
    assertEvalOk(1, 0, "1     ", formula, "valid ref");
    grid.processCommand("A1 = \"hello\"");
    assertEvalError(1, 0, formula, "string ref error");
    grid.processCommand("A1 = 2");
    assertEvalOk(1, 0, "2     ", formula, "valid ref");
    grid.processCommand("A1 = 11/20/2013");
    assertEvalError(1, 0, formula, "date ref error");
    grid.processCommand("A1 = 3");
    assertEvalOk(1, 0, "3     ", formula, "valid ref");
  }

  @Test public void testErrorPropagation() {
    String formulaA2 = "( sum A1-A1 )";
    String formulaA3 = "( 1 / A2 )";
    String formulaA4 = "( A3 + A3 )";
    String formulaB3 = "( A2 / 1 )";
    String formulaB4 = "( B3 + B3 )";
    String formulaC3 = "( avg A2-A3 )";
    String formulaC4 = "( sum C3-C3 )";
    grid.processCommand("A2 = " + formulaA2);
    grid.processCommand("A3 = " + formulaA3);
    grid.processCommand("A4 = " + formulaA4);
    grid.processCommand("B3 = " + formulaB3);
    grid.processCommand("B4 = " + formulaB4);
    grid.processCommand("C3 = " + formulaC3);
    grid.processCommand("C4 = " + formulaC4);
    assertEvalError(1, 0, formulaA2, "direct");
    assertEvalError(2, 0, formulaA3, "indirect");
    assertEvalError(3, 0, formulaA4, "indirect");
    assertEvalError(2, 1, formulaB3, "indirect");
    assertEvalError(3, 1, formulaB4, "indirect");
    assertEvalError(2, 2, formulaC3, "indirect");
    assertEvalError(3, 2, formulaC4, "indirect");
    grid.processCommand("A1 = 1");
    assertEvalOk(1, 0, "1     ", formulaA2, "direct");
    assertEvalOk(2, 0, "1     ", formulaA3, "indirect");
    assertEvalOk(3, 0, "2     ", formulaA4, "indirect");
    assertEvalOk(2, 1, "1     ", formulaB3, "indirect");
    assertEvalOk(3, 1, "2     ", formulaB4, "indirect");
    assertEvalOk(2, 2, "1     ", formulaC3, "indirect");
    assertEvalOk(3, 2, "1     ", formulaC4, "indirect");
    grid.processCommand("A1 = 0");
    assertEvalOk(1, 0, "0     ", formulaA2, "direct");
    assertEvalError(2, 0, formulaA3, "direct");
    assertEvalError(3, 0, formulaA4, "indirect");
    assertEvalOk(2, 1, "0     ", formulaB3, "indirect");
    assertEvalOk(3, 1, "0     ", formulaB4, "indirect");
    assertEvalError(2, 2, formulaC3, "indirect");
    assertEvalError(3, 2, formulaC4, "indirect");
  }
}
