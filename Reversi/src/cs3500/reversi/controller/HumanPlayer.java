package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.IPlayerFeatures;

public class HumanPlayer implements Player {
  private IPlayerFeatures listener;

  private TeamColor color;
  public HumanPlayer(TeamColor color) {
    this.color = color;
  }

  @Override
  public void addFeatureListener(IPlayerFeatures features) {
    listener = features;
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
