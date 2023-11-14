package cs3500.reversi.view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.Position;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.TeamColor;

import static java.lang.Math.sqrt;

public class JReversiPanel extends JPanel {


  public JReversiPanel(ReadonlyReversiModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    this.model = model;
    this.featuresListeners = new ArrayList<>();
    MouseListener mouselistener = new MyMouseListener();
    KeyListener keylistener = new MyKeyListener();
    this.addMouseListener(mouselistener);
    this.addKeyListener(keylistener);
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.modelRadius = model.getSize();
    HexPosition selectedHex = null;
  }

  private int modelRadius;
  private final ReadonlyReversiModel model;
  private final List<IViewFeatures> featuresListeners;

  private HexPosition selectedHex;

  private int hexagonSize;

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
   * for this panel.  Here, we set it to 400x400 pixels.
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 600);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Rectangle bounds = this.getBounds();
    this.hexagonSize = Math.min(bounds.width / (5 * this.modelRadius),
            bounds.height / (5 * this.modelRadius));


    Graphics2D g2d = (Graphics2D) g.create();

    for (int r = -this.modelRadius; r <= this.modelRadius; r++) {
      int row = Math.abs(r);
      int rMin = Math.max(-this.modelRadius, -r - this.modelRadius);
      int rMax = Math.min(this.modelRadius, -r + this.modelRadius);


      for (int q = rMin; q <= rMax; q++) {
        HexPosition currPosn = new HexPosition(q,r,-(q+r));
        Point currPoint = hexToPixel(q,r);
        Polygon hexagon = createHexagon(currPoint);
        Polygon outline = createHexagon(currPoint);
        TeamColor piece = model.getPieceAt(currPosn);
        if (currPosn.equals(selectedHex) && piece == null) {
          g2d.setColor(Color.gray);
        }
        else {
          g2d.setColor(new Color(255,204,0));
        }
        g2d.fillPolygon(hexagon);
        g2d.drawPolygon(hexagon);
        g2d.setColor(Color.black);
        g2d.drawPolygon(outline);

        if (piece != null) {
          Color team = piece.equals(TeamColor.WHITE)
                  ? Color.white : Color.black;
          g2d.setColor(team);
          drawCenteredPiece(g2d,currPoint.x,currPoint.y, (int)(this.hexagonSize * 1.2));
        }
        else if (model.getValidMoves().contains(currPosn)) {
          g2d.setColor(Color.yellow);
          drawCenteredPiece(g2d,currPoint.x,currPoint.y, (int)(this.hexagonSize * .3));
        }

      }
    }




  }

  private void drawCenteredPiece(Graphics2D g2d, int x, int y, int size) {
    x = x - (size/2);
    y = y - (size/2);
    g2d.fillOval(x, y, size, size);
  }



  private HexPosition pixelToHex(int x, int y) {
    Rectangle bounds = this.getBounds();
    double q = (sqrt(3)/3 * (x - bounds.width/2)  -  1./3 * (y - bounds.height/2)) / this.hexagonSize;
    double r = (2./3 * (y - bounds.height/2)) / this.hexagonSize;

    return roundHex(q,r);
  }

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

  private Point hexToPixel(int q, int r) {
    Rectangle bounds = this.getBounds();
    int x = bounds.width/2 + (int)(this.hexagonSize * (sqrt(3) * q  +  sqrt(3)/2 * r));
    int y = bounds.height/2 + (int)(this.hexagonSize * (3./2 * r));

    return new Point(x,y);
  }


  private class MyMouseListener extends MouseInputAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      HexPosition clickedHex = pixelToHex(x,y);
      if (clickedHex.equals(selectedHex)) {
        selectedHex = null;
      }
      else if (Math.abs(clickedHex.getQPosition()) <= modelRadius &&
              Math.abs(clickedHex.getRPosition()) <= modelRadius &&
              Math.abs(clickedHex.getSPosition()) <= modelRadius &&
              model.getPieceAt(clickedHex) == null) {
        selectedHex = clickedHex;
      }
      System.out.println("Selected cell " + selectedHex);
      repaint();
    }

  }

  private class MyKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyChar() == 'p') {
        System.out.println("Pass");

      }
      else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        System.out.println("Make move" + selectedHex);
      }
    }
  }
}
