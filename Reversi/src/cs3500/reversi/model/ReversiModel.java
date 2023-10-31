package cs3500.reversi.model;

import java.util.ArrayList;

public interface ReversiModel {

  /**
   * Adds a piece at a given location if the move is valid.
   * @param posn the position to add the piece
   */
  void addPiece(HexPosition posn);


  /**
   * Gets the color of the piece at a given position.
   * @param posn a HexPosition to check
   * @return the color of the piece at the position or null if there is no piece
   */
  TeamColor getPieceAt(HexPosition posn);

  /**
   * Returns whose turn it is.
   * @return the color of the player whose turn it is
   */
  TeamColor getCurrentTurn();

  /**
   * Skips the current players turn.
   */
  void skipTurn();

  /**
   * Determines who won the game
   * @return the winner of the game
   * @throws IllegalStateException if game is not over
   */
  TeamColor getWinner();

  /**
   * Determines if the game is over or not.
   * @return true iff the game is over
   */
  boolean isGameOver();

  /**
   * Returns the size of the board of the reversi model.
   * @return the board size
   */
  int getSize();

  /**
   * Returns valid positions that can be played for the current turn.
   * @return a list of valid positions that color can move to.
   */
  ArrayList<HexPosition> getValidMoves();


}
