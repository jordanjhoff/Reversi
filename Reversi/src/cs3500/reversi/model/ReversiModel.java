package cs3500.reversi.model;

import java.util.ArrayList;

public interface ReversiModel {

  /**
   * Start the game with a given size.
   * @param size the size of the board
   */
  void startGame(int size);

  /**
   * Adds a piece at a given location if the move is valid.
   * @param piece the color of the piece to add
   * @param posn the position to add the piece
   */
  void addPiece(TeamColor piece, HexPosition posn);


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
   * Returns valid positions that can be played.
   * @param color a color
   * @return a list of valid positions that color can move to.
   */
  ArrayList<HexPosition> getValidMoves(TeamColor color);


}
