package cs3500.reversi.controller;

import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.MoveFeatures;
/**
 * A mock implementation of the HumanPlayer class for testing and logging purposes.
 * This class logs method invocations and delegates calls to an adaptee HumanPlayer instance.
 */

public class PlayerMock implements Player {
  //the output stream to record moves
  private Appendable out;
  //adaptee to handle player impl
  private Player adaptee;

  /**
   * Constructs a PlayerMock with the given Appendable and TeamColor.
   *
   * @param out   The Appendable for logging method invocations.
   * @param player The player to be mocked
   */
  public PlayerMock(Appendable out, Player player) {
    this.out = out;
    this.adaptee = player;
  }

  @Override
  public void addFeatureListener(MoveFeatures features) {
    try {
      out.append("\naddFeatureListener");
    } catch (Exception e) {
      throw new IllegalStateException("Could not read data");
    }
    adaptee.addFeatureListener(features);
  }

  @Override
  public TeamColor getColor() {
    try {
      out.append("\ngetColor");
    } catch (Exception e) {
      throw new IllegalStateException("Could not read data");
    }
    return adaptee.getColor();
  }

  @Override
  public void promptMove() {
    try {
      out.append("\npromptMove");
    } catch (Exception e) {
      throw new IllegalStateException("Could not read data");
    }
  }
}
