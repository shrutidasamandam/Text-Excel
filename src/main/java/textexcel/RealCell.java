package textexcel;

public class RealCell implements Cell {
	public String abbreviatedCellText;
	public String fullCellText;
	public String inputValue;
	public RealCell (String inputValue) {
		this.inputValue = inputValue;
		
	}
	@Override
	// abbreviated cell text
	public String abbreviatedCellText() {
		abbreviatedCellText = Double.toString(getDoubleValue());
		if (abbreviatedCellText.length() < 10) {
			int numSpaces = 10 - abbreviatedCellText.length();
			for (int i = 0; i < numSpaces; i++) {
				abbreviatedCellText = abbreviatedCellText + " ";
			}
		} else if (abbreviatedCellText.length() > 10) {
			abbreviatedCellText = abbreviatedCellText.substring(0, 10);
		}
		return abbreviatedCellText;
	}

	@Override
	// full cell text
	public String fullCellText() {
		return fullCellText;
	}
	// input value
	public String inputValue() {
		return inputValue;
	}
	
	public double getDoubleValue() {
		return 0D;
	}
}
