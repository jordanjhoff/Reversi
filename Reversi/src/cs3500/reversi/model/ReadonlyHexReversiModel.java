package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A readonly adapter for HexReversiModel providing access to model information without allowing
 * modifications to the adaptee ReversiModel.
 */
public class ReadonlyHexReversiModel implements ReadonlyReversiModel {
  private final ReversiModel adaptee;

  /**
   * Constructs a ReadonlyHexReversiModel with the specified adaptee.
   *
   * @param adaptee the ReversiModel instance to be adapted
   * @throws IllegalArgumentException if adaptee is null
   */
  public ReadonlyHexReversiModel(ReversiModel adaptee) {
    if (adaptee == null) {
      throw new IllegalArgumentException("Adaptee cannot be null");
    }
    this.adaptee = adaptee;
  }

  @Override
  public TeamColor getPieceAt(HexPosition posn) {
    return this.adaptee.getPieceAt(posn);
  }

  @Override
  public TeamColor getCurrentTurn() {
    return this.adaptee.getCurrentTurn();
  }

  @Override
  public TeamColor getWinner() {
    return this.adaptee.getWinner();
  }

  @Override
  public boolean isGameOver() {
    return this.adaptee.isGameOver();
  }

  @Override
  public int getSize() {
    return this.adaptee.getSize();
  }

  @Override
  public ArrayList<HexPosition> getValidMoves() {
    return this.adaptee.getValidMoves();
  }

  @Override
  public HashMap<HexPosition, TeamColor> getBoard() {
    return this.adaptee.getBoard();
  }

  @Override
  public int getWhiteScore() {
    return this.adaptee.getWhiteScore();
  }

  @Override
  public int getBlackScore() {
    return this.adaptee.getBlackScore();
  }

  @Override
  public int flipCount(HexPosition posn) {
    return this.adaptee.flipCount(posn);
  }
}
