package cs3500.reversi.model;

import java.util.Objects;

/**
 * Class to represent a hexagonal position, represented by
 * three coordinates q, r, and s.
 * A position is invalid solely if s != -q - r.
 */
public class HexPosition implements Position {
  private final int q;
  private final int r;

  /**
   * Constructs a new position due to the three inputs, where s must equal
   * -q - r.
   * @param q q coordinate
   * @param r r coordinate
   * @throws IllegalArgumentException if position is invalid/illegal
   */
  public HexPosition(int q, int r) {
    this.q = q;
    this.r = r;
  }

  /**
   * Copy constructor for generating a new object of a position
   * with the same values as the parameter to avoid aliasing issues.
   * @param pos to be copied.
   */
  public HexPosition(HexPosition pos) {
    this.q = pos.q;
    this.r = pos.r;
  }

  @Override
  public int getFirstCoordinate() {
    return this.q;
  }

  @Override
  public int getSecondCoordinate() {
    return this.r;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof HexPosition)) {
      return false;
    }
    else {
      HexPosition that = (HexPosition) other;
      return this.q == that.q
              && this.r == that.r;
    }
  }

  @Override
  public String toString() {
    int s = -q - r;
    return "[" + this.q + "," + this.r + "," + s + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.q, this.r);
  }

}
