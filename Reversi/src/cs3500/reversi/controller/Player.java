package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;

public interface Player {
  HexPosition play(ReadonlyHexReversiModel model);

  TeamColor getColor();

}
