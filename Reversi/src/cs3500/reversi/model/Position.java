package cs3500.reversi.model;

/**
 * Interface to represent a position on the board.
 */
public interface Position {

  /**
   * Returns value of q.
   * @return this position's Q
   */
  int getFirstCoordinate();

  /**
   * Returns value of r.
   * @return this position's R
   */
  int getSecondCoordinate();
}
