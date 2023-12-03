package cs3500.reversi.provider.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import cs3500.reversi.provider.view.Panel;

/**
 * A mouse listener for Reversi.
 */
public class ReversiMouseListener extends MouseAdapter {
  Panel panel;

  ReversiMouseListener(Panel panel) {
    this.panel = panel;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    boolean withinBounds = false;
    for (Hexagon hex : panel.hexagons) {
      if (hex.contains(e.getX(), e.getY())) {
        if (hex.returnQ() == panel.selectedHex.returnQ()
                && hex.returnR() == panel.selectedHex.returnR()) {
          panel.selectedHex = new Hexagon(-100, -100, 0, 0);
          withinBounds = true;
        } else {
          System.out.println("[" + hex.returnQ() + ", " + hex.returnR() + "]");
          panel.selectedHex = hex;
          withinBounds = true;
        }
      }
    }
    if (!withinBounds) {
      panel.selectedHex = new Hexagon(-100, -100, 0, 0);
    }
    panel.repaint();
  }
}
