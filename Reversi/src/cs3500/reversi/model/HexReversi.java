package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;

public class HexReversi implements ReversiModel {
  private final int radius;

  private TeamColor currentTurn;

  private HashMap<HexPosition, TeamColor> board;

  public HexReversi() {
    this(6);
  }

  public HexReversi(int radius) {

    if (radius < 2) {
      throw new IllegalArgumentException("radius must be greater than 2!");
    }

    this.radius = radius;
    board = new HashMap<>();
  }

  private void dealBoard() {
    TeamColor current = TeamColor.BLACK;
    for (int r = -1; r <= 1; r++) {
      int rMin = Math.max(-1, -r - 1);
      int rMax = Math.min(1, -r + 1);

      for (int q = rMin; q <= rMax; q++) {
        int s = -r - q;
        if (!(q == 0 && r == 0 && s == 0)) {
          board.put(new HexPosition(q, r, s), current);
          current = current.cycle();
        }
      }
    }
  }

//  private boolean validMove(TeamColor color, HexPosition posn) {
//    int currQ = posn.getQPosition();
//    int currR = posn.getRPosition();
//    int currS = posn.getSPosition();
//
//    ArrayList<HexPosition> toFlip = new ArrayList<>();
//    int currIterator = currQ + 1;
//    while ()
//
//
//
//
//    for (int qneg = currQ-1; qneg > -radius; qneg--) {
//
//    }
//  }


  @Override
  public void startGame() {
    dealBoard();
  }

  @Override
  public void addPiece(TeamColor piece, HexPosition posn) {


  }

  @Override
  public TeamColor getPieceAt(HexPosition posn) {
    return board.get(new HexPosition(posn));
  }

  @Override
  public TeamColor getCurrentTurn() {
    return currentTurn;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getRadius() {
    return radius;
  }
}
