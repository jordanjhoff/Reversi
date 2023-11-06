package cs3500.reversi.model;

import java.util.Objects;

/**
 * Class to represent a hexagonal position, represented by
 * three coordinates q, r, and s.
 * A position is invalid solely if s != -q - r.
 */
public class HexPosition {
  private final int q;
  private final int r;
  private final int s;

  /**
   * Constructs a new position due to the three inputs, where s must equal
   * -q - r.
   * @param q q coordinate
   * @param r r coordinate
   * @param s s coordinate
   * @throws IllegalArgumentException if position is invalid/illegal
   */
  public HexPosition(int q, int r, int s) {
    this.q = q;
    this.r = r;
    this.s = s;

    if (q != -r - s) {
      throw new IllegalArgumentException("invalid coordinate build");
    }
  }

  /**
   * Copy constructor for generating a new object of a position
   * with the same values as the parameter to avoid aliasing issues.
   * @param pos to be copied.
   */
  public HexPosition(HexPosition pos) {
    this.q = pos.q;
    this.r = pos.r;
    this.s = pos.s;
  }

  /**
   * Returns value of q.
   * @return this position's Q
   */
  public int getQPosition() {
    return this.q;
  }

  /**
   * Returns value of r.
   * @return this position's R
   */
  public int getRPosition() {
    return this.r;
  }

  /**
   * Returns value of S.
   * @return this position's S
   */
  public int getSPosition() {
    return this.s;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof HexPosition)) {
      return false;
    }
    else {
      HexPosition that = (HexPosition) other;
      return this.q == that.q
              && this.r == that.r
              && this.s == that.s;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.q, this.r, this.s);
  }

}
