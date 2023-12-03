package cs3500.reversi.provider.model;

/**
 * An interface representing a Cell on the Reversi board.
 */
public interface ICell {
  /**
   * Sets the color of a cell to the color that is passed in.
   * @param color desired color to change the cell to
   */
  void setColor(Color color);

  /**
   * A string representation of the cell type.
   * @return a string based on what color the cell is.
   *     if the cell is black, "X"
   *     if the cell is white, "O"
   *     if cell is empty, "_"
   */
  String toString();

  /**
   * The coordinates of a cell within the board.
   * @return the q-coordinate(column) and r-coordinate(row) of a cell in an int array of size 2
   */
  int[] getCoordinates();

  /**
   * Checks if the chosen cell's color is the same as the cell type.
   * @param color to compare cell to
   * @return true if cell's color is the same as the parameter's color
   */
  boolean isColor(Color color);

  /**
   * Returns the CellType.
   * A CellType can either be:
   * - Black
   * - White
   * - Empty
   * @return cell type of cell
   */
  CellType returnType();
}