package cs3500.reversi.controller;

import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.MoveFeatures;

public class HumanPlayer implements Player {
  private MoveFeatures observer;

  private TeamColor color;
  public HumanPlayer(TeamColor color) {
    this.color = color;
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
    return;
    //does nothing
  }


}
