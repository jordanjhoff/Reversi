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
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.strategy.VisualUserStrat;
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

    if (args.length == 0) {
      // default case to figure out
    }
    else if (args == null || args.length != 3) {
      throw new IllegalArgumentException("Code 1");
    }
    try {
      // FIRST NUMBER IS BOARD SIZE
      ReversiModel model = new HexReversi(Integer.parseInt(args[0]));
      Player player1 = parsePlayer(args[1], TeamColor.BLACK, new ReadonlyHexReversiModel(model));
      Player player2 = parsePlayer(args[1], TeamColor.WHITE, new ReadonlyHexReversiModel(model));
      IReversiView viewPlayer1 = new ReversiGUIView(model);
      IReversiView viewPlayer2 = new ReversiGUIView(model);
      VisualController controller1 = new VisualController(model, viewPlayer1, player1);
      VisualController controller2 = new VisualController(model, viewPlayer2, player2);
      model.startGame();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("invalid input");
    }

    /*
    ReversiModel model = new HexReversi(3);
    IReversiView viewPlayer1 = new ReversiGUIView(model);
    IReversiView viewPlayer2 = new ReversiGUIView(model);
    Player AIplayer1 = new AIPlayer(TeamColor.BLACK,
            new CaptureMost(),
            new ReadonlyHexReversiModel(model));
    Player AIplayer2 = new AIPlayer(TeamColor.WHITE,
            new CaptureMost(),
            new ReadonlyHexReversiModel(model));
    Player player1 = new HumanPlayer(TeamColor.WHITE);
    Player player2 = new HumanPlayer(TeamColor.BLACK);
    VisualController controller1 = new VisualController(model, viewPlayer1, AIplayer1);
    VisualController controller2 = new VisualController(model, viewPlayer2, AIplayer2);
    model.startGame();

     */
  }


  private static Player parsePlayer(String arg, TeamColor color, ReadonlyReversiModel rorModel) {
    if (arg.toLowerCase().equals("player")) {
      return new HumanPlayer(color);
    }
    else if (arg.toLowerCase().equals("ai")){
      return  new AIPlayer(color, new CaptureMost(), rorModel);
    }
    else {
      throw new IllegalArgumentException("illegal arguments");
    }
  }
}
