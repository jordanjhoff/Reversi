package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * A model to play a generic version of HexReversi.
 * Game is automatically started by the constructor and
 * has various fields about size of the game, the current turn,
 * and available current moves, as well as the board itself.
 * INVARIANT: no coordinate will ever be greater than the radius (checked by validatePositions).
 */
public class HexReversi extends AbstractReversi {

  // The board works as such: the central coordinate is
  // (q,r,s) = (0,0,0). In any given direction, two coordinates change while one stays the same.
  // The corresponding coordinate increases in each direction are:
  //        q,-r,+s    +q,-r,s
  //              \   /
  //               \ /
  //     -q,r,+s ---*--- +q,r,-s
  //               / \
  //              /   \
  //       -q,+r,s     q,+r,-s
  //    q
  //     /    \
  //    |  *  | r
  //    \    /
  //   s

  /**
   * Constructs a game of HexReversi. Additionally,
   * starts the game by dealing out the board and
   * updating valid moves. Initial move goes to black.
   *
   * @param size size of the game board to be played.
   */
  public HexReversi(int size) {
    super(size);
  }

  @Override
  protected void dealBoard() {
    int[][] circle = {{1, -1}, {0, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 0}};
    TeamColor color = TeamColor.BLACK;
    int q;
    int r;
    int s;
    for (int i = 0; i < 6; i++) {
      q = circle[i][1];
      r = circle[i][0];
      s = -q - r;
      board.put(new HexPosition(q, r), color);
      color = color.cycle();
    }
  }

  @Override
  protected ArrayList<Position> toFlip(TeamColor color, Position posn, int[] vector) {
    if (vector.length != 3) {
      throw new IllegalArgumentException("Invalid position vector");
    }
    ArrayList<Position> toBeFlipped = new ArrayList<>();
    Position currPosn = posn;
    while (Math.abs(posn.getFirstCoordinate()) <= size + 1 &&
            Math.abs(posn.getSecondCoordinate()) <= size + 1 &&
            Math.abs(getSPosition(posn)) <= size + 1) {
      currPosn = new HexPosition(currPosn.getFirstCoordinate() + vector[0],
              currPosn.getSecondCoordinate() + vector[1]);
      if (!this.board.containsKey(currPosn) || !validPosition(currPosn)) {
        toBeFlipped.clear();
        break;
      }
      else if (this.board.get(currPosn).equals(color)) {
        break;
      }
      else if (!this.board.get(currPosn).equals(color)) {
        toBeFlipped.add(currPosn);
      }
    }

    return toBeFlipped;
  }

  protected int getSPosition(Position pos) {
    return -pos.getFirstCoordinate() - pos.getSecondCoordinate();
  }

  @Override
  protected boolean validPosition(Position pos) {
    return (Math.abs(pos.getFirstCoordinate()) <= this.size)
            && (Math.abs(pos.getSecondCoordinate()) <= this.size);
  }

  @Override
  protected void updateValidMoves(TeamColor color) {
    LinkedHashMap<Position, ArrayList<Position>> validityMap = color == TeamColor.WHITE ?
            this.validWhiteMoves :
            this.validBlackMoves;
    validityMap.clear();

    for (int r = -this.size; r <= this.size; r++) {
      int rMin = Math.max(-this.size, -r - this.size);
      int rMax = Math.min(this.size, -r + this.size);

      for (int q = rMin; q <= rMax; q++) {
        int s = -r - q;
        Position currPos = new HexPosition(q, r);
        if (!this.board.containsKey(currPos)) {

          ArrayList<Position> toBeFlipped = new ArrayList<>();
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{0, 1, -1}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{0, -1, 1}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{1, 0, -1}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{-1, 0, 1}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{1, -1, 0}));
          toBeFlipped.addAll(toFlip(color, currPos, new int[]{-1, 1, 0}));

          if (!toBeFlipped.isEmpty()) {
            validityMap.put(currPos, toBeFlipped);
          }
        }
      }
    }
  }
}
