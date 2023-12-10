package cs3500.reversi.view;

import cs3500.reversi.model.Position;

/**
 * This observer interface represents features that allow manipulation
 * of moves within a Reversi game. A class that implements these
 * methods would process these actions.
 */
public interface MoveFeatures {

  /**
   * Notify the observer that a move was made.
   *
   * @param posn the position for the move to be made
   */
  void notifyMakeMove(Position posn);

  /**
   * Notify the observer that a turn was passed.
   */
  void notifyPassTurn();
}
