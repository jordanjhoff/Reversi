package cs3500.reversi.provider.strat;

import java.util.ArrayList;
import java.util.List;
import cs3500.reversi.provider.model.ICell;
import cs3500.reversi.provider.model.ReadonlyReversiModel;


/**
 * A class which represents the corners strategy that makes a list of all the playable corners
 * that can be played according to the strategy.
 */
public class CornersStrategy implements Strategy {
  //@Override

  /**
   * Creates a list of available moves for the player based on the strategy.
   * @param model the model of the game Reversi.
   * @return a list of all available moves in order from best to worst.
   */
  public List<ICell> chooseMove(ReadonlyReversiModel model) {
    final int constraint = model.returnSize() / 2;
    List<ICell> availableMoves = new ArrayList<>();

    List<ICell> corners = new ArrayList<>();
    corners.add(model.getCellAt(constraint , 0)); //top left
    corners.add(model.getCellAt(2 * constraint, 0)); //top right
    corners.add(model.getCellAt(0, constraint)); //mid left
    corners.add(model.getCellAt(2 * constraint, constraint)); //mid right
    corners.add(model.getCellAt(0, 2 * constraint)); //bot left
    corners.add(model.getCellAt(constraint, 2 * constraint)); //bot right

    for (ICell cell : corners) {
      int[] coords = cell.getCoordinates();
      if (model.isLegalMove(coords[0], coords[1])) {
        availableMoves.add(cell);
      }
    }
    if (!availableMoves.isEmpty()) {
      return List.of(availableMoves.get(0));
    }
    return availableMoves;
  }
}
