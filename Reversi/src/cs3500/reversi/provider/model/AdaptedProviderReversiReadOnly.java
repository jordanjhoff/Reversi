package cs3500.reversi.provider.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.TeamColor;

public class AdaptedProviderReversiReadOnly implements ReadonlyReversiModel {
  private final cs3500.reversi.model.ReadonlyReversiModel adaptee;

  public AdaptedProviderReversiReadOnly(cs3500.reversi.model.ReadonlyReversiModel ourModel) {
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
        TeamColor col = adaptee.getPieceAt(new HexPosition(q, r, s));
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
    return adaptee.getSize() * 2 + 1;
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
    TeamColor ourColor = color.equals(Color.WHITE) ? TeamColor.WHITE : TeamColor.BLACK;
    return adaptee.getScoreColor(ourColor);
  }

  @Override
  public boolean currPlayerLegalMoves() {
    return !adaptee.getValidMoves().isEmpty();
  }

  @Override
  public List<List<ICell>> getNeighbors(ICell cell) {
    List<ICell> edge = this.getEdgeCells(cell);
    List<List<ICell>> results = new ArrayList<>();
    for (ICell cel : edge) {
      if (!cel.returnType().equals(CellType.EMPTY) && !cel.returnType().equals(cell.returnType())) {
        results.add(Arrays.asList(cell, cel));
      }
    }
    return results;
  }

  @Override
  public void extendNeighbors(List<List<ICell>> neighbors) {
    TeamColor currColor = TeamColor.WHITE;
    if (neighbors.get(0).get(0).returnType().equals(CellType.WHITE)) {
      currColor = TeamColor.WHITE;
    }
    else if (neighbors.get(0).get(0).returnType().equals(CellType.BLACK)) {
      currColor = TeamColor.BLACK;
    }
    List<int[]> vectors = calculateVectors(neighbors);
    for (int i = 0; i < vectors.size(); i++) {
      int[] qr = neighbors.get(0).get(0).getCoordinates();
      HexPosition currPosn = convertQR(qr[0], qr[1]);
      List<ICell> toFlip = toFlip(currColor, currPosn, vectors.get(i));
      ArrayList<ICell> ref = new ArrayList<>(neighbors.get(i));
      ref.addAll(toFlip);
      neighbors.set(i, ref);
    }

  }

  private List<ICell> toFlip(TeamColor color, HexPosition posn, int[] vector) {
    HashMap<HexPosition, TeamColor> board = this.adaptee.getBoard();
    if (vector.length != 3) {
      throw new IllegalArgumentException("Invalid position vector");
    }
    ArrayList<ICell> toBeFlipped = new ArrayList<>();
    HexPosition currPosn = posn;
    while (Math.abs(posn.getQPosition()) <= this.adaptee.getSize() + 1 &&
            Math.abs(posn.getRPosition()) <= this.adaptee.getSize() + 1 &&
            Math.abs(posn.getSPosition()) <= this.adaptee.getSize() + 1) {
      currPosn = new HexPosition(currPosn.getQPosition() + vector[0],
              currPosn.getRPosition() + vector[1],
              currPosn.getSPosition() + vector[2]);
      if (!board.containsKey(currPosn) || !validPosition(currPosn)) {
        toBeFlipped.clear();
        break;
      }
      else if (board.get(currPosn).equals(color)) {
        break;
      }
      else if (!board.get(currPosn).equals(color)) {
        toBeFlipped.add(new Cell(currPosn.getQPosition(), currPosn.getRPosition()));
      }
    }

    return toBeFlipped;
  }


  private List<int[]> calculateVectors(List<List<ICell>> neighbors) {
    List<int[]> vectors = new ArrayList<>();
    for (List<ICell> neighbor : neighbors) {
      int qVector = neighbor.get(1).getCoordinates()[0] - neighbor.get(0).getCoordinates()[0];
      int rVector = neighbor.get(1).getCoordinates()[1] - neighbor.get(0).getCoordinates()[1];
      vectors.add(new int[]{qVector, rVector, -rVector - qVector});
    }
    return vectors;
  }
  @Override
  public ICell getCellAt(int column, int row) {
    ICell cell = new Cell(column, row);
    HashMap<HexPosition, TeamColor> board = adaptee.getBoard();
    if (board.containsKey(convertQR(column, row))) {
      if (board.get(convertQR(column, row)).equals(TeamColor.BLACK)) {
        cell.setColor(Color.BLACK);
      }
      else if (board.get(convertQR(column, row)).equals(TeamColor.WHITE)) {
        cell.setColor(Color.WHITE);
      }
    }
    return cell;
  }

  private boolean validPosition(HexPosition pos) {
    return (Math.abs(pos.getQPosition()) <= adaptee.getSize())
            && (Math.abs(pos.getRPosition()) <= adaptee.getSize())
            && (pos.getSPosition() == -pos.getQPosition() - pos.getRPosition());
  }

  @Override
  public List<ICell> getEdgeCells(ICell cell) {
    List<ICell> result = new ArrayList<>();
    int q = cell.getCoordinates()[0];
    int r = cell.getCoordinates()[1];
    int s = -q - r;

    // The six neighbors of a hexagons in a hexagonal grid
    int[][] directions = {{1, -1, 0}, {1, 0, -1}, {0, 1, -1}, {-1, 1, 0}, {-1, 0, 1}, {0, -1, 1}};

    for (int[] direction : directions) {
      int neighborQ = q + direction[0];
      int neighborR = r + direction[1];
      int neighborS = s + direction[2];
      HexPosition neighborPos = new HexPosition(neighborQ, neighborR, neighborS);
      if (validPosition(neighborPos)) {
        ICell neighborCell = new Cell(neighborQ, neighborR);
        if (adaptee.getBoard().containsKey(neighborPos)) {
          TeamColor color = adaptee.getBoard().get(neighborPos);
          if (color.equals(TeamColor.BLACK)) {
            neighborCell.setColor(Color.BLACK);
          } else if (color.equals(TeamColor.WHITE)) {
            neighborCell.setColor(Color.WHITE);
          }

        }
        result.add(neighborCell);
      }

    }
    return result;
  }

  @Override
  public Color getCurrentColor() {
    return adaptee.getCurrentTurn() == TeamColor.BLACK ? Color.BLACK : Color.WHITE;
  }

}
