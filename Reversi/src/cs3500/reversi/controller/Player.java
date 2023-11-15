package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.TeamColor;

/**
 * Represents a player in a HexReversi game. A Player can interact with a model and decide moves.
 */
public interface Player {

  /**
   * Plays a move on the HexReversi game board using the player's strategy.
   *
   * @param model the ReadonlyHexReversiModel representing the current state of the game
   * @return the HexPosition chosen by the player's strategy
   */
  HexPosition play(ReadonlyHexReversiModel model);

  /**
   * Gets the color of the player's pieces.
   *
   * @return the TeamColor representing the player's color
   */
  TeamColor getColor();

}
