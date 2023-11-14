package cs3500.reversi.controller;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.IReversiView;

public class VisualController implements HexReversiController {
  private final ReversiModel model;
  private final IReversiView view;
  private final List<Player> players;
  private int playerIndex;

  public VisualController(ReversiModel model, IReversiView view) {
    this.model = model;
    this.view = view;
    this.players = new ArrayList<Player>();
  }

  @Override
  public void addPlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    this.players.add(player);
  }

  @Override
  public void play() {
    this.view.setVisible(true);
  }

  @Override
  public void handleInput(HexPosition pos) {
    if (pos == null) {
      this.model.pass();
    }
    //this.model.addPiece(pos);
  }
}
