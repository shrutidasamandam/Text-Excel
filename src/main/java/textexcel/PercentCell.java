package textexcel;

public class PercentCell extends RealCell {

	public PercentCell(String inputValue) {
		// input value
		super(inputValue);
		// the abbreviated cell text
		super.abbreviatedCellText = inputValue.replaceAll("%", "");
		super.abbreviatedCellText = Integer.toString((int) Double.parseDouble(abbreviatedCellText)) + "%";
		if (super.abbreviatedCellText.length() < 10) {
			int numSpaces = 10 - super.abbreviatedCellText.length();
			for (int i = 0; i < numSpaces; i++) {
				super.abbreviatedCellText = super.abbreviatedCellText + " ";
			}
		} else if (super.abbreviatedCellText.length() > 10) {
			super.abbreviatedCellText = super.abbreviatedCellText.substring(0, 10);
		}
		// full cell text
//		String withoutPercent = inputValue.replaceAll("%", "");
//		double numberWithoutPercent = Double.parseDouble(withoutPercent);
//		numberWithoutPercent = numberWithoutPercent/100;
		super.fullCellText = Double.toString(getDoubleValue());
	}

	// double value
	public double getDoubleValue() {
		String withoutPercent = inputValue.replaceAll("%", "");
		double numberWithoutPercent = Double.parseDouble(withoutPercent);
		numberWithoutPercent = numberWithoutPercent / 100;
		return numberWithoutPercent;
	}

}