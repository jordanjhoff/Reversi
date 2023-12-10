package cs3500.reversi.strategy;

import cs3500.reversi.model.Position;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.TeamColor;

/**
 * Interface to represent a REVERSISTRATEGY, which is a type
 * of function class to return a possible move by whichever means.
 *
 */
public interface ReversiStrategy {

  /**
   * Select a move due to the strategies method.
   * @param model the model to pick the move from
   * @param color the team color to choose for
   * @return the chosen position to make
   */
  Position choosePosn(ReadonlyReversiModel model, TeamColor color);
}
