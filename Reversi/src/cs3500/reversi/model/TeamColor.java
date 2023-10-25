package cs3500.reversi.model;

/**
 * Represents an enumerated type of every game state.
 */
public enum TeamColor {
  WHITE("O"), BLACK("X");

  String symbol;
  TeamColor(String str) {
    this.symbol = str;
  }

  @Override
  public String toString() {
    return this.symbol;
  }


}
