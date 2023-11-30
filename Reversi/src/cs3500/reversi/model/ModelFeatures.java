package cs3500.reversi.model;

/**
 * This observer interface outlines the features for the Reversi game model.
 * This interface includes methods for notifying various state changes within the game.
 */
public interface ModelFeatures {

  /**
   * Notify the observer that the board has changed.
   */
  void notifyUpdateView();

  /**
   * Notify observer that the game has begun.
   * @param startingPlayer the player color who goes first.
   */
  void notifyStartGame(TeamColor startingPlayer);

  /**
   * Notify observer that a message needs to be displayed.
   * @param color the color player to display the message to
   * @param message the message
   */
  void notifyMessage(TeamColor color, String message);

  /**
   * Notify the observer that the game has ended.
   */
  void notifyGameOver();

  /**
   * Notify the observer that the state of the game has changed, and the board needs to be updated.
   */
  void notifyUpdatedGamestate();

  /**
   * Notify the observer that a player made a move, and the turn advanced.
   * @param currentTurn the updated color of the player who's turn it is
   */
  void notifyAdvanceTurn(TeamColor currentTurn);
}
