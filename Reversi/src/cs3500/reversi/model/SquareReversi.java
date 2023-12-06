package cs3500.reversi.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SquareReversi extends HexReversi {
  /**
   * Constructs a game of HexReversi. Additionally,
   * starts the game by dealing out the board and
   * updating valid moves. Initial move goes to black.
   *
   * @param size size of the game board to be played.
   */
  public SquareReversi(int size) {
    super(size);
    if (size % 2 != 0) {
      throw new IllegalArgumentException("Radius must be even");
    }
  }

  @Override
  protected void dealBoard() {
    int mid = this.size / 2;
    this.board.put(new SquarePos(mid + 1, mid + 1), TeamColor.WHITE);
    this.board.put(new SquarePos(mid, mid), TeamColor.WHITE);
    this.board.put(new SquarePos(mid + 1, mid), TeamColor.BLACK);
    this.board.put(new SquarePos(mid, mid + 1), TeamColor.BLACK);
  }

  @Override
  protected ArrayList<Position> toFlip(TeamColor color, Position posn, int[] vector) {
    return null;
  }

  @Override
  protected boolean validPosition(Position pos) {

  }

  @Override
  protected void updateValidMoves(TeamColor color) {

  }
}
