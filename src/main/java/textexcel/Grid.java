////////////////////////////////////////////////////////////////////////
//                    DO NOT MODIFY THIS FILE!!!!                     //
////////////////////////////////////////////////////////////////////////

package textexcel;

public interface Grid {
  String processCommand(String command);

  int getRows();

  int getCols();

  Cell getCell(Location loc);

  String getGridText();
}
