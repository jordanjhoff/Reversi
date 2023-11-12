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
    ReversiModel model = new HexReversi(8);
    model.addPiece(new HexPosition(1,-2,1));
    model.addPiece(new HexPosition(2,-1,-1));
    model.addPiece(new HexPosition(1,1,-2));
    model.addPiece(new HexPosition(-1,2,-1));
    model.addPiece(new HexPosition(-2,1,1));
    model.addPiece(new HexPosition(-1,-1,2));

    IReversiView view = new ReversiGUIView(model);
    HexReversiController controller = new VisualController(model, view);
    controller.play();
  }
}
