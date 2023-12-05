package cs3500.reversi;

import cs3500.reversi.controller.AIPlayer;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.VisualController;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.provider.model.AdaptedProviderReversiReadOnly;
import cs3500.reversi.provider.strat.AvoidCornersStrategy;
import cs3500.reversi.provider.strat.CornersStrategy;
import cs3500.reversi.provider.strat.MostPiecesStrategy;
import cs3500.reversi.provider.strat.StrategyAdaptor;
import cs3500.reversi.provider.view.AdaptedProviderView;
import cs3500.reversi.provider.view.IView;
import cs3500.reversi.provider.view.View;
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.ReversiGUIView;

/**
 * A main class to execute and view a HexReversi game.
 */
public class Main {

  /**
   * A main method to execute and view a HexReversi game.
   */
  public static void main(String[] args) {

    if (args == null || args.length != 3) {
      throw new IllegalArgumentException("Code 1");
    }
    else {
      try {
        // FIRST NUMBER IS BOARD SIZE
        ReversiModel model = new HexReversi(Integer.parseInt(args[0]));
        Player player1 = parseProviderPlayer(args[1], TeamColor.BLACK, new ReadonlyHexReversiModel(model));
        Player player2 = parseProviderPlayer(args[2], TeamColor.WHITE, new ReadonlyHexReversiModel(model));
        IReversiView viewPlayer1 = new ReversiGUIView(model);
        IView viewPlayer2 = new View(new AdaptedProviderReversiReadOnly(new ReadonlyHexReversiModel(model)));
        IReversiView adaptedViewPlayer2 = new AdaptedProviderView(viewPlayer2);
        VisualController controller1 = new VisualController(model, viewPlayer1, player1);
        VisualController controller2 = new VisualController(model, adaptedViewPlayer2, player2);
        model.startGame();
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("invalid input");
      }
    }
  }


  private static Player parsePlayer(String arg, TeamColor color, ReadonlyReversiModel rorModel) {
    if (arg.toLowerCase().equals("human")) {
      return new HumanPlayer(color);
    }
    else if (arg.toLowerCase().equals("ai")) {
      return  new AIPlayer(color, new CaptureMost(), rorModel);
    }
    else {
      throw new IllegalArgumentException("illegal arguments");
    }
  }

  private static Player parseProviderPlayer(String arg, TeamColor color, ReadonlyReversiModel rorModel) {
    if (arg.toLowerCase().equals("human")) {
      return new HumanPlayer(color);
    }
    else if (arg.toLowerCase().equals("providerstrategy1")) {
      return  new AIPlayer(color, new StrategyAdaptor(new MostPiecesStrategy()), rorModel);
    }
    else if (arg.toLowerCase().equals("providerstrategy2")) {
      return  new AIPlayer(color, new StrategyAdaptor(new CornersStrategy()), rorModel);
    }
    else if (arg.toLowerCase().equals("providerstrategy3")) {
      return  new AIPlayer(color, new StrategyAdaptor(new AvoidCornersStrategy()), rorModel);
    }

    else {
      throw new IllegalArgumentException("illegal arguments");
    }
  }
}
