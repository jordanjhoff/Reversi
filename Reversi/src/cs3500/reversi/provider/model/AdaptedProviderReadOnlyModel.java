package cs3500.reversi.provider.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.provider.model.CellType;
import cs3500.reversi.provider.model.Color;
import cs3500.reversi.provider.model.ICell;
import cs3500.reversi.provider.model.ReadonlyReversiModel;
import cs3500.reversi.provider.view.AdaptedProviderFeatures;

public class AdaptedProviderReadOnlyModel implements ReadonlyReversiModel {
  private cs3500.reversi.model.ReadonlyReversiModel adaptee;
  public AdaptedProviderReadOnlyModel(cs3500.reversi.model.ReadonlyReversiModel ourModel) {
    this.adaptee = ourModel;
  }

  @Override
  public List<List<ICell>> getBoard() {
    int radius = this.adaptee.getSize();
    List<List<ICell>> board = new ArrayList<>();

    for (int r = -radius; r <= radius; r++) {
      board.add(new ArrayList<>());
      int rMin = Math.max(-radius, -r - radius);
      int rMax = Math.min(radius, -r + radius);

      for (int q = rMin; q <= rMax; q++) {
        int s = -r - q;
        TeamColor col = adaptee.getBoard().get(new HexPosition(q, r, s));
        Cell curCell = new Cell(q, r);
        if (col == null) {
          board.get(r + radius).add(curCell);

        }
        else if (col.equals(TeamColor.BLACK)) {
          curCell.setColor(Color.BLACK);
          board.get(r + radius).add(curCell);
        }
        else {
          curCell.setColor(Color.WHITE);
          board.get(r + radius).add(curCell);
        }
      }
    }
    return board;
  }

  private HexPosition convertQR(int q, int r) {
    return new HexPosition(q, r, -q - r);
  }

  @Override
  public boolean isGameOver() {
    return adaptee.isGameOver();
  }

  @Override
  public int returnSize() {
    return adaptee.getSize();
  }

  @Override
  public CellType returnCellContents(int q, int r) throws IllegalArgumentException {
    TeamColor color = adaptee.getPieceAt(convertQR(q,r));
    if (color.equals(TeamColor.BLACK)) {
      return CellType.BLACK;
    }
    else if (color.equals(TeamColor.WHITE)){
      return CellType.WHITE;
    }
    else {
      return CellType.EMPTY;
    }
  }

  @Override
  public boolean isLegalMove(int q, int r) {
    return adaptee.getValidMoves().contains(convertQR(q,r));
  }

  @Override
  public int getScore(Color color) {
    TeamColor ourColor = color.equals(CellType.WHITE) ? TeamColor.WHITE : TeamColor.BLACK;
    return adaptee.getScoreColor(ourColor);
  }

  @Override
  public boolean currPlayerLegalMoves() {
    return !adaptee.getValidMoves().isEmpty();
  }

  @Override
  public List<List<ICell>> getNeighbors(ICell cell) {
    return null;
  }

  @Override
  public void extendNeighbors(List<List<ICell>> neighbors) {

  }

  @Override
  public ICell getCellAt(int column, int row) {
    ICell cell = new Cell(column, row);
    if (adaptee.getBoard().get(convertQR(column, row)).equals(TeamColor.BLACK)) {
      cell.setColor(Color.BLACK);
    } else if (adaptee.getBoard().get(convertQR(column, row)).equals(TeamColor.WHITE)) {
      cell.setColor(Color.WHITE);
    }

    return cell;
  }

  @Override
  public List<ICell> getEdgeCells(ICell cell) {
    List<ICell> result = new ArrayList<>();
    int radius = this.adaptee.getSize();




    return result;
  }

  @Override
  public Color getCurrentColor() {
    return adaptee.getCurrentTurn() == TeamColor.BLACK ? Color.BLACK : Color.WHITE;
  }
}
