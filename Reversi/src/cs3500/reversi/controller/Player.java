package cs3500.reversi.controller;

import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.MoveFeatures;

/**
 * Represents a player in a HexReversi game. A Player can interact with a model and decide moves.
 */
public interface Player {

  /**
   * Registers an observer to this class.
   *
   * @param features the observer to listen in
   */
  void addFeatureListener(MoveFeatures features);

  /**
   * What color is the player.
   * @return the color of the player
   */
  public TeamColor getColor();

  /**
   * Notifies the observer of a desired move. Does nothing if player is a human.
   */
  void promptMove();

}
