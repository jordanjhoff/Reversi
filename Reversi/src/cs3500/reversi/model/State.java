package cs3500.reversi.model;

/**
 * Represents an enumerated type of every game state.
 */
public enum State {
  WHITE("O"), BLACK("X"), EMPTY("_");

  String symbol;
  State(String str) {
    this.symbol = str;
  }
}
