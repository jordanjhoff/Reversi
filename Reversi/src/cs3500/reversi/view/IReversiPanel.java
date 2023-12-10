package cs3500.reversi.view;

import java.awt.Graphics;

import cs3500.reversi.model.TeamColor;

/**
 * This interface represents a panel that can be drawn on.
 */
public interface IReversiPanel {
  void paintComponent(Graphics g);

  void addFeatureListener(MoveFeatures features);

  void setPlayer(TeamColor player);

  void enableMoves(boolean enable);

}
