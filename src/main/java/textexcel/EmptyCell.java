package textexcel;

public class EmptyCell implements Cell {
	// instance variables
	String abbreviatedCellText = "          ";
	String fullCellText = "";

	@Override
	// abbreviated cell text
	public String abbreviatedCellText() {
		return abbreviatedCellText;
	}

	@Override
	// full cell text
	public String fullCellText() {
		return fullCellText;
	}

}
