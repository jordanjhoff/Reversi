package cs3500.reversi.view;

import java.awt.*;

import cs3500.reversi.model.TeamColor;

public interface IReversiPanel {
  void paintComponent(Graphics g);

  void addFeatureListener(MoveFeatures features);

  void setPlayer(TeamColor player);

  void enableMoves(boolean enable);

}
