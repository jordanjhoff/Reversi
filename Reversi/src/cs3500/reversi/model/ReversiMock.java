package cs3500.reversi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A mock implementation of the ReversiModel interface for testing and logging purposes.
 * This class delegates calls to an adaptee HexReversi instance and logs method invocations.
 */
public class ReversiMock implements ReversiModel {
  private Appendable out;
  private ReversiModel adaptee;

  /**
   * Constructs a HexReversiMock with the given Appendable and adaptee.
   *
   * @param out The Appendable for logging method invocations.
   * @param adaptee functional class to make the moves
   */
  public ReversiMock(Appendable out, ReversiModel adaptee) {
    this.out = out;
    this.adaptee = adaptee;
  }

  @Override
  public TeamColor getPieceAt(Position posn) {
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
  public ArrayList<Position> getValidMoves() {
    try {
      out.append("\ngetValidMoves");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.getValidMoves();
  }

  @Override
  public HashMap<Position, TeamColor> getBoard() {
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
  public int flipCount(Position posn) {
    int value = adaptee.flipCount(posn);
    try {
      out.append("\nflipCount: " + posn.toString() + ", value: " + value);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return value;
  }

  @Override
  public void addPiece(TeamColor color, Position posn) {
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
    adaptee.addFeatureObserver(features);
    try {
      out.append("\naddFeatureObserver");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }

  @Override
  public void startGame() {
    adaptee.startGame();
    try {
      out.append("\nstartGame");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }
}
