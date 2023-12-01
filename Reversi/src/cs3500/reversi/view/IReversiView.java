package cs3500.reversi.view;

import cs3500.reversi.model.TeamColor;

/**
 * The graphical user interface view for a Reversi game. It allows setting visibility of the view.
 */
public interface IReversiView {

  /**
   * Sets the visibility of the GUI view.
   *
   * @param show True to make the view visible, false to hide it.
   */
  void setVisible(boolean show);

  /**
   * Renders the view of the given player.
   *
   * @param color the color of the player.
   */
  void renderView(TeamColor color);

  /**
   * Register the given listner to the view.
   *
   * @param features Feature listener
   */
  void addFeatureListener(MoveFeatures features);

  /**
   * Enables/disables moves for a given view.
   *
   * @param enable true/false to enable/disable interaction with board
   */
  void enableMoves(boolean enable);

  /**
   * Display out a given message.
   *
   * @param message to be displayed
   */
  void displayMessage(String message);

}
