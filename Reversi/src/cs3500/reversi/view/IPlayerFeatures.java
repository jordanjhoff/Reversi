package cs3500.reversi.view;

import cs3500.reversi.model.HexPosition;

public interface IPlayerFeatures {

  /**
   * Notify the listener that a move was made.
   *
   * @param posn the position for the move to be made
   */
  default void makeMove(HexPosition posn) {

  }

  /**
   * Notify the listener that a turn was passed.
   */
  void passTurn();
}
