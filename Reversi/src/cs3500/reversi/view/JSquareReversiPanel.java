package cs3500.reversi.view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.Position;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.SquarePos;
import cs3500.reversi.model.TeamColor;

/**
 * JReversiPanel is a Swing JPanel implementation designed for rendering and interacting with a
 * Reversi game board. It includes features for displaying the game state, handling user input
 * and providing board updates for moves made and selected cells. This panel relies on a
 * ReadonlyReversiModel. Users can click on cells to select them, and key presses trigger specific
 * actions, such as making a move or passing.
 **/
public class JSquareReversiPanel extends JPanel implements IReversiPanel {

  //The radius of the Reversi game board.
  protected final int modelSize;

  //The ReadonlyReversiModel instance providing game state information.
  protected ReadonlyReversiModel gameState;


  //The currently selected hexagon position.
  protected Optional<SquarePos> selectedPos;

  //The size of each hexagon cell.
  protected int squareSize;

  //determines if clicking and making moves are allowed
  protected boolean enableMoves;

  //represents the player's color of this view
  protected TeamColor thisPlayer;

  protected boolean hints;


  protected final List<MoveFeatures> featuresListeners;


  /**
   * Constructs a new JReversiPanel with the specified ReadonlyReversiModel.
   *
   * @throws IllegalArgumentException iff the provided model is null
   */
  public JSquareReversiPanel(ReadonlyReversiModel model) {
    MouseListener mouselistener = new MyMouseListener();
    KeyListener keylistener = new MyKeyListener();
    this.featuresListeners = new ArrayList<>();
    this.addMouseListener(mouselistener);
    this.addKeyListener(keylistener);
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.selectedPos = Optional.empty();
    this.enableMoves = false;
    this.gameState = model;
    this.modelSize = model.getSize();
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
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    ArrayList<Position> currValidMoves = gameState.getValidMoves();
    //scales hexagon size based on bounds
    this.squareSize = Math.min(this.getWidth() / (this.modelSize + 2),
            this.getHeight() / (this.modelSize + 2));
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(2));
    drawCurrentPlayer(g2d);
    for (int row = 0; row < modelSize; row++) {
      for (int col = 0; col < modelSize; col++) {

        drawCell(g2d, row, col, currValidMoves);
      }
    }

  }

  /**
   * Draws a single hexagonal cell based on the game state.
   * @param g2d The graphics object to draw the cell on.
   * @param r The given cell's q coordinate.
   * @param c The given cell's r coordinate.
   * @param validMoves the currently available valid moves
   */
  private void drawCell(Graphics2D g2d, int r, int c, ArrayList<Position> validMoves) {
    Point currPoint = squareToPixel(r, c);
    SquarePos currPosn = new SquarePos(r,c);



    TeamColor piece = gameState.getPieceAt(currPosn);
    //change color to grey if selected
    if (selectedPos.isPresent() && currPosn.equals(selectedPos.get()) && piece == null) {
      g2d.setColor(Color.gray);
    }
    else {
      g2d.setColor(new Color(255,204,0));
    }
    g2d.fillRect(currPoint.x - squareSize/2, currPoint.y - squareSize/2, squareSize, squareSize);
    g2d.setColor(Color.black);
    g2d.drawRect(currPoint.x - squareSize/2, currPoint.y - squareSize/2, squareSize, squareSize);

    if (piece != null) {
      Color team = piece.equals(TeamColor.WHITE)
              ? Color.white : Color.black;
      g2d.setColor(team);
      drawCenteredPiece(g2d, currPoint.x, currPoint.y, (int)(this.squareSize/1.5));
    }
    else if (validMoves.contains(currPosn) && enableMoves) {
      //adds a small yellow indicator to indicate legal moves
      g2d.setColor(Color.yellow);
      drawCenteredPiece(g2d, currPoint.x, currPoint.y, (int)(this.squareSize/4));

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

  private Point squareToPixel(int r, int c) {
    int x = c * squareSize + (int)(squareSize*1.5);
    int y = r * squareSize + (int)(squareSize*1.5);
    return new Point(x,y);
  }

  private SquarePos pixelToSquare(int x, int y) {
    if (y - squareSize < 0 || x - squareSize < 0) {
      return new SquarePos(-1,-1);
    }
    int r = (y - squareSize) / squareSize;
    int c = (x - squareSize) / squareSize;
    return new SquarePos(r, c);
  }



  public void addFeatureListener(MoveFeatures features) {
    this.featuresListeners.add(features);
  }

  @Override
  public void setPlayer(TeamColor player) {
    this.thisPlayer = player;
  }

  @Override
  public void enableMoves(boolean enable) {
    this.enableMoves = enable;
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
        SquarePos clickedPos = pixelToSquare(x,y);
        if (selectedPos.isPresent() && clickedPos.equals(selectedPos.get())) {
          selectedPos = Optional.empty();
        }
        else if (clickedPos.getFirstCoordinate() < modelSize &&
                clickedPos.getSecondCoordinate() < modelSize &&
                clickedPos.getFirstCoordinate() >= 0 &&
                clickedPos.getSecondCoordinate() >= 0 &&
                gameState.getPieceAt(clickedPos) == null) {
          selectedPos = Optional.of(clickedPos);
        }
        else {
          selectedPos = Optional.empty();
        }
        repaint();
      }

    }
  }

  private class MyKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      //if the view is allowed to make moves

      if (enableMoves) {
        if (e.getKeyChar() == 'p') {
          for (MoveFeatures listener : JSquareReversiPanel.this.featuresListeners) {
            listener.notifyPassTurn();
            selectedPos = Optional.empty();
          }
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER && selectedPos.isPresent()) {
          for (MoveFeatures listener : JSquareReversiPanel.this.featuresListeners) {
            listener.notifyMakeMove(selectedPos.get());
          }
        }


      }

    }
  }
}
