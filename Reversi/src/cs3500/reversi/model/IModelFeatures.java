package cs3500.reversi.model;

public interface IModelFeatures {

  /**
   * Notify the listener that the board has changed.
   */
  void notifyUpdateView();

  /**
   * Notify listener that the game has begun.
   */
  void startGame();

  /**
   * Notify listener that a message needs to be displayed.
   * @param color the color player to display the message to
   * @param message the message
   */
  void notifyMessage(TeamColor color, String message);

  /**
   * Notify the listener that the game has ended.
   */
  void notifyGameOver();

  /**
   * Notify the listener that the state of the game has changed, and the board needs to be updated.
   * @param currentTurn the current turn in this new game state.
   */
  void notifyUpdatedGamestate();

  void notifyAdvanceTurn(TeamColor currentTurn);
}
