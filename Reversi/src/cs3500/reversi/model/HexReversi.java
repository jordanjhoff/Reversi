package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A model to play a generic version of HexReversi.
 * Game is automatically started by the constructor and
 * has various fields about size of the game, the current turn,
 * and available current moves, as well as the board itself.
 */
public class HexReversi implements ReversiModel {
  private final int radius;
  private TeamColor currentTurn;

  /**
   * A map of hexagonal positions represeting the board. The central coordinate is
   * (q,r,s) = (0,0,0). In any given direction, two coordinates change while one stays the same.
   * The corresponding coordinate increases in each direction are:
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

  /**
   * Represents valid moves, where the key is the position, and the value is the list of
   * HexPositions that need to be updated if that move is played.
   */
  private HashMap<HexPosition, ArrayList<HexPosition>> validBlackMoves;
  private HashMap<HexPosition, ArrayList<HexPosition>> validWhiteMoves;


  /**
   * Constructs a game of HexReversi. Additionally,
   * starts the game by dealing out the board and
   * updating valid moves. Initial move goes to black.
   *
   * @param radius size of the game board to be played.
   */
  public HexReversi(int radius) {
    this.board = new HashMap<>();
    this.validWhiteMoves = new HashMap<>();
    this.validBlackMoves = new HashMap<>();
    if (radius < 2) {
      throw new IllegalArgumentException("radius must be greater than 2!");
    }
    this.radius = radius;
    dealBoard();
    this.currentTurn = TeamColor.BLACK;
    updateValidMoves();
  }

  /**
   * Returns valid positions that can be played for the current player
   * @return a list of valid positions that color can move to.
   */
  @Override
  public ArrayList<HexPosition> getValidMoves() {

    HashMap<HexPosition, ArrayList<HexPosition>> validityMap = this.currentTurn.equals(TeamColor.WHITE) ? this.validWhiteMoves :
            this.validBlackMoves;
    return new ArrayList<>(validityMap.keySet());
  }

  /**
   * Method to initialize the gameboard, putting the original circle
   * around the origin of cycling colors.
   */
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

  /**
   * Returns an ArrayList of all the pieces to be flipped on a single Q file
   * @param color the color to be placed
   * @param posn the position to place the piece
   * @return a list of positions that need to be flipped
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
      currRDir2 = currRDir2 - 1;
      currSDir2 = currSDir2 + 1;
    }
    toBeFlippedDir1.addAll(toBeFlippedDir2);
    return toBeFlippedDir1;
  }

  /**
   * Returns an ArrayList of all the pieces to be flipped on a single R file
   * @param color the color to be placed
   * @param posn the position to place the piece
   * @return a list of positions that need to be flipped
   */
  private ArrayList<HexPosition> toFlipR(TeamColor color, HexPosition posn) {
    int currR = posn.getRPosition();

    ArrayList<HexPosition> toBeFlippedDir1 = new ArrayList<>();
    int currQDir1 = posn.getQPosition() + 1;
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
    int currQDir2 = posn.getQPosition() - 1;
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
      currQDir2 = currQDir2 - 1;
      currSDir2 = currSDir2 + 1;
    }
    toBeFlippedDir1.addAll(toBeFlippedDir2);
    return toBeFlippedDir1;
  }

  /**
   * Returns an ArrayList of all the pieces to be flipped on a single S file
   * @param color the color to be placed
   * @param posn the position to place the piece
   * @return a list of positions that need to be flipped
   */
  private ArrayList<HexPosition> toFlipS(TeamColor color, HexPosition posn) {
    int currS = posn.getSPosition();

    ArrayList<HexPosition> toBeFlippedDir1 = new ArrayList<>();
    int currQDir1 = posn.getQPosition() + 1;
    int currRDir1 = posn.getRPosition() - 1;
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
    int currQDir2 = posn.getQPosition() - 1;
    int currRDir2 = posn.getRPosition() + 1;
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
      currQDir2 = currQDir2 - 1;
      currRDir2 = currRDir2 + 1;
    }
    toBeFlippedDir1.addAll(toBeFlippedDir2);
    return toBeFlippedDir1;
  }

  /**
   * Determines whether the position is legal on the board.
   *
   * @param pos position to validate
   * @return true if valid position
   */
  private boolean validPosition(HexPosition pos) {
    return (Math.abs(pos.getQPosition()) <= this.radius)
            && (Math.abs(pos.getRPosition()) <= this.radius)
            && (pos.getSPosition() == -pos.getQPosition() - pos.getRPosition());
  }

  /**
   * Sets the color of a given list of positions.
   *
   * @param color the color to set at each position
   * @param posns the list of positions to set to color
   */
  private void setColor(TeamColor color, ArrayList<HexPosition> posns) {
    for (HexPosition posn : posns) {
      this.board.put(posn, color);
    }
  }

  /**
   * Updates the validity maps to contain all available moves.
   */
  private void updateValidMoves() {
    for (int teams = 0; teams < 2; teams++) {
      TeamColor color = teams == 0 ? TeamColor.WHITE : TeamColor.BLACK;
      HashMap<HexPosition, ArrayList<HexPosition>> validityMap = teams == 0 ? this.validWhiteMoves :
              this.validBlackMoves;
      validityMap.clear();

      for (int r = -this.radius; r <= this.radius; r++) {
        int rMin = Math.max(-this.radius, -r - this.radius);
        int rMax = Math.min(this.radius, -r + this.radius);

        for (int q = rMin; q <= rMax; q++) {
          int s = -r - q;
          HexPosition currPos = new HexPosition(q,r,s);
          if (!this.board.containsKey(currPos)) {

            ArrayList<HexPosition> toBeFlipped = new ArrayList<>();
            toBeFlipped.addAll(toFlipQ(color, currPos));
            toBeFlipped.addAll(toFlipR(color, currPos));
            toBeFlipped.addAll(toFlipS(color, currPos));


            if (!toBeFlipped.isEmpty()) {
              validityMap.put(currPos,toBeFlipped);
            }
          }
        }
      }
    }

  }


  @Override
  public void addPiece(HexPosition posn) {

    HashMap<HexPosition, ArrayList<HexPosition>> validityMap = currentTurn.equals(TeamColor.WHITE) ?
            this.validWhiteMoves : this.validBlackMoves;
    if (!validPosition(posn)) {
      throw new IllegalArgumentException("Position out of bounds");
    }
    else if (!validityMap.containsKey(posn)) {
      throw new IllegalStateException("Invalid move");
    }


    this.board.put(posn, currentTurn);
    setColor(currentTurn, validityMap.get(posn));

    this.currentTurn = this.currentTurn.cycle();
    updateValidMoves();
  }

  @Override
  public TeamColor getPieceAt(HexPosition posn) {
    if (!validPosition(posn)) {
      throw new IllegalArgumentException("Position out of bounds");
    }
    else if (!board.containsKey(posn)) {
      return null;
    }

    return this.board.get(new HexPosition(posn));
  }

  @Override
  public TeamColor getCurrentTurn() {
    return this.currentTurn;
  }

  @Override
  public void skipTurn() {
    this.currentTurn = this.currentTurn.cycle();
  }

  @Override
  public TeamColor getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over!");
    }
    else {
      HashMap<TeamColor, Integer> freqMap = new HashMap<>();
      TeamColor mostFreq = null;
      int mostFreqCount = -1;
      for (TeamColor color : board.values()) {
        Integer count = freqMap.get(color);
        freqMap.put(color, count = (count == null ? 1 : count+1));
        if (count > mostFreqCount) {
          mostFreq = color;
          mostFreqCount = count;
        }
      }
      return mostFreq;
    }
  }

  @Override
  public boolean isGameOver() {
    return this.validBlackMoves.isEmpty() && this.validWhiteMoves.isEmpty();
  }

  @Override
  public int getSize() {
    return this.radius;
  }
}
