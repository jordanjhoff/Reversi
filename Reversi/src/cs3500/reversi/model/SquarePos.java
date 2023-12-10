package cs3500.reversi.model;

import java.util.Objects;

/**
 * Class to represent a cartesian coordinate position
 * for the game of square reversi.
 */
public class SquarePos implements Position {
  private final int x;
  private final int y;

  /**
   * Constructs a new position due to two inputs.
   * @param x x coordinate
   * @param y y coordinate
   * @throws IllegalArgumentException if position is invalid/illegal
   */
  public SquarePos(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Copy constructor for generating a new object of a position
   * with the same values as the parameter to avoid aliasing issues.
   * @param pos to be copied.
   */
  public SquarePos(SquarePos pos) {
    this.x = pos.getFirstCoordinate();
    this.y = pos.getSecondCoordinate();
  }

  @Override
  public int getFirstCoordinate() {
    return this.x;
  }

  @Override
  public int getSecondCoordinate() {
    return this.y;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof SquarePos)) {
      return false;
    }
    else {
      SquarePos that = (SquarePos) other;
      return this.x == that.x
              && this.y == that.y;
    }
  }

  @Override
  public String toString() {
    return "[" + this.x + "," + this.y + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

}
