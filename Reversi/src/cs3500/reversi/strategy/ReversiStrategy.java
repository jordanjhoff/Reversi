package cs3500.reversi.strategy;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;

public interface ReversiStrategy {

  HexPosition choosePosn(ReadonlyHexReversiModel model, TeamColor color);
}
