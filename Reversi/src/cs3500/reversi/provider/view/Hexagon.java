package cs3500.reversi.provider.view;

import java.awt.geom.Path2D;

import cs3500.reversi.provider.model.CellType;
//import model.ICell;

/**
 * A class which represents a hexagon on the board of Reversi. Creates a hexagon to be placed
 * on the board for the game Reversi.
 */

public class Hexagon extends Path2D.Double {
  private final double sideLength;
  private final double centerX;
  private final double centerY;
  private final int q;
  private final int r;
  private CellType type;

  /**
   * The constructor for a hexagon.
   *
   * @param centerX the center x coordinate of the hexagon.
   * @param centerY the center y coordinate of the hexagon.
   * @param q       represents the column coordinate of the hexagon on the board.
   * @param r       represents the row coordinate of the hexagon on the board.
   */
  public Hexagon(double centerX, double centerY, int q, int r) {
    this.sideLength = 30;
    this.centerX = centerX;
    this.centerY = centerY;
    this.type = CellType.EMPTY;
    this.q = q;
    this.r = r;
    constructHexagon();
  }

  /**
   * Constructs a hexagon to be placed on the board of Reversi.
   */
  private void constructHexagon() {
    moveTo(centerX + sideLength * Math.cos(0 + Math.PI / 2.0), centerY + sideLength
            * Math.sin(0 + Math.PI / 2.0));
    for (int i = 1; i < 6; i++) {
      double angle = (i * 2.0 * Math.PI / 6.0) + (Math.PI / 2.0);
      lineTo(centerX + sideLength * Math.cos(angle), centerY + sideLength * Math.sin(angle));
    }
    closePath();
  }

  /**
   * Sets the CellType of the hexagon.
   *
   * @param type the color of the hexagon (BLACK, WHITE, or EMPTY)
   */
  public void setType(CellType type) {
    this.type = type;
  }

  /**
   * Returns the CellType the hexagon is.
   *
   * @return the type of cell the hexagon is (BLACK, WHITE, or EMPTY).
   */
  public CellType returnType() {
    return this.type;
  }

  /**
   * Returns the column coordinate of the hexagon.
   *
   * @return the column coordinate of the hexagon.
   */
  public int returnQ() {
    return this.q;
  }

  /**
   * Returns the row coordinate of the hexagon.
   *
   * @return the row coordinate of the hexagon.
   */
  public int returnR() {
    return this.r;
  }
}