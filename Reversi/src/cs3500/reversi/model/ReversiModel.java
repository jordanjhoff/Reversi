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
   */
  void addPiece(TeamColor color, HexPosition posn);

  /**
   * Skips the current players turn.
   * @throws IllegalStateException if game is over
   */
  void pass();

  void addFeatureListener(IModelFeatures features);

  void startGame();

}
