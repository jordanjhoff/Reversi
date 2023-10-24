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
    this.radius = radius;
    board = new HashMap<>();
    dealBoard();
  }

  private void dealBoard() {
    //middle 6;

  }

  private boolean validMove(TeamColor color, HexPosition posn) {
    int currQ = posn.getQPosition();
    int currR = posn.getRPosition();
    int currS = posn.getSPosition();

    ArrayList<HexPosition> toFlip = new ArrayList<>();
    int currIterator = currQ + 1;
    while ()




    for (int qneg = currQ-1; qneg > -radius; qneg--) {

    }
  }


  @Override
  public void startGame(int radius) {

  }

  @Override
  public void addPiece(TeamColor piece, HexPosition posn) {

  }

  @Override
  public TeamColor getPieceAt(HexPosition posn) {
    return null;
  }

  @Override
  public TeamColor getCurrentTurn() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getRadius() {
    return 0;
  }
}
