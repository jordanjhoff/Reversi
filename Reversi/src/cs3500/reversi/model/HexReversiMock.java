package cs3500.reversi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A mock implementation of the ReversiModel interface for testing and logging purposes.
 * This class delegates calls to an adaptee HexReversi instance and logs method invocations.
 */
public class HexReversiMock implements ReversiModel {
  private Appendable out;
  private HexReversi adaptee;

  public HexReversiMock(Appendable out, HexReversi adaptee) {
    this.out = out;
    this.adaptee = adaptee;
  }

  @Override
  public TeamColor getPieceAt(HexPosition posn) {
    try {
      out.append("\ngetPieceAt: " + posn.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.getPieceAt(posn);
  }

  @Override
  public TeamColor getCurrentTurn() {
    try {
      out.append("\ngetCurrentTurn");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.getCurrentTurn();
  }

  @Override
  public TeamColor getWinner() {
    try {
      out.append("\ngetWinner");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.getWinner();
  }

  @Override
  public boolean isGameOver() {
    try {
      out.append("\nisGameOver");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.isGameOver();
  }

  @Override
  public int getSize() {
    try {
      out.append("\ngetSize");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.getSize();
  }

  @Override
  public ArrayList<HexPosition> getValidMoves() {
    try {
      out.append("\ngetValidMoves");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.getValidMoves();
  }

  @Override
  public HashMap<HexPosition, TeamColor> getBoard() {
    try {
      out.append("\ngetBoard");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.getBoard();
  }


  @Override
  public int getScoreColor(TeamColor scoreColor) {
    try {
      out.append("\ngetScoreColor: " + scoreColor);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.getScoreColor(scoreColor);
  }

  @Override
  public int flipCount(HexPosition posn) {
    int value = adaptee.flipCount(posn);
    try {
      out.append("\nflipCount: " + posn.toString() + ", value: " + value);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return value;
  }

  @Override
  public void addPiece(TeamColor color, HexPosition posn) {
    try {
      out.append("\naddPiece: " + posn.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.addPiece(color, posn);
  }

  @Override
  public void pass() {
    adaptee.pass();
    try {
      out.append("\npass");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }

  @Override
  public void addFeatureObserver(ModelFeatures features) {

  }

  @Override
  public void startGame() {

  }
}
