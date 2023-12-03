package cs3500.reversi.provider.strat;

import java.util.List;
import cs3500.reversi.provider.model.ICell;
import cs3500.reversi.provider.model.ReadonlyReversiModel;

/**
 * The interface that holds the positional strategies to be used in the game of Reversi.
 */
public interface Strategy {
  /**
   * Chooses a position in the game Reversi.
   * @param model the model of the game Reversi.
   * @return the current position to try a strategy on in the game Reversi.
   */
  List<ICell> chooseMove(ReadonlyReversiModel model);
}
