package textexcel.extracredit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import textexcel.Grid;
import textexcel.Spreadsheet;

public class ExtraCreditCommandHistory {
  Grid grid;

  @Before public void initializeGrid() {
    grid = new Spreadsheet();
  }

  @Test public void testEmptyCommandHistory() {
    startHistory(3);

    checkHistory(new String[]{});

    stopHistory();
  }

  @Test public void testPartiallyEmptyCommandHistory() {
    startHistory(5);

    executeCommands(new String[]
        {
        "A1 = 5",
        "A2 = \"Test\""
        });

    checkHistory(new String[]
        {
        "A2 = \"Test\"",
        "A1 = 5"
        });

    executeCommands(new String[]
        {
        "clear A1",
        "A3 = 10"
        });

    checkHistory(new String[]
        {
        "A3 = 10",
        "clear A1",
        "A2 = \"Test\"",
        "A1 = 5"
        });

    stopHistory();
  }

  @Test public void testFullCommandHistory() {
    startHistory(6);

    executeCommands(new String[]
        {
        "A1 = 5",
        "A2 = \"Test\"",
        "clear A1"
        });

    checkHistory(new String[]
        {
        "clear A1",
        "A2 = \"Test\"",
        "A1 = 5",
        });

    executeCommands(new String[]
        {
        "A1 = 6",
        "B1 = 7",
        "C1 = 8"
        });

    checkHistory(new String[]
        {
        "C1 = 8",
        "B1 = 7",
        "A1 = 6",
        "clear A1",
        "A2 = \"Test\"",
        "A1 = 5",
        });

    stopHistory();
  }

  @Test public void testOverflowingCommandHistory() {
    startHistory(4);

    executeCommands(new String[]
        {
        "A1 = 10",
        "A2 = \"Test2\"",
        "clear A2"
        });

    checkHistory(new String[]
        {
        "clear A2",
        "A2 = \"Test2\"",
        "A1 = 10",
        });

    executeCommands(new String[]
        {
        "A1 = 60",
        "B1 = 70",
        "C1 = 80"
        });

    checkHistory(new String[]
        {
        "C1 = 80",
        "B1 = 70",
        "A1 = 60",
        "clear A2"
        });

    executeCommands(new String[]
        {
        "clear B1",
        "clear C1"
        });


    checkHistory(new String[]
        {
        "clear C1",
        "clear B1",
        "C1 = 80",
        "B1 = 70"
        });

    stopHistory();
  }

  @Test public void testClearHistory() {
    startHistory(5);

    executeCommands(new String[]
        {
        "A1 = 8",
        "A2 = \"Test\"",
        "clear A1",
        "clear A2"
        });

    checkHistory(new String[]
        {
        "clear A2",
        "clear A1",
        "A2 = \"Test\"",
        "A1 = 8"
        });

    clearHistory(2);

    checkHistory(new String[]
        {
        "clear A2",
        "clear A1",
        });

    clearHistory(1);

    checkHistory(new String[]
        {
        "clear A2"
        });

    clearHistory(1);

    checkHistory(new String[]
        {
        });

    executeCommands(new String[]
        {
        "C1 = 8",
        "D2 = \"Test\"",
        "clear C1",
        "clear D2",
        "C1 = 20",
        "E1 = 40",
        "F3 = 60"
        });

    checkHistory(new String[]
        {
        "F3 = 60",
        "E1 = 40",
        "C1 = 20",
        "clear D2",
        "clear C1"
        });

    clearHistory(3);

    checkHistory(new String[]
        {
        "F3 = 60",
        "E1 = 40"
        });

    executeCommands(new String[]
        {
        "A5 = 6",
        "A6 = 7",
        "A7 = 8",
        "A8 = 9"
        });

    checkHistory(new String[]
        {
        "A8 = 9",
        "A7 = 8",
        "A6 = 7",
        "A5 = 6",
        "F3 = 60",
        });

    clearHistory(2);


    checkHistory(new String[]
        {
        "A8 = 9",
        "A7 = 8",
        "A6 = 7"
        });

    clearHistory(5);
    checkHistory(new String[]
        {
        });

    stopHistory();
  }

  @Test public void testAllOnSameSheet() {
    testClearHistory();

    grid.processCommand("A1 = 1");

    testEmptyCommandHistory();

    grid.processCommand("B2 = 2");

    testPartiallyEmptyCommandHistory();

    grid.processCommand("C3 = 3");

    testFullCommandHistory();

    grid.processCommand("D4 = 4");

    testOverflowingCommandHistory();
  }

  private void startHistory(int historySize) {
    String historyStart = grid.processCommand("history start " + historySize);
    assertEquals("", historyStart);
  }

  private void executeCommands(String[] commands) {
    for (String command : commands) {
      String output = grid.processCommand(command);
      if (command.startsWith("history")) {
        assertEquals("", output);
      }
    }
  }

  private void checkHistory(String[] expectedHistory) {
    String historyDisplay = grid.processCommand("history display");
    if (historyDisplay.equals("")) {
      assertEquals(0, expectedHistory.length);
    } else {
      String[] historyCommands = historyDisplay.split("\n");

      assertTrue(Arrays.equals(expectedHistory, historyCommands));
    }
  }

  private void clearHistory(int numToClear) {
    String historyClear = grid.processCommand("history clear " + numToClear);
    assertEquals("", historyClear);
  }

  private void stopHistory() {
    String historyStop = grid.processCommand("history stop");
    assertEquals("", historyStop);
  }
}
