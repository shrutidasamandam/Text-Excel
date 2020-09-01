package textexcel;

public class TestLocation implements Location {
  private int row;
  private int col;

  public TestLocation(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getCol() {
    return col;
  }
}

