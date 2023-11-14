package cs3500.reversi;

import java.util.Scanner;

import cs3500.reversi.controller.TextualController;
import cs3500.reversi.controller.HexReversiController;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerImpl;
import cs3500.reversi.controller.VisualController;
import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.strategy.UserTextInput;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.IViewFeatures;
import cs3500.reversi.view.ReversiGUIView;
import cs3500.reversi.view.ReversiTextualView;
import cs3500.reversi.view.ReversiView;

public class main {
  public static void main(String[] args) {
    ReversiModel model = new HexReversi(8); /*
    //black's turn
    model.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    //white's turn
    model.addPiece(TeamColor.WHITE, new HexPosition(2,-1,-1));
    //black's turn
    model.addPiece(TeamColor.BLACK, new HexPosition(1,1,-2));
    //white's turn
    model.pass();
    //black's turn
    model.addPiece(TeamColor.BLACK, new HexPosition(-1,-1,2));
    //white's turn
    model.addPiece(TeamColor.WHITE, new HexPosition(-1,2,-1));
    //black's turn
    model.pass();
    //white's turn
    model.addPiece(TeamColor.WHITE, new HexPosition(-2,1,1));

    IReversiView view = new ReversiGUIView(model);
    HexReversiController controller = new VisualController(model, view);
    controller.play();
    */

    ReversiStrategy textStrat = new UserTextInput(new Scanner(System.in));
    Appendable out = System.out;
    Player white = new PlayerImpl(TeamColor.WHITE, textStrat);
    Player black = new PlayerImpl(TeamColor.BLACK, textStrat);
    ReversiModel modelText = new HexReversi(2);
    ReversiView viewText = new ReversiTextualView(new ReadonlyHexReversiModel(modelText), out);
    HexReversiController controllerText = new TextualController(modelText, viewText);
    controllerText.addPlayer(black);
    controllerText.addPlayer(white);
    controllerText.play();

  }
}
