package textexcel;

public class ValueCell extends RealCell {

	public ValueCell(String inputValue) {
		// input value
		super(inputValue);
		super.fullCellText = inputValue;
		// abbreviated cell text
		super.abbreviatedCellText = inputValue;
		abbreviatedCellText = Double.toString(Double.parseDouble(abbreviatedCellText));
		if (super.abbreviatedCellText.length() < 10) {
			int numSpaces = 10 - super.abbreviatedCellText.length();
			for (int i = 0; i < numSpaces; i++) {
				super.abbreviatedCellText = super.abbreviatedCellText + " ";
			}
		} else if (super.abbreviatedCellText.length() > 10) {
			super.abbreviatedCellText = super.abbreviatedCellText.substring(0, 10);
		}
		// full text

	}

	public double getDoubleValue() {
		return (Double.parseDouble(inputValue));
	}

}
