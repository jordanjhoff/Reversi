package cs3500.reversi.provider.strat;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.provider.model.ICell;
import cs3500.reversi.provider.model.ReadonlyReversiModel;


/**
 * A class which represents the strategy AvoidCorners which returns a list of the cells next
 * to the corners that should not be played according to the strategy.
 */
public class AvoidCornersStrategy implements Strategy {
  @Override
  public List<ICell> chooseMove(ReadonlyReversiModel model) {
    final int constraint = model.returnSize() / 2;
    ArrayList<ICell> corners = new ArrayList<>();
    corners.add(model.getCellAt(constraint , 0)); //top left
    corners.add(model.getCellAt(2 * constraint, 0)); //top right
    corners.add(model.getCellAt(0, constraint)); //mid left
    corners.add(model.getCellAt(2 * constraint, constraint)); //mid right
    corners.add(model.getCellAt(0, 2 * constraint)); //bot left
    corners.add(model.getCellAt(constraint, 2 * constraint)); //bot right

    ArrayList<ICell> edgeCells = new ArrayList<>();
    for (ICell cell : corners) {
      edgeCells.addAll(model.getEdgeCells(cell));
    }

    ArrayList<ICell> playableEdgeCells = new ArrayList<>();
    for (ICell cell : edgeCells) {
      int[] coords = cell.getCoordinates();
      if (model.isLegalMove(coords[0], coords[1])) {
        playableEdgeCells.add(cell);
      }
    }
    return playableEdgeCells;
  }
}
