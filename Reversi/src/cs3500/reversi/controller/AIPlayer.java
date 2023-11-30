package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.view.MoveFeatures;

public class AIPlayer implements Player {
  private MoveFeatures observer;
  private ReversiStrategy strategy;

  private ReadonlyReversiModel model;

  private TeamColor color;
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
    HexPosition chosenMove = this.strategy.choosePosn(model, this.color);
    if (chosenMove != null) {
      observer.notifyMakeMove(chosenMove);
    }
    else {
      observer.notifyPassTurn();
    }
  }
}
