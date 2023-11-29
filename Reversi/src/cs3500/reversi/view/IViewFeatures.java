package cs3500.reversi.view;

import cs3500.reversi.model.HexPosition;

public interface IViewFeatures {

  void makeMove(HexPosition posn);

  void passTurn();

  void quit();
}
