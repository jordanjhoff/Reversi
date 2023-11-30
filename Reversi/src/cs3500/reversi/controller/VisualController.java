package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.IModelFeatures;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.IPlayerFeatures;

public class VisualController implements IPlayerFeatures, IModelFeatures {
  private final ReversiModel model;
  private final IReversiView view;
  private final Player player;

  private boolean myTurn;

  public VisualController(ReversiModel model, IReversiView view, Player player) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.view.addFeatureListener(this);
    this.model.addFeatureListener(this);
    this.player.addFeatureListener(this);
  }

  @Override
  public void makeMove(HexPosition posn) {
    try {
      this.model.addPiece(this.player.getColor(), posn);
    }
    catch (IllegalStateException e) {
      this.notifyMessage(this.player.getColor(), e.getMessage());
    }
  }

  @Override
  public void passTurn() {
    this.model.pass();
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
    if (this.player.getColor().equals(color)) {
      displayToNonAI(message);
    }
  }

  @Override
  public void notifyGameOver() {
    this.myTurn = false;
    this.view.enableMoves(false);
    if (model.getWinner() == null) {
      this.view.displayMessage("Game over! Tie!\n"
              + model.getScoreColor(TeamColor.BLACK) + " to " + model.getScoreColor(TeamColor.WHITE));
    }
    else {
      this.view.displayMessage("Game over! " + model.getWinner() + " WINS!!\n"
              + model.getScoreColor(TeamColor.BLACK) + " to " + model.getScoreColor(TeamColor.WHITE));
    }

  }

  @Override
  public void notifyUpdatedGamestate() {
    this.view.renderView(this.player.getColor());
  }

  @Override
  public void notifyAdvanceTurn(TeamColor currentTurn) {
    this.myTurn = this.player.getColor().equals(currentTurn);
    this.view.enableMoves(myTurn);
    if (myTurn) {
      this.player.promptMove();
    }

  }

  private void displayToNonAI(String message) {
    if (!(player instanceof AIPlayer)) {
      this.view.displayMessage(message);
    }
  }

}
