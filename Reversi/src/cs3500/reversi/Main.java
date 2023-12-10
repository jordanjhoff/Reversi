package cs3500.reversi;

import cs3500.reversi.controller.AIPlayer;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.VisualController;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.ReversiGUIView;
import cs3500.reversi.view.ReversiSquareTextualView;

/**
 * A main class to execute and view a HexReversi game.
 */
public class Main {

  /**
   * A main method to execute and view a HexReversi game.
   */
  public static void main(String[] args) {
    if (args == null || args.length != 4) {
      throw new IllegalArgumentException("Code 1");
    }
    else {
      try {
        //5 human ai hints square
        boolean square = hexOrSquare(args[3]);
        ReversiModel model;
        if (square) {
          model = new SquareReversi(Integer.parseInt(args[0]));
          Player player1 = parseProviderPlayer(args[1], TeamColor.BLACK,
                  new ReadonlyHexReversiModel(model));
          Player player2 = parseProviderPlayer(args[2], TeamColor.WHITE,
                  new ReadonlyHexReversiModel(model));
          IReversiView viewPlayer1 = new ReversiGUIView(model, true, true);
          IReversiView viewPlayer2 = new ReversiGUIView(model, true, true);
          IReversiView textviewPlayer2 = new ReversiSquareTextualView(model);
          VisualController controller1 = new VisualController(model, viewPlayer1, player1);
          VisualController controller2 = new VisualController(model, viewPlayer2, player2);
        }
        else {
          //5 human ai hints hex
          model = new HexReversi(Integer.parseInt(args[0]));
          Player player1 = parseProviderPlayer(args[1], TeamColor.BLACK,
                  new ReadonlyHexReversiModel(model));
          Player player2 = parseProviderPlayer(args[2], TeamColor.WHITE,
                  new ReadonlyHexReversiModel(model));
          IReversiView viewPlayer1 = new ReversiGUIView(model, true, false);
          IReversiView viewPlayer2 = new ReversiGUIView(model, true, false);
          VisualController controller1 = new VisualController(model, viewPlayer1, player1);
          VisualController controller2 = new VisualController(model, viewPlayer2, player2);
        }
        model.startGame();
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("invalid input");
      }
        }
    }


  private static Player parseProviderPlayer(String arg, TeamColor color,
                                            ReadonlyReversiModel rorModel) {
    if (arg.toLowerCase().equals("human")) {
      return new HumanPlayer(color);
    }
    else if (arg.toLowerCase().equals("ai")) {
      return new AIPlayer(color, new CaptureMost(), rorModel);
    }
    else {
      throw new IllegalArgumentException("illegal arguments");
    }
  }

  private static boolean hexOrSquare(String arg) {
    if (arg.toLowerCase().equals("hex")) {
      return false;
    }
    else if (arg.toLowerCase().equals("square")) {
      return true;
    }
    else {
      throw new IllegalArgumentException("illegal arguments");
    }
  }
}
