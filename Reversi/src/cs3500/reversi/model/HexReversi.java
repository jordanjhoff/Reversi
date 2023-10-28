package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;

public class HexReversi implements ReversiModel {
  private int radius;

  private TeamColor currentTurn;
  boolean started;

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


  /*
  private boolean validMove(TeamColor color, HexPosition posn) {
    notStarted();
    if (!(validPosition(posn))) {
      throw new IllegalArgumentException("Invalid input position");
    }

    int currQ = posn.getQPosition();
    int currR = posn.getRPosition();
    int currS = posn.getSPosition();

    ArrayList<HexPosition> toFlip = new ArrayList<>();
    int currIterator = currQ + 1;
    while ()




    for (int qneg = currQ-1; qneg > -radius; qneg--) {

    }
  }
  */

  private boolean validPosition(HexPosition pos) {
    notStarted();
    if ((Math.abs(pos.getQPosition()) <= this.radius)
            && (Math.abs(pos.getRPosition()) <= this.radius)
            && (pos.getSPosition() == -pos.getQPosition() - pos.getRPosition())) {
      return true;
    }
    return false;
  }




  @Override
  public void addPiece(TeamColor piece, HexPosition posn) {
    notStarted();
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
