package cs3500.reversi.provider.model;

/**
 * Our implementation of our providers ICell interface. Contains coordinates q and r, as well
 * as a color.
 */
public class Cell implements ICell {
  private int q;
  private int r;
  private Color color;

  /**
   * Constructs a cell at the given coordinates.
   * @param q coordinate q
   * @param r coordinate r
   */
  public Cell(int q, int r) {
    this.q = q;
    this.r = r;
  }

  @Override
  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public int[] getCoordinates() {
    return new int[]{this.q, this.r};
  }

  @Override
  public boolean isColor(Color color) {
    return color.equals(this.color);
  }

  @Override
  public CellType returnType() {
    if (color == null) {
      return CellType.EMPTY;
    }
    return color == Color.BLACK ? CellType.BLACK : CellType.WHITE;
  }
}
