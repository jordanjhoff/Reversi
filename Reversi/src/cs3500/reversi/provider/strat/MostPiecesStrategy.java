package cs3500.reversi.provider.strat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import cs3500.reversi.provider.model.ICell;
import cs3500.reversi.provider.model.ReadonlyReversiModel;
import cs3500.reversi.provider.strat.Strategy;
import java.util.Optional;

/**
 * A class that represents the strategy that obtains the most amount of pieces
 * during each turn.
 */
public class MostPiecesStrategy implements Strategy {

  /**
   * Creates a list of available moves for the player based on the strategy.
   * @param model the model of the game Reversi.
   * @return a list of all available moves in order from best to worst.
   */
  @Override
  public List<ICell> chooseMove(ReadonlyReversiModel model) {
    int highestCount = 0;
    Optional<ICell> bestCell = Optional.empty();
    List<ICell> availableMoves = new ArrayList<>();
    List<List<ICell>> board = model.getBoard();
    for (List<ICell> diag : board) {
      for (ICell cell : diag) {
        int[] coordinates = cell.getCoordinates();
        if (model.isLegalMove(coordinates[0], coordinates[1])) {
          availableMoves.add(cell);
        }
      }
    }

    availableMoves.sort(Comparator.comparingInt(ICell -> getScore(model, ICell)));
    availableMoves = removeDuplicates(model, availableMoves);
    return availableMoves;
  }

  private void combineDiags(List<List<ICell>> changeableCells) {
    ICell previousCell = new Cell(-1, -1);
    for (int diag = 0; diag < changeableCells.size(); diag++) {
      ICell cell = changeableCells.get(diag).get(0);
      if (previousCell == cell) {
        List<ICell> thisDiag = changeableCells.get(diag);
        List<ICell> previousDiag = changeableCells.get(diag - 1);
        List<ICell> newCombinedDiag = new ArrayList<>();
        newCombinedDiag.addAll(thisDiag);
        newCombinedDiag.addAll(previousDiag);
        newCombinedDiag.remove(cell);
        changeableCells.remove(thisDiag);
        changeableCells.remove(previousDiag);
        changeableCells.add(newCombinedDiag);
      }
      else {
        previousCell = cell;
      }
    }
  }

  private int getScore(ReadonlyReversiModel model, ICell cell) {
    List<List<ICell>> changeableCells = model.getNeighbors(cell);
    model.extendNeighbors(changeableCells);
    combineDiags(changeableCells);

    int count = 0;
    for (List<ICell> diag : changeableCells) {
      count = count + diag.size();
    }
    return count;
  }

  private List<ICell> removeDuplicates(ReadonlyReversiModel model, List<ICell> moves) {
    ArrayList<Integer> scoreList = new ArrayList<>();
    ArrayList<ICell> newList = new ArrayList<>();

    for (ICell move : moves) {
      if (!scoreList.contains(getScore(model, move))) {
        scoreList.add(getScore(model, move));
        newList.add(move);
      }
    }
    return newList;
  }
}
