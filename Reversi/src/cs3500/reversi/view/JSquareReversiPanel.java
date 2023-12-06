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
public class JSquareReversiPanel extends JPanel {

  //The radius of the Reversi game board.
  private int modelSize;

  //The ReadonlyReversiModel instance providing game state information.
  protected ReadonlyReversiModel gameState;


  //The currently selected hexagon position.
  private Optional<SquarePos> selectedPos;

  //The size of each hexagon cell.
  private int squareSize;

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
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    ArrayList<Position> currValidMoves = gameState.getValidMoves();
    //scales hexagon size based on bounds
    this.squareSize = Math.min(this.getWidth() / (5 * this.modelSize),
            this.getHeight() / (5 * this.modelSize));

    for (int r = 0; r < modelSize; r++) {



      for (int c = 0; c < modelSize; c++) {

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
  private void drawCell(Graphics2D g2d, int q, int r, ArrayList<Position> validMoves) {

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
    int x = 0;
    int y = 0;
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
