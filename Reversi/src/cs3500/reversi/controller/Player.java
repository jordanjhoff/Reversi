package cs3500.reversi.controller;

import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.MoveFeatures;

/**
 * Represents a player in a HexReversi game. A Player can interact with a model and decide moves.
 */
public interface Player {

  void addFeatureListener(MoveFeatures features);

  public TeamColor getColor();

  void promptMove();

}
