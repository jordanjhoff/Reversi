package cs3500.reversi;

import java.util.Calendar;
import java.util.Scanner;

import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerImpl;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.strategy.UserTextInput;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.ReversiGUIView;
import cs3500.reversi.view.ReversiTextualView;
import cs3500.reversi.view.ReversiView;

/**
 * A main class to execute and view a HexReversi game.
 */
public class Main {

  /**
   * A main method to execute and view a HexReversi game.
   */
  public static void main(String[] args) {
    ReversiStrategy textStrat = new UserTextInput(new Scanner(System.in));
    Appendable out = System.out;
    Player white = new PlayerImpl(TeamColor.WHITE, new CaptureMost());
    Player black = new PlayerImpl(TeamColor.BLACK, textStrat);
    ReversiModel modelText = new HexReversi(2); */


    ReversiModel model = new HexReversi(3);
    IReversiView viewPlayer1 = new ReversiGUIView(model);
    IReversiView viewPlayer2 = new ReversiGUIView(model);
    Player player1 = new PlayerImpl(TeamColor.BLACK, new CaptureMost());
    Player player2 = new PlayerImpl(TeamColor.WHITE, new CaptureMost());
    HexReversiController controller1 = new VisualController(model, viewPlayer1, player1);
    HexReversiController controller2 = new VisualController(model, viewPlayer2, player2);
    model.startGame();



  }
}
