package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;

public class HexReversi implements ReversiModel {
  private int radius;

  private TeamColor currentTurn;
  private boolean started;

  /**
   * A map of hexagonal positions represeting the board. The central coordinate is
   * (q,r,s) = (0,0,0). In any given direction, two coordinates change while one stays the same.
   * The corresponding coordinate increases in each direction are:
   *
   *        q,-r,+s    +q,-r,s
   *              \   /
   *               \ /
   *     -q,r,+s ---*--- +q,r,-s
   *               / \
   *              /   \
   *       -q,+r,s     q,+r,-s
   *
   *    q
   *     /    \
   *    |  *  | r
   *    \    /
   *   s
   */
  private HashMap<HexPosition, TeamColor> board;


  public HexReversi() {
    radius = 5;
    board = new HashMap<>();
    started = false;
  }

  @Override
  public void startGame(int radius) {
    if (radius < 2) {
      throw new IllegalArgumentException("radius must be greater than 2!");
    }
    if (started) {
      throw new IllegalStateException("Game already started");
    }

    started = true;

    this.radius = radius;
    dealBoard();
    currentTurn = TeamColor.BLACK;
  }

  void notStarted() {
    if (!started) {
      throw new IllegalStateException("Game has not been started yet");
    }
  }

  private void dealBoard() {
    int[][] circle = {{1, -1}, {0, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 0}};
    TeamColor color = TeamColor.BLACK;
    int q;
    int r;
    int s;
    for (int i = 0; i < 6; i++) {
      q = circle[i][1];
      r = circle[i][0];
      s = -q - r;
      board.put(new HexPosition(q, r, s), color);
      color = color.cycle();
    }
  }



  private boolean validMove(TeamColor color, HexPosition posn) {
    return !toFlipQ(color, posn).isEmpty() || !toFlipR(color, posn).isEmpty() ||
            !toFlipS(color, posn).isEmpty();
  }

  /**
   * Returns an ArrayList of all the pieces to be flipped on a single Q file
   * @param color the color to be placed
   * @param posn the position to place the piece
   * @return
   */
  private ArrayList<HexPosition> toFlipQ(TeamColor color, HexPosition posn) {
    int currQ = posn.getQPosition();

    ArrayList<HexPosition> toBeFlippedDir1 = new ArrayList<>();
    int currRDir1 = posn.getRPosition() + 1;
    int currSDir1 = posn.getSPosition() - 1;
    while (Math.abs(currRDir1) <= radius && Math.abs(currSDir1) <= radius) {
      HexPosition currPosnDir1 = new HexPosition(currQ, currRDir1, currSDir1);
      if (!this.board.containsKey(currPosnDir1)) {
        toBeFlippedDir1.clear();
        break;
      }
      else if (this.board.get(currPosnDir1).equals(color)) {
        break;
      }
      else if (!this.board.get(currPosnDir1).equals(color)) {
        toBeFlippedDir1.add(currPosnDir1);
      }
      currRDir1 = currRDir1 + 1;
      currSDir1 = currSDir1 - 1;
    }

    ArrayList<HexPosition> toBeFlippedDir2 = new ArrayList<>();
    int currRDir2 = posn.getRPosition() - 1;
    int currSDir2 = posn.getSPosition() + 1;
    while (Math.abs(currRDir2) <= radius && Math.abs(currSDir2) <= radius) {
      HexPosition currPosnDir2 = new HexPosition(currQ, currRDir2, currSDir2);
      if (!this.board.containsKey(currPosnDir2)) {
        toBeFlippedDir2.clear();
        break;
      }
      else if (this.board.get(currPosnDir2).equals(color)) {
        break;
      }
      else if (!this.board.get(currPosnDir2).equals(color)) {
        toBeFlippedDir2.add(currPosnDir2);
      }
      currRDir2 = currRDir1 - 1;
      currSDir2 = currSDir1 + 1;
    }
    toBeFlippedDir1.addAll(toBeFlippedDir2);
    return toBeFlippedDir1;
  }

  private ArrayList<HexPosition> toFlipR(TeamColor color, HexPosition posn) {
    int currR = posn.getRPosition();

    ArrayList<HexPosition> toBeFlippedDir1 = new ArrayList<>();
    int currQDir1 = posn.getRPosition() + 1;
    int currSDir1 = posn.getSPosition() - 1;
    while (Math.abs(currQDir1) <= radius && Math.abs(currSDir1) <= radius) {
      HexPosition currPosnDir1 = new HexPosition(currQDir1, currR, currSDir1);
      if (!this.board.containsKey(currPosnDir1)) {
        toBeFlippedDir1.clear();
        break;
      }
      else if (this.board.get(currPosnDir1).equals(color)) {
        break;
      }
      else if (!this.board.get(currPosnDir1).equals(color)) {
        toBeFlippedDir1.add(currPosnDir1);
      }
      currQDir1 = currQDir1 + 1;
      currSDir1 = currSDir1 - 1;
    }

    ArrayList<HexPosition> toBeFlippedDir2 = new ArrayList<>();
    int currQDir2 = posn.getRPosition() - 1;
    int currSDir2 = posn.getSPosition() + 1;
    while (Math.abs(currQDir2) <= radius && Math.abs(currSDir2) <= radius) {
      HexPosition currPosnDir2 = new HexPosition(currQDir2, currR, currSDir2);
      if (!this.board.containsKey(currPosnDir2)) {
        toBeFlippedDir2.clear();
        break;
      }
      else if (this.board.get(currPosnDir2).equals(color)) {
        break;
      }
      else if (!this.board.get(currPosnDir2).equals(color)) {
        toBeFlippedDir2.add(currPosnDir2);
      }
      currQDir2 = currQDir1 - 1;
      currSDir2 = currSDir1 + 1;
    }
    toBeFlippedDir1.addAll(toBeFlippedDir2);
    return toBeFlippedDir1;
  }

  private ArrayList<HexPosition> toFlipS(TeamColor color, HexPosition posn) {
    int currS = posn.getRPosition();

    ArrayList<HexPosition> toBeFlippedDir1 = new ArrayList<>();
    int currQDir1 = posn.getRPosition() + 1;
    int currRDir1 = posn.getSPosition() - 1;
    while (Math.abs(currQDir1) <= radius && Math.abs(currRDir1) <= radius) {
      HexPosition currPosnDir1 = new HexPosition(currQDir1, currRDir1, currS);
      if (!this.board.containsKey(currPosnDir1)) {
        toBeFlippedDir1.clear();
        break;
      }
      else if (this.board.get(currPosnDir1).equals(color)) {
        break;
      }
      else if (!this.board.get(currPosnDir1).equals(color)) {
        toBeFlippedDir1.add(currPosnDir1);
      }
      currQDir1 = currQDir1 + 1;
      currRDir1 = currRDir1 - 1;
    }

    ArrayList<HexPosition> toBeFlippedDir2 = new ArrayList<>();
    int currQDir2 = posn.getRPosition() - 1;
    int currRDir2 = posn.getSPosition() + 1;
    while (Math.abs(currQDir2) <= radius && Math.abs(currRDir2) <= radius) {
      HexPosition currPosnDir2 = new HexPosition(currQDir2, currRDir2, currS);
      if (!this.board.containsKey(currPosnDir2)) {
        toBeFlippedDir2.clear();
        break;
      }
      else if (this.board.get(currPosnDir2).equals(color)) {
        break;
      }
      else if (!this.board.get(currPosnDir2).equals(color)) {
        toBeFlippedDir2.add(currPosnDir2);
      }
      currQDir2 = currQDir1 - 1;
      currRDir2 = currRDir1 + 1;
    }
    toBeFlippedDir1.addAll(toBeFlippedDir2);
    return toBeFlippedDir1;
  }


  private boolean validPosition(HexPosition pos) {
    notStarted();
    if ((Math.abs(pos.getQPosition()) <= this.radius)
            && (Math.abs(pos.getRPosition()) <= this.radius)
            && (pos.getSPosition() == -pos.getQPosition() - pos.getRPosition())) {
      return true;
    }
    return false;
  }

  private void setColor(TeamColor color, ArrayList<HexPosition> posns) {
    for (HexPosition posn : posns) {
      this.board.put(posn, color);
    }
  }


  @Override
  public void addPiece(TeamColor piece, HexPosition posn) {
    notStarted();
    if (!validPosition(posn)) {
      throw new IllegalArgumentException("Position out of bounds");
    }
    else if (!validMove(piece, posn)) {
      throw new IllegalStateException("Invalid move");
    }
    this.board.put(posn, piece);
    setColor(piece, toFlipQ(piece, posn));
    setColor(piece, toFlipR(piece, posn));
    setColor(piece, toFlipS(piece, posn));

    this.currentTurn = this.currentTurn.cycle();
  }

  @Override
  public TeamColor getPieceAt(HexPosition posn) {
    notStarted();
    return board.get(new HexPosition(posn));
  }

  @Override
  public TeamColor getCurrentTurn() {
    notStarted();
    return currentTurn;
  }

  @Override
  public boolean isGameOver() {
    notStarted();
    return false;
  }

  @Override
  public int getRadius() {
    notStarted();
    return radius;
  }
}
