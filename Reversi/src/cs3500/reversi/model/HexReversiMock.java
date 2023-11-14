package cs3500.reversi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HexReversiMock implements ReversiModel {
  Appendable out;

  public HexReversiMock(Appendable out) {
    this.out = out;
  }

  @Override
  public TeamColor getPieceAt(HexPosition posn) {
    try {
      out.append("\ngetPieceAt: " + posn.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return null;
  }

  @Override
  public TeamColor getCurrentTurn() {
    try {
      out.append("\ngetCurrentTurn");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return null;
  }

  @Override
  public TeamColor getWinner() {
    try {
      out.append("\ngetWinner");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return null;
  }

  @Override
  public boolean isGameOver() {
    try {
      out.append("\nisGameOver");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return false;
  }

  @Override
  public int getSize() {
    try {
      out.append("\ngetSize");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return 0;
  }

  @Override
  public ArrayList<HexPosition> getValidMoves() {
    try {
      out.append("\ngetValidMoves");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return null;
  }

  @Override
  public HashMap<HexPosition, TeamColor> getBoard() {
    try {
      out.append("\ngetBoard");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return null;
  }

  @Override
  public boolean isMoveValid(HexPosition pos) {
    try {
      out.append("\nisMoveValid: " + pos.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return false;
  }

  @Override
  public int getWhiteScore() {
    try {
      out.append("\ngetWhiteScore");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return 0;
  }

  @Override
  public int getBlackScore() {
    try {
      out.append("\ngetBlackScore");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return 0;
  }

  @Override
  public int flipCount(HexPosition posn) {
    try {
      out.append("\nflipCount: " + posn.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    return 0;
  }

  @Override
  public void addPiece(HexPosition posn) {
    try {
      out.append("\naddPiece: " + posn.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }

  @Override
  public void pass() {
    try {
      out.append("\npass");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }
}
