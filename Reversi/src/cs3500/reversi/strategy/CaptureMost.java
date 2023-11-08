package cs3500.reversi.strategy;

import java.util.ArrayList;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.TeamColor;

public class CaptureMost implements ReversiStrategy {
  @Override
  public HexPosition choosePosn(ReadonlyHexReversiModel model, TeamColor color) {
    ArrayList<HexPosition> posList = model.getValidMoves();
    if (posList.isEmpty()) {
      return null;
    }
    else {
      int max = 0;
      HexPosition flipMax = posList.get(0);
      for (int i = 0; i < posList.size(); i++) {
        if (max < model.flipCount(posList.get(i))) {
          max = model.flipCount(posList.get(i));
          flipMax = posList.get(i);
        }
      }
      return flipMax;
    }
  }
}
