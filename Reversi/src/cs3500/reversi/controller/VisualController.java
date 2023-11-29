package cs3500.reversi.controller;

import java.io.IOException;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.IModelFeatures;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.IViewFeatures;

public class VisualController implements HexReversiController, IViewFeatures, IModelFeatures {
  private final ReversiModel model;
  private final IReversiView view;
  private final List<Player> players;
  private int playerIndex;

  public VisualController(ReversiModel model, IReversiView view) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.view.addFeatureListener(this);
    this.model.addFeatureListener(this);
  }

  @Override
  public void addPlayer(Player player) {

  }

  @Override
  public void play() {
    this.playerIndex = 0;
    this.view.setVisible(true);
    while (!this.model.isGameOver() && myTurn) {
      try {
        HexPosition pos = this.players.get(this.playerIndex).play(new ReadonlyHexReversiModel(this.model));
        if (pos == null) {
          this.model.pass();
        }
        this.model.addPiece(this.players.get(this.playerIndex).getColor(), pos);
        this.playerIndex = (this.playerIndex + 1) % this.players.size();
      } catch (Exception e) {
        if (e instanceof IOException) {
          throw new IllegalStateException("Unable to print");
        }
        if (e instanceof IllegalStateException) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  @Override
  public void makeMove(HexPosition posn) {
    if (!player.isAI()) {
      try {
        this.model.addPiece(this.player.getColor(), posn);
      } catch (IllegalStateException e) {
        this.notifyMessage(this.player.getColor(), e.getMessage());
      }

    }
    else {
      HexPosition chosenPosn = this.player.play(new ReadonlyHexReversiModel(model));
      if (chosenPosn == null) {
        this.model.pass();
      }
      else {
        this.model.addPiece(this.player.getColor(), chosenPosn);
      }

    }
  }

  @Override
  public void passTurn() {
    this.model.pass();
  }

  @Override
  public void quit() {
    System.exit(0);
  }

  @Override
  public void notifyUpdateView() {
    this.view.renderView(this.player.getColor());
  }

  @Override
  public void startGame() {
    this.view.setVisible(true);
    if (myTurn) {
      displayToNonAI("Welcome to Reversi! You start");
    }

  }

  @Override
  public void notifyMessage(TeamColor color, String message) {
    if (this.player.getColor().equals(color) && !player.isAI()) {
      displayToNonAI(message);
    }
  }

  @Override
  public void notifyGameOver() {
    this.myTurn = false;
    this.view.displayMessage("Game over! " + model.getWinner() + " WINS!!");
  }

  @Override
  public void notifyCurrentTurn(TeamColor color) {
    this.myTurn = this.player.getColor().equals(color);
    if (!player.isAI()) {
      this.view.enableMoves(myTurn);
    }
    else if (player.isAI() && myTurn){
      this.play();
    }

  }

  private void displayToNonAI(String message) {
    if (!player.isAI()) {
      this.view.displayMessage(message);
    }
  }


}
