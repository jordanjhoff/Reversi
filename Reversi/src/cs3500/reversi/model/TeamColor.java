package cs3500.reversi.model;

/**
 * Represents an enumerated type of every game state.
 */
public enum TeamColor {
  /**
   * Available team colors.
   */
  WHITE("O"), BLACK("X");

  /**
   * The symbol representation of the color.
   */
  private final String symbol;



  private TeamColor(String str) {
    this.symbol = str;
  }


  /**
   * Cycles through the team colors.
   * @return the next team color in the cycle.
   */
  public TeamColor cycle() {
    if (this.symbol == WHITE.symbol) {
      return BLACK;
    }
    else {
      return WHITE;
    }
  }

  public String getName() {
    if (this.symbol == WHITE.symbol) {
      return "White";
    }
    else {
      return "Black";
    }
  }

  public String getSymbol() {
    return this.symbol;
  }



}
