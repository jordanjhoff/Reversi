package cs3500.reversi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.ReversiView;

public class TextualController implements HexReversiController {
  private final ReversiModel model;
  private final ReversiView view;
  private final List<Player> players;
  private int playerIndex;

  public TextualController(ReversiModel model, ReversiView view) {
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
    this.playerIndex = 0;
    while (!this.model.isGameOver()) {
      try {
        this.view.render();
        HexPosition pos = this.players.get(this.playerIndex).play(new ReadonlyHexReversiModel(this.model));
        if (pos == null) {
          this.model.pass();
        }
        System.out.println(this.players.get(this.playerIndex).getColor().getSymbol());
        System.out.println(pos);

        this.model.addPiece(this.players.get(this.playerIndex).getColor(), pos);
        this.playerIndex = (this.playerIndex + 1) % this.players.size();
      } catch (Exception e) {
        if (e instanceof IOException) {
          throw new IllegalStateException("Unable to print");
        }
        if (e instanceof IllegalStateException) {
          System.out.println("here");
          System.out.println(e.getMessage());
        }
      }
    }
    try {
      view.render();
      if (this.model.getWinner() == null) {
        view.writeMessage("Game was tied!");
      }
      else {
        view.writeMessage(this.model.getWinner().getName() + " won the game!");
      }
    } catch (Exception e) {

    }
  }

  @Override
  public void handleInput(HexPosition pos) {

  }
}
