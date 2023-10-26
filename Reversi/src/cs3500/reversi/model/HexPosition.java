package cs3500.reversi.model;

import java.util.Objects;

public class HexPosition {
  private final int q;
  private final int r;
  private final int s;

  public HexPosition(int q, int r, int s) {
    this.q = q;
    this.r = r;
    this.s = s;

    if (!(q == -r - s)) {
      throw new IllegalArgumentException("invalid coordinate build");
    }
  }

  public HexPosition(HexPosition pos) {
    this.q = pos.q;
    this.r = pos.r;
    this.s = pos.s;

    if (!(pos.s == -pos.q - pos.r)) {
      throw new IllegalArgumentException("invalid coordinate build");
    }
  }

  public int getQPosition() {
    return this.q;
  }

  public int getRPosition() {
    return this.r;
  }

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
