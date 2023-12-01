package cs3500.reversi.view;

import java.awt.Polygon;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.event.MouseInputAdapter;
import javax.swing.JPanel;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.TeamColor;

import static java.lang.Math.sqrt;

/**
 * JReversiPanel is a Swing JPanel implementation designed for rendering and interacting with a
 * Reversi game board. It includes features for displaying the game state, handling user input
 * and providing board updates for moves made and selected cells. This panel relies on a
 * ReadonlyReversiModel. Users can click on cells to select them, and key presses trigger specific
 * actions, such as making a move or passing.
 **/
public class JReversiPanel extends JPanel {

  //The radius of the Reversi game board.
  private int modelRadius;

  //The ReadonlyReversiModel instance providing game state information.
  protected ReadonlyReversiModel gameState;


  //The currently selected hexagon position.
  private Optional<HexPosition> selectedHex;

  //The size of each hexagon cell.
  private int hexagonSize;

  //determines if clicking and making moves are allowed
  protected boolean enableMoves;

  //represents the player's color of this view
  protected TeamColor thisPlayer;


  private final List<MoveFeatures> featuresListeners;


  /**
   * Constructs a new JReversiPanel with the specified ReadonlyReversiModel.
   *
   * @throws IllegalArgumentException iff the provided model is null
   */
  public JReversiPanel(ReadonlyReversiModel model) {
    MouseListener mouselistener = new MyMouseListener();
    KeyListener keylistener = new MyKeyListener();
    this.featuresListeners = new ArrayList<>();
    this.addMouseListener(mouselistener);
    this.addKeyListener(keylistener);
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.selectedHex = Optional.empty();
    this.enableMoves = false;
    this.gameState = model;
    this.modelRadius = model.getSize();
  }

  /**
   * Creates a hexagon polygon centered at the specified point with the current hexagon size.
   *
   * @param center the center point of the hexagon
   * @return the hexagon polygon
   */
  private Polygon createHexagon(Point center) {
    Polygon polygon = new Polygon();

    for (int i = 0; i < 6; i++) {
      int xval = (int) (center.x + this.hexagonSize * Math.cos((Math.PI / 6) + (i * Math.PI / 3)));
      int yval = (int) (center.y + this.hexagonSize * Math.sin((Math.PI / 6) + (i * Math.PI / 3)));
      polygon.addPoint(xval, yval);
    }
    return polygon;
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 600x600 pixels.
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 600);
  }

  /**
   * Paints the component by rendering hexagonal cells based on the game state.
   *
   * @param g The Graphics context.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    ArrayList<HexPosition> currValidMoves = gameState.getValidMoves();
    //scales hexagon size based on bounds
    this.hexagonSize = Math.min(this.getWidth() / (5 * this.modelRadius),
            this.getHeight() / (5 * this.modelRadius));

    Graphics2D g2d = (Graphics2D) g.create();
    drawCurrentPlayer(g2d);
    //iterates through the board, top to bottom, left to right
    for (int r = -this.modelRadius; r <= this.modelRadius; r++) {
      int rMin = Math.max(-this.modelRadius, -r - this.modelRadius);
      int rMax = Math.min(this.modelRadius, -r + this.modelRadius);

      for (int q = rMin; q <= rMax; q++) {
        drawCell(g2d, q, r, currValidMoves);

      }
    }
  }

  /**
   * Draws a single hexagonal cell based on the game state.
   * @param g2d The graphics object to draw the cell on.
   * @param q The given cell's q coordinate.
   * @param r The given cell's r coordinate.
   * @param validMoves the currently available valid moves
   */
  private void drawCell(Graphics2D g2d, int q, int r, ArrayList<HexPosition> validMoves) {

    HexPosition currPosn = new HexPosition(q,r,-(q + r));
    Point currPoint = hexToPixel(q,r);
    Polygon hexagon = createHexagon(currPoint);
    TeamColor piece = gameState.getPieceAt(currPosn);
    //change color to grey if selected
    if (selectedHex.isPresent() && currPosn.equals(selectedHex.get()) && piece == null) {
      g2d.setColor(Color.gray);
    }
    else {
      g2d.setColor(new Color(255,204,0));
    }
    g2d.fillPolygon(hexagon);
    g2d.setColor(Color.black);
    g2d.drawPolygon(hexagon);

    if (piece != null) {
      Color team = piece.equals(TeamColor.WHITE)
              ? Color.white : Color.black;
      g2d.setColor(team);
      drawCenteredPiece(g2d,currPoint.x,currPoint.y, (int)(this.hexagonSize * 1.2));
    }
    else if (validMoves.contains(currPosn) && enableMoves) {
      //adds a small yellow indicator to indicate legal moves
      g2d.setColor(Color.yellow);
      drawCenteredPiece(g2d,currPoint.x,currPoint.y, (int)(this.hexagonSize * .3));
    }
  }


  /**
   * Draws a centered piece (circle) within the given Graphics2D context.
   *
   * @param g2d   The Graphics2D context.
   * @param x     The x-coordinate of the center.
   * @param y     The y-coordinate of the center.
   * @param size  The size of the piece.
   */
  private void drawCenteredPiece(Graphics2D g2d, int x, int y, int size) {
    x = x - (size / 2);
    y = y - (size / 2);
    g2d.fillOval(x, y, size, size);
  }

  /**
   * Prints the current player at the top left of the board.
   *
   * @param g2d The graphics object to draw the cell on.
   */
  private void drawCurrentPlayer(Graphics2D g2d) {
    if (thisPlayer != null) {
      g2d.drawString("Player: " + thisPlayer,10,10);
    }
  }


  /**
   * Converts an x and a y to the nearest hexagonal position. Implementation inspiration taken
   * from https://www.redblobgames.com/grids/hexagons/.
   * @param x the x coordinate
   * @param y the y coordinate
   * @return the corresponding HexPosition
   */
  private HexPosition pixelToHex(int x, int y) {
    double q = (sqrt(3) / 3 * (x - this.getWidth() / 2)  -  1. / 3 * (y - this.getHeight() / 2)) /
            this.hexagonSize;
    double r = (2. / 3 * (y - this.getHeight() / 2)) / this.hexagonSize;

    return roundHex(q,r);
  }

  /**
   * Rounds an x and a y to the nearest hexagonal position. Implementation inspiration taken
   * from https://www.redblobgames.com/grids/hexagons/.
   * @param x the x coordinate as a double
   * @param y the y coordinate as a double
   * @return the corresponding HexPosition
   */
  private HexPosition roundHex(double x, double y) {
    int xgrid = (int) Math.round(x);
    int ygrid = (int) Math.round(y);

    x -= xgrid;
    y -= ygrid;

    // remainder
    int dx = (int) Math.round(x + 0.5 * y) * (x * x >= y * y ? 1 : 0);
    int dy = (int) Math.round(y + 0.5 * x) * (x * x < y * y ? 1 : 0);
    return new HexPosition(xgrid + dx,ygrid + dy, -(xgrid + dx + ygrid + dy));
  }

  /**
   * Converts a hexagonal q,r coordinate to a pixel coordinate. Implementation inspiration taken
   * from https://www.redblobgames.com/grids/hexagons/.
   * @param q hexagon position q
   * @param r hexagon position r
   * @return the corrisponding coordinate
   */
  private Point hexToPixel(int q, int r) {
    int x = this.getWidth() / 2 + (int)(this.hexagonSize * (sqrt(3) * q  +  sqrt(3) / 2 * r));
    int y = this.getHeight() / 2 + (int)(this.hexagonSize * (3. / 2 * r));

    return new Point(x,y);
  }

  public void addFeatureListener(MoveFeatures features) {
    this.featuresListeners.add(features);
  }

  /**
   * Private inner class for handling mouse events within the Reversi panel.
   */
  private class MyMouseListener extends MouseInputAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {

      //if the view is allowed to make moves
      if (enableMoves) {
        int x = e.getX();
        int y = e.getY();
        HexPosition clickedHex = pixelToHex(x,y);
        if (selectedHex.isPresent() && clickedHex.equals(selectedHex.get())) {
          selectedHex = Optional.empty();
        }
        else if (Math.abs(clickedHex.getQPosition()) <= modelRadius &&
                Math.abs(clickedHex.getRPosition()) <= modelRadius &&
                Math.abs(clickedHex.getSPosition()) <= modelRadius &&
                gameState.getPieceAt(clickedHex) == null) {
          selectedHex = Optional.of(clickedHex);
        }
        else {
          selectedHex = Optional.empty();
        }
        repaint();
      }
    }

  }

  /**
   * Private inner class for handling key events within the Reversi panel.
   */
  private class MyKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      //if the view is allowed to make moves
      if (enableMoves) {
        if (e.getKeyChar() == 'p') {
          for (MoveFeatures listener : JReversiPanel.this.featuresListeners) {
            listener.notifyPassTurn();
            selectedHex = Optional.empty();
          }
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER && selectedHex.isPresent()) {
          for (MoveFeatures listener : JReversiPanel.this.featuresListeners) {
            listener.notifyMakeMove(selectedHex.get());
          }
        }


      }

    }
  }
}
