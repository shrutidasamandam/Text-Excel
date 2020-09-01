package textexcel;

public class SpreadsheetLocation implements Location {
	String location;

	public SpreadsheetLocation(String cellName) {
		location = cellName.toUpperCase();
	}

	@Override
	// finds rows
	public int getRow() {
		String rowVal = location.substring(1);
		return Integer.parseInt(rowVal) - 1;

	}

	@Override
	// finds columns
	public int getCol() {
		String colVal = location.substring(0, 1);
		if (colVal.equals("A")) {
			return 0;
		}
		if (colVal.equals("B")) {
			return 1;
		}
		if (colVal.equals("C")) {
			return 2;
		}
		if (colVal.equals("D")) {
			return 3;
		}
		if (colVal.equals("E")) {
			return 4;
		}
		if (colVal.equals("F")) {
			return 5;
		}
		if (colVal.equals("G")) {
			return 6;
		}
		if (colVal.equals("H")) {
			return 7;
		}
		if (colVal.equals("I")) {
			return 8;
		}
		if (colVal.equals("J")) {
			return 9;
		}
		if (colVal.equals("K")) {
			return 10;
		}
		if (colVal.equals("L")) {
			return 11;
		}
		return 0;

	}
}
