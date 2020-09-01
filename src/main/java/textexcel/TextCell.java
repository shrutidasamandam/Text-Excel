package textexcel;

public class TextCell implements Cell {
	private String abbreviatedCellText;
	private String fullCellText;

	// converting it to fit into cells for abbreviated cell text
	public TextCell(String abbreviatedCellText, String fullCellText) {
		this.abbreviatedCellText = fullCellText.replaceAll("\"", "");
		if (this.abbreviatedCellText.length() < 10) {
			int numSpaces = 10 - this.abbreviatedCellText.length();
			for (int i = 0; i < numSpaces; i++) {
				this.abbreviatedCellText = this.abbreviatedCellText + " ";
			}
		} else if (this.abbreviatedCellText.length() > 10) {
			this.abbreviatedCellText = this.abbreviatedCellText.substring(0, 10);
		}
		// whatever user inputted, leave it the same
		this.fullCellText = fullCellText;
	}

	@Override
	public String abbreviatedCellText() {
//		
//		String retVal = abbreviatedCellText;
//		if(abbreviatedCellText.length()<10) {
//			int numSpaces = 10 - abbreviatedCellText.length();
//			for(int i=0;i<numSpaces;i++) {
//				retVal = retVal + " ";
//			}
//		}else if(abbreviatedCellText.length()>10){
//			retVal = abbreviatedCellText.substring(0, 10); 
//		}
		return abbreviatedCellText;
	}

	@Override
	public String fullCellText() {
//		String retVal = "";
//		if(fullCellText.length()>0) {
//			retVal = "\"" + fullCellText + "\"";
//		}
		return fullCellText;
	}
}
