package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.Position;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.view.MoveFeatures;

/**
 * Class to represent an AI player of hexreversi. Has a color and observer which
 * can be registered. Additionally, contains a strategy by which the moves are chosen.
 */
public class AIPlayer implements Player {
  //the observer that needs to know when a move is made (the controller)
  private MoveFeatures observer;
  //the strategy to determine the ai moves
  private ReversiStrategy strategy;
  //the readonlymodel to base the strategies off of
  private ReadonlyReversiModel model;
  //the color of this player
  private TeamColor color;

  /**
   * Constructs an AI player due to their team color as well as strategy and board of the game.
   *
   * @param color team color of the ai
   * @param strategy the stategy by which ai chooses their moves
   * @param model the model of the game played on
   */
  public AIPlayer(TeamColor color, ReversiStrategy strategy, ReadonlyReversiModel model) {
    this.color = color;
    this.strategy = strategy;
    this.model = model;
  }

  @Override
  public void addFeatureListener(MoveFeatures features) {
    observer = features;
  }

  @Override
  public TeamColor getColor() {
    return color;
  }

  @Override
  public void promptMove() {
    Position chosenMove = this.strategy.choosePosn(model, this.color);
    if (!model.isGameOver()) {
      if (chosenMove != null) {
        observer.notifyMakeMove(chosenMove);
      }
      else {
        observer.notifyPassTurn();
      }
    }
  }
}
