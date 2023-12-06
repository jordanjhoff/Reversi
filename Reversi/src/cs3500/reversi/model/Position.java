package cs3500.reversi.model;

public interface Position {

  /**
   * Returns value of q.
   * @return this position's Q
   */
  public int getFirstCoordinate();

  /**
   * Returns value of r.
   * @return this position's R
   */
  public int getSecondCoordinate();
}
