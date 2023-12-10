package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Abstract version of reversi to be extended by SquareReversi and HexReversi.
 * Could be implemented by other versions of reversi with alternative position
 * interface implementations.
 */
public abstract class AbstractReversi implements ReversiModel {
  //the radius from the center in either q, r, or s axis
  protected final int size;
  //the teamcolor of the current player's turn
  protected TeamColor currentTurn;
  //records if the previous move made was a pass. used in determining two passes in a row
  protected boolean lastPass; //keeps track if the previous move made was a pass
  protected boolean started; //has the game been started?


  //these are the listeners to the game, (i.e. the controllers and players)
  protected List<ModelFeatures> featuresListeners;

  /**
   * Represents the board, where the key is the position, and the value is the color of the piece.
   */
  protected HashMap<Position, TeamColor> board;

  /**
   * Represents valid moves, where the key is the position, and the value is the list of
   * Positions that need to be updated if that move is played.
   */
  protected LinkedHashMap<Position, ArrayList<Position>> validBlackMoves;
  protected LinkedHashMap<Position, ArrayList<Position>> validWhiteMoves;


  /**
   * Constructs an abstract game of reversi. Additionally,
   * starts the game by dealing out the board and
   * updating valid moves. Initial move goes to black.
   *
   * @param size size of the game board to be played.
   */
  public AbstractReversi(int size) {
    this.board = new HashMap<>();
    this.validWhiteMoves = new LinkedHashMap<>();
    this.validBlackMoves = new LinkedHashMap<>();
    if (size < 2) {
      throw new IllegalArgumentException("size must be greater than 2!");
    }
    this.size = size;
    dealBoard();
    this.currentTurn = TeamColor.BLACK;
    updateValidMoves(TeamColor.BLACK);
    updateValidMoves(TeamColor.WHITE);
    lastPass = false;

    featuresListeners = new ArrayList<>();
    this.started = false;
  }



  @Override
  public void startGame() {
    if (featuresListeners.size() != 2) {
      throw new IllegalStateException("Game needs 2 players");
    }
    if (started) {
      throw new IllegalStateException("Game has already been started.");
    }
    this.started = true;
    this.notifyListenersStartGame();
    this.notifyListenersAdvanceTurn();
    this.notifyListenersUpdateGamestate();

  }

  /**
   * Notifies game state for all listeners.
   */
  private void notifyListenersStartGame() {
    for (ModelFeatures listener : this.featuresListeners) {
      listener.notifyStartGame(currentTurn);
    }
  }

  /**
   * Notifies the listeners that the board has changed.
   */
  private void notifyListenersUpdateGamestate() {
    for (ModelFeatures listener : this.featuresListeners) {
      listener.notifyUpdatedGameState();
    }
  }

  /**
   * Notifies the listeners that the turn has advanced.
   */
  private void notifyListenersAdvanceTurn() {
    LinkedHashMap<Position, ArrayList<Position>> validityMap =
            this.currentTurn.equals(TeamColor.WHITE)
                    ? this.validWhiteMoves : this.validBlackMoves;
    for (ModelFeatures listener : this.featuresListeners) {
      listener.notifyAdvanceTurn(this.currentTurn);
    }
  }

  /**
   * Returns valid positions that can be played for the current player in top-leftmost order.
   * @return a list of valid positions that color can move to.
   */
  @Override
  public ArrayList<Position> getValidMoves() {
    LinkedHashMap<Position, ArrayList<Position>> validityMap =
            this.currentTurn.equals(TeamColor.WHITE)
                    ? this.validWhiteMoves : this.validBlackMoves;
    if (isGameOver()) {
      return new ArrayList<>();
    }
    ArrayList<Position> result = new ArrayList<>();
    for (Position key : validityMap.keySet()) {
      result.add(key);
    }

    return result;
  }

  /**
   * Returns the number of pieces a move will flip for the current player at a given
   * position.
   * @param posn the position to
   * @return the number of pieces that will be flipped if a piece is placed at posn
   * @throws IllegalArgumentException if the position is of bounds
   */
  public int flipCount(Position posn) {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!!!");
    }
    validatePosition(posn);
    LinkedHashMap<Position, ArrayList<Position>> validityMap =
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
  protected abstract void dealBoard();

  /**
   * Determines which pieces need to be flipped for a placed piece.
   * @param color the color of the piece to be placed
   * @param posn the position to be flipped
   * @param vector the [q,r,s] direction vector to iterate in
   * @return the list of pieces that need to be flipped for a given move.
   */
  protected abstract ArrayList<Position> toFlip(TeamColor color, Position posn, int[] vector);

  protected int getSPosition(Position pos) {
    return -pos.getFirstCoordinate() - pos.getSecondCoordinate();
  }


  /**
   * Determines whether the position is legal on the board.
   *
   * @param pos position to validate
   * @return true if valid position
   */
  protected abstract boolean validPosition(Position pos);

  /**
   * Sets the color of a given list of positions.
   *
   * @param color the color to set at each position
   * @param posns the list of positions to set to color
   */
  private void setColor(TeamColor color, ArrayList<Position> posns) {
    for (Position posn : posns) {
      this.board.put(posn, color);
    }
  }

  /**
   * Updates the validity maps to contain all available moves for a specific team color.
   */
  protected abstract void updateValidMoves(TeamColor color);

  /**
   * Throws an exeption of the posn is out of the board's bounds.
   * @param posn the posn
   * @throws IllegalArgumentException if given posn is ot of bounds
   */
  private void validatePosition(Position posn) {
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
  public void addPiece(TeamColor color, Position posn) {
    if (!started) {
      throw new IllegalStateException("Game has not been started");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!!!");
    }
    validatePosition(posn);
    if (!color.equals(currentTurn)) {
      throw new IllegalStateException("Not your turn");
    }

    LinkedHashMap<Position, ArrayList<Position>> validityMap = color.equals(TeamColor.WHITE) ?
            this.validWhiteMoves : this.validBlackMoves;
    validatePosition(posn);
    if (validityMap.isEmpty()) {
      throw new IllegalStateException("No valid moves. You must pass");
      //notifyCurrentColorListener("No valid moves. You must pass");
    }
    else if (!validityMap.containsKey(posn)) {
      throw new IllegalStateException("Invalid move");
      //notifyCurrentColorListener("Invalid move");
    }

    this.board.put(posn, currentTurn);
    setColor(currentTurn, validityMap.get(posn));

    this.currentTurn = this.currentTurn.cycle();
    updateValidMoves(TeamColor.BLACK);
    updateValidMoves(TeamColor.WHITE);
    if (isGameOver()) {
      notifyGameOver();
    }
    else {
      lastPass = false;
      notifyCurrentColorListener("It's your turn");
      notifyListenersUpdateGamestate();
      notifyListenersAdvanceTurn();

    }

  }

  /**
   * Gets the color of the piece at a given position.
   * @param posn a Position to check
   * @return the color of the piece at the position or null if there is no piece
   */
  @Override
  public TeamColor getPieceAt(Position posn) {
    validatePosition(posn);
    if (!board.containsKey(posn)) {
      return null;
    }

    return this.board.get(posn);
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
    if (!started) {
      throw new IllegalStateException("Game has not been started!");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!");
    }

    if (!this.lastPass) {
      this.currentTurn = this.currentTurn.cycle();
      this.lastPass = true;
    }
    else {
      validBlackMoves = new LinkedHashMap<>();
      validWhiteMoves = new LinkedHashMap<>();
      this.lastPass = false;
      notifyGameOver();
      //makes it so gameover would flip
    }
    if (!isGameOver()) {
      notifyCurrentColorListener("Opponent passed. It's your turn");
      notifyListenersUpdateGamestate();
      notifyListenersAdvanceTurn();
    }
  }

  @Override
  public void addFeatureObserver(ModelFeatures features) {
    if (started) {
      throw new IllegalStateException("Game has already been started!");
    }

    this.featuresListeners.add(features);
  }

  /**
   * Notifies the listeners the current turn and message.
   * @param message message to be sent to listener whose turn it is
   */
  private void notifyCurrentColorListener(String message) {
    for (ModelFeatures listener : this.featuresListeners) {
      listener.notifyMessage(this.currentTurn, message);
    }
  }

  @Override
  public HashMap<Position, TeamColor> getBoard() {
    return new HashMap<>(board);
  }

  @Override
  public int getScoreColor(TeamColor scoreColor) {
    int score = 0;

    for (TeamColor color : board.values()) {
      if (color.equals(scoreColor)) {
        score = score + 1;
      }
    }

    return score;
  }

  @Override
  public TeamColor getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over!");
    }

    int blackCount = getScoreColor(TeamColor.BLACK);
    int whiteCount = getScoreColor(TeamColor.WHITE);

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
    return this.size;
  }

  private void notifyGameOver() {
    for (ModelFeatures listener : this.featuresListeners) {
      notifyListenersUpdateGamestate();
      listener.notifyGameOver();
    }
  }
}

