package cs3500.reversi.provider.model;

import cs3500.reversi.provider.view.ModelFeatures;

/**
 * An interface representing a Reversi game.
 */
public interface IReversi extends cs3500.reversi.provider.model.ReadonlyReversiModel {
  /**
   * Plays a valid move of the current player.
   * @param q the column coordinate of a cell you want to play
   * @param r the row coordinate of a cell you want to play
   */
  void playMove(int q, int r);

  void passMove();

  /**
   * Determines if the game is over.
   * @return true if all tiles have been played in the game.
   *     false there is still an empty cell or a playable move in the game.
   */
  boolean isGameOver();

  void startGame();

  void addFeatures(ModelFeatures features);
}