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

  void renderView(TeamColor color);

  void addFeatureListener(MoveFeatures features);

  void enableMoves(boolean enable);

  void displayMessage(String message);

}
