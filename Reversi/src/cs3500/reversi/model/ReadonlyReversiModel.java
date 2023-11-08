package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;

public interface ReadonlyReversiModel {


  /**
   * Gets the color of the piece at a given position.
   * @param posn a HexPosition to check
   * @return the color of the piece at the position or null if there is no piece
   */
  TeamColor getPieceAt(HexPosition posn);

  /**
   * Returns whose turn it is.
   * @return the color of the player whose turn it is
   * @throws IllegalStateException if game is over
   */
  TeamColor getCurrentTurn();

  /**
   * Determines who won the game.
   * @return the winner of the game, and null if the game is tied.
   * @throws IllegalStateException if game is not over
   */
  TeamColor getWinner();

  /**
   * Determines if the game is over or not.
   * @return true iff the game is over.
   */
  boolean isGameOver();

  /**
   * Returns the size of the board of the reversi model.
   * @return the board size
   */
  int getSize();

  /**
   * Returns valid positions that can be played for the current turn in top-leftmost order.
   * @return a list of valid positions that color can move to.
   */
  ArrayList<HexPosition> getValidMoves();


  /**
   * Returns a copy of the board.
   * @return a copy of the board
   */
  HashMap<HexPosition, TeamColor> getBoard();

  /**
   * Is this position a valid move?
   * @return whether input position is a valid move
   */
  boolean isMoveValid(HexPosition pos);

  /**
   * What is white's score?
   * @return natural number of the count of whites pieces
   */
  int getWhiteScore();

  /**
   * What is black's score?
   * @return natural number of the count of black pieces
   */
  int getBlackScore();

  /**
   * Returns the number of pieces a move will flip for the current player at a given
   * position.
   * @param posn the position to
   * @return the number of pieces that will be flipped if a piece is placed at posn
   * @throws IllegalArgumentException if the position is of bounds
   */
  public int flipCount(HexPosition posn);

}
