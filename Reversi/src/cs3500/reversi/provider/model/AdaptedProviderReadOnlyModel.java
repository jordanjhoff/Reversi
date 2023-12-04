package cs3500.reversi.provider.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.TeamColor;

public class AdaptedProviderReadOnlyModel implements ReadonlyReversiModel {
  private final cs3500.reversi.provider.model.AdaptedProviderReversi adaptee;
  public AdaptedProviderReadOnlyModel(cs3500.reversi.provider.model.AdaptedProviderReversi ourModel) {
    this.adaptee = ourModel;
  }

  @Override
  public List<List<ICell>> getBoard() {
    return this.adaptee.getBoard();
  }
  @Override
  public boolean isGameOver() {
    return adaptee.isGameOver();
  }

  @Override
  public int returnSize() {
    return adaptee.returnSize();
  }

  @Override
  public CellType returnCellContents(int q, int r) throws IllegalArgumentException {
    return adaptee.returnCellContents(q, r);
  }

  @Override
  public boolean isLegalMove(int q, int r) {
    return adaptee.isLegalMove(q, r);
  }

  @Override
  public int getScore(Color color) {
    return adaptee.getScore(color);
  }

  @Override
  public boolean currPlayerLegalMoves() {
    return adaptee.currPlayerLegalMoves();
  }

  @Override
  public List<List<ICell>> getNeighbors(ICell cell) {
    return adaptee.getNeighbors(cell);
  }

  @Override
  public void extendNeighbors(List<List<ICell>> neighbors) {
    adaptee.extendNeighbors(neighbors);
  }

  @Override
  public ICell getCellAt(int column, int row) {
    return adaptee.getCellAt(column, row);
  }

  @Override
  public List<ICell> getEdgeCells(ICell cell) {
    return adaptee.getEdgeCells(cell);
  }

  @Override
  public Color getCurrentColor() {
    return adaptee.getCurrentColor();
  }
}
