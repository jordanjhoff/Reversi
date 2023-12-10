package cs3500.reversi.view;

import cs3500.reversi.model.TeamColor;

import java.io.IOException;

/**
 * A mock implementation of the ReversiGUIView class for testing and logging purposes.
 */
public class ReversiGUIViewMock implements IReversiView {

  private Appendable out;

  /**
   * Constructs a ReversiGUIViewMock with the given Appendable.
   *
   * @param out The Appendable for logging method invocations.
   */
  public ReversiGUIViewMock(Appendable out) {
    this.out = out;
  }

  @Override
  public void setVisible(boolean show) {
    try {
      out.append("\nsetVisible: " + show);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }

  @Override
  public void renderView(TeamColor color) {
    try {
      out.append("\nrenderView: " + color);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }

  @Override
  public void addFeatureListener(MoveFeatures features) {
    try {
      out.append("\naddFeatureListener");
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }

  @Override
  public void enableMoves(boolean enable) {
    try {
      out.append("\nenableMoves: " + enable);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }

  @Override
  public void displayMessage(String message) {
    try {
      out.append("\ndisplayMessage: " + message);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read data");
    }
  }
}
