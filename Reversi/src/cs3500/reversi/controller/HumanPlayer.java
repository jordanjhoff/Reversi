package cs3500.reversi.controller;

import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.MoveFeatures;

/**
 * Class to represent a Human player of hexreversi. Has a color and observer which
 * can be registered.
 */
public class HumanPlayer implements Player {
  //this player's color
  private TeamColor color;

  /**
   * Constructs a human player due to their team color.
   * @param color Determines what color this player is.
   */
  public HumanPlayer(TeamColor color) {
    this.color = color;
  }

  @Override
  public void addFeatureListener(MoveFeatures features) {
    return;
    //does nothing
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
