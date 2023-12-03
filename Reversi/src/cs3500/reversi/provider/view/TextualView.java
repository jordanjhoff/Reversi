package cs3500.reversi.provider.view;

import java.util.List;

import cs3500.reversi.provider.model.ICell;

/**
 * An class representing a textual view.
 */
public class TextualView implements ITextualView {
  private final List<List<ICell>> board;
  private final int constraint;

  public TextualView(List<List<ICell>> board) {
    this.board = board;
    constraint = board.size() / 2;
  }

  /**
   * System.out.println the board, returns "" (for now).
   *
   * @return the string representation of the board
   */
  public String toString() {
    for (int i = 0; i < board.size(); i++) {
      printSpaceNTimes(Math.abs(i - constraint));
      for (ICell cell : board.get(i)) {
        System.out.print(cell.toString() + " ");
      }
      System.out.println();
    }
    return "";
  }

  /**
   * Prints a space n number of times.
   *
   * @param count # of times to print
   */
  private void printSpaceNTimes(int count) {
    for (int i = 0; i < count; i++) {
      System.out.print(" ");
    }
  }
}
