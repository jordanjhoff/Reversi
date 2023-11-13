package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.view.IViewFeatures;

public interface HexReversiController {

  void addPlayer(Player player);

  void play();

  void handleInput(HexPosition pos);
}
