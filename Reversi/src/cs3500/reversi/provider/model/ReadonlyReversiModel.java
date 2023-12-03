package cs3500.reversi.provider.model;

import java.util.List;


/**
 * An interface that represents the non-mutable version of Reversi.
 */
public interface ReadonlyReversiModel {
  /**
   * Retrieves the board to be played on.
   * @return the 2D List of ICells that represent the board
   */
  List<List<ICell>> getBoard();

  /**
   * Determines if the game is over.
   * @return true if all tiles have been played in the game.
   *     false there is still an empty cell or a playable move in the game.
   */
  boolean isGameOver();

  /**
   * Is the game over.
   * @return an int representing the diameter of the board.
   */
  int returnSize();

  /**
   * Returns the cell type of a cell.
   * @param q coordinate of cell
   * @param r coordinate of cell
   * @return cell type of cell with provided coordinates.
   * @throws IllegalArgumentException if coordinates are invalid
   */
  CellType returnCellContents(int q, int r) throws IllegalArgumentException;

  /**
   * Is this move legal.
   * @param q coordinate of cell
   * @param r coordinate of cell
   * @return a boolean representing whether a move is legal given the current player.
   */
  boolean isLegalMove(int q, int r);

  /**
   * Get the score of a player.
   * @param color of pieces to count
   * @return an int representing the number of cells marked with the given color.
   */
  int getScore(Color color);

  /**
   * Does the current player have any legal moves.
   * @return a boolean representing whether the current player has any legal moves.
   */
  boolean currPlayerLegalMoves();

  /**
   * Checks all the possible directional options a cell can have to see if the cell has neighbors.
   * @param cell an empty cell that you want to play a move on
   * @return a 2D List of all valid neighboring cells in the following format:
   *     [cell, neighbor1], [cell, neighbor2], etc.
   *     A neighboring cell is considered "valid" if it is the opposite color of the current player.
   */
  List<List<ICell>> getNeighbors(ICell cell);

  /**
   * Creates a list of all possible moves from the neighboring cells of the current cell.
   * @param neighbors a 2D list of all the neighboring cells
   */
  void extendNeighbors(List<List<ICell>> neighbors);

  /**
   * Retrieves the chosen cell with the given column and row coordinates.
   * @param column the q-value of the cell you want to get.
   * @param row the r-value of the cell you want to get.
   * @return the cell with the given coordinates, or null if it doesn't exist.
   */
  ICell getCellAt(int column, int row);


  /**
   * Get the edge cells of a cell.
   * @param cell of which to return bordering cells.
   * @return a list of all the ICells that border the given cell.
   */
  List<ICell> getEdgeCells(ICell cell);

  /**
   * What is the color of the current player.
   * @return the current player's color.
   */
  Color getCurrentColor();
}
