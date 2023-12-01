package cs3500.reversi.model;

/**
 * Interface for the representing a generic game of Hexagonal Reversi,
 * able to interface with both the view and controller to provide a
 * flawless experience for the user.
 */
public interface ReversiModel extends ReadonlyReversiModel {

  /**
   * Adds a piece at a given location if the move is valid.
   * @param posn the position to add the piece
   * @throws IllegalStateException if game is over, or if its not the right color
   * @throws IllegalStateException if game is not started
   */
  void addPiece(TeamColor color, HexPosition posn);

  /**
   * Skips the current players turn.
   * @throws IllegalStateException if game is over
   * @throws IllegalStateException if game is not started
   */
  void pass();

  /**
   * Register the given features observer to this model.
   * @param features the given feature to be added
   * @throws IllegalStateException if game has been already started
   */
  void addFeatureObserver(ModelFeatures features);

  /**
   * Starts the game. Implemented to guarantee that
   * there are two players before the start of the game.
   * @throws IllegalStateException if there are not 2 features observers
   * @throws IllegalStateException if game has been already started
   */
  void startGame();

}
