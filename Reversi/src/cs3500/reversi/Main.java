package cs3500.reversi;

import cs3500.reversi.controller.AIPlayer;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.VisualController;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReadonlyHexReversiModel;
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
    /*
    ReversiStrategy textStrat = new UserTextInput(new Scanner(System.in));
    Appendable out = System.out;
    Player white = new PlayerImpl(TeamColor.WHITE, new CaptureMost());
    Player black = new PlayerImpl(TeamColor.BLACK, textStrat);
    ReversiModel modelText = new HexReversi(2); */


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



  }
}
