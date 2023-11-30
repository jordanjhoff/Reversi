package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.TeamColor;

import java.io.IOException;

/**
 * A mock implementation of the VisualController class for testing and logging purposes.
 * This class logs method invocations and delegates calls to an adaptee VisualController instance.
 */
public class VisualControllerMock implements HexReversiController {
  private Appendable out;
  private HexReversiController adaptee;

  public VisualControllerMock(Appendable out, HexReversiController adaptee) {
    this.out = out;
    this.adaptee = adaptee;
  }

  @Override
  public void notifyMakeMove(HexPosition posn) {
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
  public void notifyUpdateView() {
    try {
      out.append("\nnotifyUpdateView");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.notifyUpdateView();
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
  public void notifyUpdatedGamestate() {
    try {
      out.append("\nnotifyUpdatedGamestate");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.notifyUpdatedGamestate();
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
