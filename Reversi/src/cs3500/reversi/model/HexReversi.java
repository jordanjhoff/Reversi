package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A model to play a generic version of HexReversi.
 * Game is automatically started by the constructor and
 * has various fields about size of the game, the current turn,
 * and available current moves, as well as the board itself.
 * INVARIANT: no coordinate will ever be greater than the radius (checked by validatePositions).
 */
public class HexReversi implements ReversiModel {

  //the radius from the center in either q, r, or s axis
  private final int radius;
  //the teamcolor of the current player's turn
  private TeamColor currentTurn;
  //records if the previous move made was a pass. used in determining two passes in a row
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
    updateValidMoves(TeamColor.BLACK);
    updateValidMoves(TeamColor.WHITE);
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
      result.add(entry.getKey()); });

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
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!");
    }
    validatePosition(posn);
    LinkedHashMap<HexPosition, ArrayList<HexPosition>> validityMap =
            this.currentTurn.equals(TeamColor.WHITE)
                    ? this.validWhiteMoves : this.validBlackMoves;
    if (!validityMap.containsKey(posn)) {
      return 0;
    }
    else {
      return validityMap.get(posn).size();
    }

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
   * Determines which pieces need to be flipped for a placed piece.
   * @param color the color of the piece to be placed
   * @param posn the position to be flipped
   * @param vector the [q,r,s] direction vector to iterate in
   * @return the list of pieces that need to be flipped for a given move.
   */
  private ArrayList<HexPosition> toFlip(TeamColor color, HexPosition posn, int[] vector) {
    if (vector.length != 3) {
      throw new IllegalArgumentException("Invalid position vector");
    }
    ArrayList<HexPosition> toBeFlipped = new ArrayList<>();
    HexPosition currPosn = posn;
    while (Math.abs(posn.getQPosition()) <= radius + 1 &&
            Math.abs(posn.getRPosition()) <= radius + 1 &&
            Math.abs(posn.getSPosition()) <= radius + 1) {
      currPosn = new HexPosition(currPosn.getQPosition() + vector[0],
              currPosn.getRPosition() + vector[1],
              currPosn.getSPosition() + vector[2]);
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
   * Updates the validity maps to contain all available moves for a specific team color.
   */
  private void updateValidMoves(TeamColor color) {
    LinkedHashMap<HexPosition, ArrayList<HexPosition>> validityMap = color == TeamColor.WHITE ?
            this.validWhiteMoves :
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
          toBeFlipped.addAll(toFlip(color,currPos, new int[]{0,1,-1}));
          toBeFlipped.addAll(toFlip(color,currPos, new int[]{0,-1,1}));
          toBeFlipped.addAll(toFlip(color,currPos, new int[]{1,0,-1}));
          toBeFlipped.addAll(toFlip(color,currPos, new int[]{-1,0,1}));
          toBeFlipped.addAll(toFlip(color,currPos, new int[]{1,-1,0}));
          toBeFlipped.addAll(toFlip(color,currPos, new int[]{-1,1,0}));

          if (!toBeFlipped.isEmpty()) {
            validityMap.put(currPos,toBeFlipped);
          }
        }
      }
    }


  }

  /**
   * Throws an exeption of the posn is out of the board's bounds.
   * @param posn the posn
   * @throws IllegalArgumentException if given posn is ot of bounds
   */
  private void validatePosition(HexPosition posn) {
    if (!validPosition(posn)) {
      throw new IllegalArgumentException("Invalid position, out of bounds");
    }
  }


  /**
   * Adds a piece at a given location if the move is valid.
   * @param posn the position to add the piece
   * @throws IllegalStateException if game is over, or if its not the right color
   *
   */
  @Override
  public void addPiece(TeamColor color, HexPosition posn) {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!");
    }
    validatePosition(posn);
    if (!color.equals(currentTurn)) {
      throw new IllegalStateException("Wrong turn");
    }

    LinkedHashMap<HexPosition, ArrayList<HexPosition>> validityMap = color.equals(TeamColor.WHITE) ?
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
    updateValidMoves(TeamColor.BLACK);
    updateValidMoves(TeamColor.WHITE);
  }

  /**
   * Gets the color of the piece at a given position.
   * @param posn a HexPosition to check
   * @return the color of the piece at the position or null if there is no piece
   */
  @Override
  public TeamColor getPieceAt(HexPosition posn) {
    validatePosition(posn);
    if (!board.containsKey(posn)) {
      return null;
    }

    return this.board.get(new HexPosition(posn));
  }

  /**
   * Returns whose turn it is.
   * @return the color of the player whose turn it is
   * @throws IllegalStateException if game is over
   */
  @Override
  public TeamColor getCurrentTurn() {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!");
    }
    return this.currentTurn;
  }

  /**
   * Skips the current players turn.
   * @throws IllegalStateException if game is over
   */
  @Override
  public void pass() {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!");
    }

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
    return new HashMap<>(board);
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
