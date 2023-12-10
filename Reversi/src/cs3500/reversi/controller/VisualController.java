package cs3500.reversi.controller;

import cs3500.reversi.model.Position;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.IReversiView;

/**
 * A controller for HexReversi to be used in tandem with the GUI.
 * Has a player (which can either be a human/ai) as well as the model
 * of the game and the view for the given player to be controlled.
 */
public class VisualController implements ReversiController {
  private final ReversiModel model; // the model the game is played on
  private final IReversiView view; // the view of the player
  private final Player player; // the player to be controlled

  private boolean myTurn; //is this players turn or not

  /**
   * Constructs a VisualController due the model of the game as well as
   * the Player to be controlled and their respective view.
   *
   * @param model of the game
   * @param view of the player
   * @param player the player themselves
   */
  public VisualController(ReversiModel model, IReversiView view, Player player) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.view.addFeatureListener(this);
    this.model.addFeatureObserver(this);
    this.player.addFeatureListener(this);
  }

  @Override
  public void notifyMakeMove(Position posn) {
    try {
      this.model.addPiece(this.player.getColor(), posn);
    }
    catch (IllegalStateException e) {
      this.notifyMessage(this.player.getColor(), e.getMessage());
    }
  }

  @Override
  public void notifyPassTurn() {
    this.model.pass();
  }

  @Override
  public void notifyStartGame(TeamColor startingTurn) {
    this.view.setVisible(true);
    if (this.player.getColor().equals(startingTurn)) {
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
              + model.getScoreColor(TeamColor.BLACK) +
              " to " + model.getScoreColor(TeamColor.WHITE));
    }
    else {
      this.view.displayMessage("Game over! " + model.getWinner() + " WINS!!\n"
              + model.getScoreColor(TeamColor.BLACK) +
              " to " + model.getScoreColor(TeamColor.WHITE));
    }

  }

  @Override
  public void notifyUpdatedGameState() {
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

  /**
   * Only print out if the player is not an AI.
   *
   * @param message message to be printed
   */
  private void displayToNonAI(String message) {
    if (!(player instanceof AIPlayer)) {
      this.view.displayMessage(message);
    }
  }

}
