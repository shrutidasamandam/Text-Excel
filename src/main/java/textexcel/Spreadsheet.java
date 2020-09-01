package textexcel;

// need some sort of field
public class Spreadsheet implements Grid {
	// initialized and declared array
	Cell[][] myArray = new Cell[20][12];
	// creating spreadsheet
	public Spreadsheet() {
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getCols(); col++) {
				myArray[row][col] = new EmptyCell();
			}
		}
	}

	@Override
	// process command
	public String processCommand(String command) {
		System.out.println("Command = " + command);
		String retVal = "";
		if (command.trim().equals("")) {
			return "";
		}
		// clear
		if (command.equalsIgnoreCase("clear")) {
			for (int row = 0; row < getRows(); row++) {
				for (int col = 0; col < getCols(); col++) {
					myArray[row][col] = new EmptyCell();
				}
			}
			retVal = this.getGridText();
		} else
		// clear A1
		if (command.toLowerCase().startsWith("clear ")) {
			String cellLocation = command.substring(6);
			// System.out.println("cellLocation="+ cellLocation);
			SpreadsheetLocation location = new SpreadsheetLocation(cellLocation);
			myArray[location.getRow()][location.getCol()] = null;
			retVal = this.getGridText();
		} else
		// assignment A1 = "hello"
		if (command.contains(" = ")) {
			String loc = command.substring(0, command.indexOf(" "));
			System.out.println("cellLocation=" + loc);
			// String str = command.substring(command.indexOf(" ")+4,command.length()-1);
			String str = command.substring(command.indexOf(" ") + 3);
			//System.out.println("str=" + str);
			SpreadsheetLocation location = new SpreadsheetLocation(loc);
			if (str.contains("%")) {
				myArray[location.getRow()][location.getCol()] = new PercentCell(str);
			} else if (str.startsWith("\"") && str.endsWith("\"")) {
				myArray[location.getRow()][location.getCol()] = new TextCell(str, str);
			} else if (str.startsWith("(") && str.endsWith(")")) {
				myArray[location.getRow()][location.getCol()] = new FormulaCell(str,this);
			} else {
				myArray[location.getRow()][location.getCol()] = new ValueCell(str);
			}
			retVal = this.getGridText();
		} else {
			// inspection A1
			SpreadsheetLocation location = new SpreadsheetLocation(command);
			retVal = getCell(location).fullCellText();
		}

		return retVal;
	}

	@Override
	// amount of rows
	public int getRows() {
		return 20;
	}

	@Override
	// amount of columns
	public int getCols() {
		return 12;
	}

	@Override
	// finding location of cell
	public Cell getCell(Location loc) {
		Cell retCell = new TextCell("", "");
		int row = loc.getRow();
		int col = loc.getCol();
		if (myArray[row][col] != null) {
			retCell = myArray[row][col];
		}
		return retCell;
	}

	public Cell getCell(int row, int col) {
		Cell retCell = new TextCell("", "");
		if (myArray[row][col] != null) {
			retCell = myArray[row][col];
		}
		return retCell;
	}

	@Override
	public String getGridText() {
		// created the header
		String rowHeader[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" };
		String header = "   ";
		for (int i = 0; i < rowHeader.length; i++) {
			header = header + "|" + rowHeader[i] + "         ";
		}
		header = header + "|\n";
		//
		String grid = "";
		for (int row = 0; row < getRows(); row++) {
			if ((row + 1) < 10)
				grid = grid + (row + 1) + "  |";
			else
				grid = grid + (row + 1) + " |";
			for (int col = 0; col < getCols(); col++) {
				//
				Cell currentCell = myArray[row][col];
				if (currentCell == null) {
					currentCell = new EmptyCell();
				}
				//
				grid = grid + currentCell.abbreviatedCellText() + "|";
			}
			grid = grid + "\n";
		}
		//
		return header + grid;
	}

}
