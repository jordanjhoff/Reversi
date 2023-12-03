package cs3500.reversi.provider.view;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cs3500.reversi.provider.model.CellType;
import cs3500.reversi.provider.model.ICell;
import cs3500.reversi.provider.model.ReadonlyReversiModel;

/**
 * A class which currently holds the panel that displays the game of Reversi.
 */
public class Panel extends JPanel implements IPanel {
  private final ReadonlyReversiModel model;
  protected List<Hexagon> hexagons;
  protected Hexagon selectedHex;

  /**
   * Represents the constructor of the Panel.
   *
   * @param model the unmutable model of Reversi used to represent the view of the game.
   */
  Panel(ReadonlyReversiModel model) {
    this.model = model;
    hexagons = new ArrayList<>();

    selectedHex = new Hexagon(-100, -100, 0, 0);

    int sizeX = model.getBoard().size() * 54;
    int sizeY = model.getBoard().size() * 47;
    this.setPreferredSize(new Dimension(sizeX, sizeY));
  }

  /**
   * Visualizes the game of Reversi with the Graphics class.
   *
   * @param g the <code>Graphics</code> context in which to paint.
   */
  protected void paintComponent(Graphics g) {
    this.hexagons.clear();
    Graphics2D g2D = (Graphics2D) g;
    g2D.setStroke(new BasicStroke(2));

    List<List<ICell>> board = this.model.getBoard();
    int xOffset = 27; //shifts the board to the right
    int yOffset = 30; //shifts the board down
    for (int i = 0; i < board.size(); i++) {
      xOffset = xOffset + (Math.abs(this.model.returnSize() - board.get(i).size()) * 27);
      for (int j = 0; j < board.get(i).size(); j++) {
        ICell cell = board.get(i).get(j);
        int[] coords = cell.getCoordinates();
        Hexagon hexagon = new Hexagon(j * 54 + xOffset, i * 46 + yOffset,
                coords[0], coords[1]);
        if (cell.returnType() != CellType.EMPTY) {
          hexagon.setType(cell.returnType());
        }
        this.hexagons.add(hexagon);

        drawHexagon(g2D, hexagon, Color.LIGHT_GRAY);
        drawHexagon(g2D, selectedHex, Color.CYAN);
      }
      xOffset = 27;
    }
    this.setBackground(Color.DARK_GRAY);
  }

  /**
   * Draws the starting ring for the game Reversi.
   *
   * @param g2D the Graphics2D of the panel.
   * @param hex a hexagon on the board of Reversi.
   * @param x   the x-coordinate of the hexagon.
   * @param y   the y-coordinate of the hexagon.
   */
  private void drawRing(Graphics2D g2D, Hexagon hex, double x, double y) {
    Shape circle;
    switch (hex.returnType()) {
      case BLACK:
        g2D.setColor(Color.BLACK);
        circle = new Ellipse2D.Double(x - 30.0 / 2, y - 30.0 / 2, 30, 30);
        g2D.draw(circle);
        g2D.fill(circle);
        break;
      case WHITE:
        g2D.setColor(Color.WHITE);
        circle = new Ellipse2D.Double(x - 30.0 / 2, y - 30.0 / 2, 30, 30);
        g2D.draw(circle);
        g2D.fill(circle);
        g2D.setColor(Color.BLACK);
        break;
      default:
        break;
    }
  }

  /**
   * Draws the hexagon of Reversi.
   *
   * @param g     the graphic of the hexagon.
   * @param hex   the hexagon to be drawn.
   * @param color the color of the hexagon (BLACK, WHITE, or EMPTY).
   */
  private void drawHexagon(Graphics2D g, Hexagon hex, Color color) {
    g.setColor(Color.BLACK);
    g.draw(hex);
    g.setColor(color);
    g.fill(hex);
    if (hex.returnType() != CellType.EMPTY) {
      drawRing(g, hex, hex.getBounds().getCenterX(), hex.getBounds().getCenterY());
    }
  }
}
