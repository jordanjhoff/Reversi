package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * A model to play a generic version of HexReversi.
 * Game is automatically started by the constructor and
 * has various fields about size of the game, the current turn,
 * and available current moves, as well as the board itself.
 * INVARIANT: no coordinate will ever be greater than the radius (checked by validatePositions).
 */
public class HexReversi implements ReversiModel {
  private final int radius;
  private TeamColor currentTurn;
  private boolean lastPass;

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
  private LinkedHashMap<HexPosition, ArrayList<HexPosition>> validBlackMoves;
  private LinkedHashMap<HexPosition, ArrayList<HexPosition>> validWhiteMoves;


  /**
   * Constructs a game of HexReversi. Additionally,
   * starts the game by dealing out the board and
   * updating valid moves. Initial move goes to black.
   *
   * @param radius size of the game board to be played.
   */
  public HexReversi(int radius) {
    this.board = new HashMap<>();
    this.validWhiteMoves = new LinkedHashMap<>();
    this.validBlackMoves = new LinkedHashMap<>();
    if (radius < 2) {
      throw new IllegalArgumentException("radius must be greater than 2!");
    }
    this.radius = radius;
    dealBoard();
    this.currentTurn = TeamColor.BLACK;
    updateValidMoves();
    lastPass = false;
  }

  /**
   * Returns valid positions that can be played for the current player in top-leftmost order.
   * @return a list of valid positions that color can move to.
   */
  @Override
  public ArrayList<HexPosition> getValidMoves() {
    LinkedHashMap<HexPosition, ArrayList<HexPosition>> validityMap =
            this.currentTurn.equals(TeamColor.WHITE)
                    ? this.validWhiteMoves : this.validBlackMoves;
    if (isGameOver()) {
      return new ArrayList<>();
    }
    ArrayList<HexPosition> result = new ArrayList<>();
    validityMap.entrySet().forEach(entry -> {
      result.add(entry.getKey());});

    return result;
  }

  /**
   * Returns the number of pieces a move will flip for the current player at a given
   * position.
   * @param posn the position to
   * @return the number of pieces that will be flipped if a piece is placed at posn
   * @throws IllegalArgumentException if the position is of bounds
   */
  public int flipCount(HexPosition posn) {
    LinkedHashMap<HexPosition, ArrayList<HexPosition>> validityMap =
            this.currentTurn.equals(TeamColor.WHITE)
                    ? this.validWhiteMoves : this.validBlackMoves;
    return validityMap.get(posn).size();
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
   * Returns an ArrayList of all the pieces to be flipped on a single Q file.
   *
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
   * Returns an ArrayList of all the pieces to be flipped on a single R file.
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
   * Returns an ArrayList of all the pieces to be flipped on a single S file.
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
      LinkedHashMap<HexPosition, ArrayList<HexPosition>> validityMap = teams == 0 ? this.validWhiteMoves :
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

  private void validatePosition(HexPosition posn) {
    if (!validPosition(posn)) {
      throw new IllegalArgumentException("Invalid position, out of bounds");
    }
  }


  @Override
  public void addPiece(HexPosition posn) {
    gameRunning();
    LinkedHashMap<HexPosition, ArrayList<HexPosition>> validityMap = currentTurn.equals(TeamColor.WHITE) ?
            this.validWhiteMoves : this.validBlackMoves;
    validatePosition(posn);
    if (validityMap.isEmpty()) {
      throw new IllegalStateException("No legal moves, player must pass");
    }
    else if (!validityMap.containsKey(posn)) {
      throw new IllegalStateException("Invalid move");
    }

    lastPass = false;

    this.board.put(posn, currentTurn);
    setColor(currentTurn, validityMap.get(posn));

    this.currentTurn = this.currentTurn.cycle();
    updateValidMoves();
  }

  @Override
  public TeamColor getPieceAt(HexPosition posn) {
    validatePosition(posn);
    if (!board.containsKey(posn)) {
      return null;
    }

    return this.board.get(new HexPosition(posn));
  }

  @Override
  public TeamColor getCurrentTurn() {
    gameRunning();
    return this.currentTurn;
  }

  @Override
  public void pass() {
    gameRunning();
    if (!this.lastPass) {
      this.currentTurn = this.currentTurn.cycle();
      this.lastPass = true;
    } else {
      validBlackMoves = new LinkedHashMap<>();
      validWhiteMoves = new LinkedHashMap<>();
      //makes it so gameover would flip
    }

  }

  @Override
  public HashMap<HexPosition, TeamColor> getBoard() {
    return new HashMap<HexPosition, TeamColor>(board);
  }

  @Override
  public boolean isMoveValid(HexPosition pos) {
    return getValidMoves().contains(pos);
  }

  @Override
  public int getWhiteScore() {
    int whiteCount = 0;

    for (TeamColor color : board.values()) {
      if (color.equals(TeamColor.WHITE)) {
        whiteCount = whiteCount + 1;
      }
    }

    return whiteCount;
  }

  @Override
  public int getBlackScore() {
    int blackCount = 0;
    for (TeamColor color : board.values()) {
      if (color.equals(TeamColor.BLACK)) {
        blackCount = blackCount + 1;
      }
    }
    return blackCount;
  }

  /**
   * Throw exception if the game is called.
   */
  private void gameRunning() {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!");
    }
  }

  @Override
  public TeamColor getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over!");
    }

    int blackCount = getBlackScore();
    int whiteCount = getWhiteScore();

    if (blackCount == whiteCount) {
      return null;
    }
    return (blackCount > whiteCount) ? TeamColor.BLACK : TeamColor.WHITE;
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
