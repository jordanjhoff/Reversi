package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.ReversiStrategy;

/**
 * Implementation of the Player interface representing a player in a HexReversi game.
 */
public class PlayerImpl implements Player {

  //the color of the player's pieces
  private final TeamColor color;

  //the strategy used by the player to make moves
  private ReversiStrategy strategy;

  /**
   * Constructs a PlayerImpl with the specified color and strategy.
   *
   * @param color    the color of the player's pieces
   * @param strategy the strategy used by the player to make moves
   */
  public PlayerImpl(TeamColor color, ReversiStrategy strategy) {
    this.color = color;
    this.strategy = strategy;
  }

  /**
   * Plays a move on the HexReversi game board using the player's strategy.
   *
   * @param model the ReadonlyHexReversiModel representing the current state of the game
   * @return the HexPosition chosen by the player's strategy
   */
  @Override
  public HexPosition play(ReadonlyHexReversiModel model) {
    return this.strategy.choosePosn(model, this.color);
  }

  /**
   * Gets the color of the player's pieces.
   *
   * @return the TeamColor representing the player's color
   */
  @Override
  public TeamColor getColor() {
    return this.color;
  }
}
