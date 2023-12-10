package cs3500.reversi.controller;

import cs3500.reversi.model.Position;
import cs3500.reversi.model.TeamColor;

import java.io.IOException;

/**
 * A mock implementation of the VisualController class for testing and logging purposes.
 * This class logs method invocations and delegates calls to an adaptee VisualController instance.
 */
public class VisualControllerMock implements ReversiController {
  private Appendable out;
  private ReversiController adaptee;

  /**
   * Constructs a VisualControllerMock with the given Appendable and adaptee.
   *
   * @param out The Appendable for logging method invocations.
   * @param adaptee functional class to make the moves
   */
  public VisualControllerMock(Appendable out, ReversiController adaptee) {
    this.out = out;
    this.adaptee = adaptee;
  }

  @Override
  public void notifyMakeMove(Position posn) {
    try {
      out.append("\nmakeMove: " + posn.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.notifyMakeMove(posn);
  }

  @Override
  public void notifyPassTurn() {
    try {
      out.append("\npassTurn");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.notifyPassTurn();
  }

  @Override
  public void notifyStartGame(TeamColor startingTurn) {
    try {
      out.append("\nstartGame: " + startingTurn);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.notifyStartGame(startingTurn);
  }

  @Override
  public void notifyMessage(TeamColor color, String message) {
    try {
      out.append("\nnotifyMessage: " + color + ", " + message);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.notifyMessage(color, message);
  }

  @Override
  public void notifyGameOver() {
    try {
      out.append("\nnotifyGameOver");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.notifyGameOver();
  }

  @Override
  public void notifyUpdatedGameState() {
    try {
      out.append("\nnotifyUpdatedGamestate");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.notifyUpdatedGameState();
  }

  @Override
  public void notifyAdvanceTurn(TeamColor currentTurn) {
    try {
      out.append("\nnotifyAdvanceTurn: " + currentTurn);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.notifyAdvanceTurn(currentTurn);
  }
}
