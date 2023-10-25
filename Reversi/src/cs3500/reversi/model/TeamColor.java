package cs3500.reversi.model;

/**
 * Represents an enumerated type of every game state.
 */
public enum TeamColor {
  WHITE("O"), BLACK("X");

  public final String symbol;

  private TeamColor(String str) {
    this.symbol = str;
  }


  public TeamColor cycle() {
    if (this.symbol == WHITE.symbol) {
      return BLACK;
    }
    else {
      return WHITE;
    }
  }


}
