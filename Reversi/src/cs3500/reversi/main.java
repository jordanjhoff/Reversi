package cs3500.reversi;

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

public class main {
  public static void main(String[] args) {
    ReversiStrategy textStrat = new UserTextInput(new Scanner(System.in));
    Appendable out = System.out;
    Player white = new PlayerImpl(TeamColor.WHITE, new CaptureMost());
    Player black = new PlayerImpl(TeamColor.BLACK, textStrat);
    ReversiModel modelText = new HexReversi(2);
    ReversiView viewText = new ReversiTextualView(new ReadonlyHexReversiModel(modelText), out);
    IReversiView viewGui = new ReversiGUIView(new ReadonlyHexReversiModel(modelText));
    viewGui.setVisible(true);
  }
}
