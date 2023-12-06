package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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
    ArrayList<Position> toBeFlipped = new ArrayList<>();
    Position currPosn = posn;
    while (posn.getFirstCoordinate() <= size + 1 &&
            posn.getSecondCoordinate() <= size + 1 &&
            posn.getSecondCoordinate() <= size + 1 &&
            posn.getSecondCoordinate() <= size + 1) {


    }
    return toBeFlipped;
  }

  @Override
  protected boolean validPosition(Position pos) {
    return (pos.getFirstCoordinate() < size) && (pos.getSecondCoordinate() < size);
  }

  @Override
  protected void updateValidMoves(TeamColor color) {
    LinkedHashMap<Position, ArrayList<Position>> validityMap = color == TeamColor.WHITE ?
            this.validWhiteMoves :
            this.validBlackMoves;
    validityMap.clear();

    for (int r = 0; r < size; r++) {


      for (int c = 0; c < size; c++) {
        Position currPos = new SquarePos(r, c);
        if (!this.board.containsKey(currPos)) {
          ArrayList<Position> toBeFlipped = new ArrayList<>();
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{0,1}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{1,0}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{-1,0}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{0,-1}));

          if (!toBeFlipped.isEmpty()) {
            validityMap.put(currPos,toBeFlipped);
          }
        }

      }
    }

  }
}
