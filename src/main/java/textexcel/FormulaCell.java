package textexcel;

public class FormulaCell extends RealCell {
	Spreadsheet mySpreadsheet;

	public FormulaCell(String inputValue, Spreadsheet sheet) {
		super(inputValue);
		super.fullCellText = inputValue;
		mySpreadsheet = sheet;
		super.abbreviatedCellText = Double.toString(getDoubleValue());
		if (super.abbreviatedCellText.length() < 10) {
			int numSpaces = 10 - super.abbreviatedCellText.length();
			for (int i = 0; i < numSpaces; i++) {
				super.abbreviatedCellText = super.abbreviatedCellText + " ";
			}
		} else if (super.abbreviatedCellText.length() > 10) {
			super.abbreviatedCellText = super.abbreviatedCellText.substring(0, 10);
		}

	}

	public double getDoubleValueAverageOrSum(String input) {
		// looks for average or sum
		boolean calculateAverage = input.contains("AVG");
		boolean calculateSum = input.contains("SUM");
		input = input.replace("AVG ", "");
		input = input.replace("SUM ", "");
		String firstCell = input.substring(0, input.indexOf("-"));
		System.out.println("Firstcell=" + firstCell);
		String secondCell = input.substring(input.indexOf("-") + 1);
		System.out.println("secondcell=" + secondCell);
		// to get location of the cells
		SpreadsheetLocation locationOfFirstCell = new SpreadsheetLocation(firstCell);
		SpreadsheetLocation locationOfSecondCell = new SpreadsheetLocation(secondCell);
		System.out.print("locationOfFirstCell.getRow()=" + locationOfFirstCell.getRow()
				+ ",locationOfFirstCell.getCol()=" + locationOfFirstCell.getCol());
		System.out.print("locationOfSecondCell.getRow()=" + locationOfSecondCell.getRow()
				+ ",locationOfFirstCell.getCol()=" + locationOfSecondCell.getCol());
		// get range of the cells
		int count = 0;
		double result = 0;
		for (int i = locationOfFirstCell.getRow(); i <= locationOfSecondCell.getRow(); i++) {
			for (int j = locationOfFirstCell.getCol(); j <= locationOfSecondCell.getCol(); j++) {
				System.out.println("i=" + i + ",j=" + j);
				result = result + ((RealCell) this.mySpreadsheet.getCell(i, j)).getDoubleValue();
				count = count + 1;
				System.out.println("Result is" + result);
				System.out.println("Count is" + count);
			}
		}
		if (calculateAverage) {
			result = result / count;
		}
		return result;
	}

	public double getDoubleValue() {
		// finding double value
		String input = fullCellText.toUpperCase();
		input = input.replace(" )", "");
		input = input.replace("( ", "");
		System.out.println("input=" + input);
		if (input.startsWith("AVG") || input.startsWith("SUM")) {
			System.out.println("In average:");
			return getDoubleValueAverageOrSum(input);
		}
		String[] inputArr = (input.split(" "));
		int firstIndex = 0;
		double result = 0.0;
		String startingVal = inputArr[firstIndex];
		if (startingVal.startsWith("A") || startingVal.startsWith("B") || startingVal.startsWith("C")
				|| startingVal.startsWith("D") || startingVal.startsWith("E") || startingVal.startsWith("F")
				|| startingVal.startsWith("G") || startingVal.startsWith("H") || startingVal.startsWith("I")
				|| startingVal.startsWith("J") || startingVal.startsWith("K") || startingVal.startsWith("L")) {
			// trying to find what the cell value is
			// then you convert reference to location
			SpreadsheetLocation location = new SpreadsheetLocation(startingVal);
			// grab what was stored in the location
			RealCell rcell = (RealCell) mySpreadsheet.getCell(location);
			result = rcell.getDoubleValue();
			// then plug it back into the rest of the formula
		} else {
			result = Double.parseDouble(startingVal);
		}
		if (inputArr.length == 1) {
			return result;
		} else {
			// System.out.println("startingV..al"+startingVal);
			while (true) {
				String firstOperand = inputArr[firstIndex + 1];
				// System.out.print("firstOperand is" + firstOperand);
				String secondVal = inputArr[firstIndex + 2];
				// if secondVal is a reference, then get the cell value from spreadsheet
				double secondValDbl = 0;
				if (secondVal.startsWith("A") || secondVal.startsWith("B") || secondVal.startsWith("C")
						|| secondVal.startsWith("D") || secondVal.startsWith("E") || secondVal.startsWith("F")
						|| secondVal.startsWith("G") || secondVal.startsWith("H") || secondVal.startsWith("I")
						|| secondVal.startsWith("J") || secondVal.startsWith("K") || secondVal.startsWith("L")) {
					// trying to find what the cell value is
					// then you convert reference to location
					SpreadsheetLocation location = new SpreadsheetLocation(secondVal);
					// grab what was stored in the location
					RealCell rcell = (RealCell) mySpreadsheet.getCell(location);
					secondValDbl = rcell.getDoubleValue();
					// then plug it back into the rest of the formula
				} else {
					secondValDbl = Double.parseDouble(secondVal);
				}
				// checking for operands
				// System.out.println("secondVal is" + secondVal);
				if (firstOperand.equals("+")) {
					result = result + secondValDbl;
				} else if (firstOperand.equals("*")) {
					result = result * secondValDbl;
				} else if (firstOperand.equals("-")) {
					result = result - secondValDbl;
				} else if (firstOperand.equals("/")) {
					result = result / secondValDbl;
				} else {
					// System.out.println("Invalid Operator");
					break;
				}
				// startingVal = Double.toString(result);
				firstIndex = firstIndex + 2;
				// System.out.println("firstIndex is"+ firstIndex);
				// System.out.println("inputArr.length is"+ inputArr.length);
				if ((firstIndex + 2 > inputArr.length - 1)) {
					// System.out.println("Incomplete equation");
				}
				if ((firstIndex >= inputArr.length - 1) || (firstIndex + 2 > inputArr.length - 1)) {
					break;

				}
			}
		}
		return result;
	}

}
