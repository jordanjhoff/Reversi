package cs3500.reversi.strategy;

import java.util.ArrayList;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.TeamColor;

/**
 * Class to represent the strategy of capturing the most pieces
 * possible in for a given turn. A basic AI for reversiView.
 * If there are multiple possible moves with the same number of captures,
 * it will pick the uppermost leftmost piece.
 */
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
        int flipC = model.flipCount(posList.get(i));
        if (max < flipC) {
          max = flipC;
          flipMax = posList.get(i);
        }
      }
      return flipMax;
    }
  }
}
