package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Class to represent a game of Square Reversi. Game is played
 * on a cartesian board with a size of 4 or greater.
 **/
public class SquareReversi extends AbstractReversi {
  /**
   * Constructs a game of Square Reversi. Additionally,
   * starts the game by dealing out the board and
   * updating valid moves. Initial move goes to black.
   *
   * @param size size of the game board to be played.
   * @throws IllegalArgumentException if size is not even or less than 4
   */
  public SquareReversi(int size) {
    super(size);
    if (size % 2 != 0) {
      throw new IllegalArgumentException("size must be even");
    }
    if (size < 4) {
      throw new IllegalArgumentException("size must be at least 4");
    }
  }

  @Override
  protected void dealBoard() {
    int mid = this.size / 2;
    this.board.put(new SquarePos(mid - 1, mid - 1), TeamColor.WHITE);
    this.board.put(new SquarePos(mid, mid), TeamColor.WHITE);
    this.board.put(new SquarePos(mid - 1, mid), TeamColor.BLACK);
    this.board.put(new SquarePos(mid, mid - 1), TeamColor.BLACK);
  }

  @Override
  protected ArrayList<Position> toFlip(TeamColor color, Position posn, int[] vector) {
    ArrayList<Position> toBeFlipped = new ArrayList<>();
    Position currPosn = posn;
    while (validPosition(currPosn)) {
      currPosn = new SquarePos(currPosn.getFirstCoordinate() + vector[0],
              currPosn.getSecondCoordinate() + vector[1]);
      if (!this.board.containsKey(currPosn) || !validPosition(currPosn)) {
        return new ArrayList<>();
      }
      else if (this.board.get(currPosn).equals(color)) {
        return toBeFlipped;
      }
      else if (!this.board.get(currPosn).equals(color)) {
        toBeFlipped.add(currPosn);
      }
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
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{1,1}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{1,-1}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{-1,1}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{-1,-1}));

          if (!toBeFlipped.isEmpty()) {
            validityMap.put(currPos,toBeFlipped);
          }
        }

      }
    }

  }
}
