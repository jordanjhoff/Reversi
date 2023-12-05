package cs3500.reversi.provider.strat;

import java.util.List;
import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.provider.model.AdaptedProviderReversiReadOnly;
import cs3500.reversi.provider.model.ICell;
import cs3500.reversi.strategy.ReversiStrategy;

/**
 * An adaptor to adapt our provider's strategy to our strategy interface.
 */
public class StrategyAdaptor implements ReversiStrategy {

  private Strategy adaptee;

  /**
   * A constructor to adapt our provider's strategy.
   * @param providedStrategy our provider's strategy.
   */
  public StrategyAdaptor(Strategy providedStrategy) {
    this.adaptee = providedStrategy;
  }

  @Override
  public HexPosition choosePosn(ReadonlyReversiModel model, TeamColor color) {

    List<ICell> moves = this.adaptee.chooseMove(new AdaptedProviderReversiReadOnly(model));
    if (moves.isEmpty()) {
      return model.getValidMoves().isEmpty() ? null : model.getValidMoves().get(0);
    }
    else {
      int q = moves.get(0).getCoordinates()[0];
      int r = moves.get(0).getCoordinates()[1];

      return new HexPosition(q, r, -q - r);
    }
  }
}
