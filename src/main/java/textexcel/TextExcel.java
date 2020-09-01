package textexcel;

import java.util.*;

public class TextExcel {
	public static void main(String[] args) {
		Spreadsheet myspreadsheet = new Spreadsheet();
		//System.out.println(myspreadsheet.processCommand("A1 = \"ThisIsALongString\""));
		
		// uses scanner	
		Scanner sc = new Scanner(System.in);
		while (true) {
			String input = sc.nextLine();
			if (input.equals("quit")) {
				break;
			} else {
				System.out.println(myspreadsheet.processCommand(input));
				//System.out.println(myspreadsheet.getGridText());
			}
		}
		
		sc.close();
	}
}