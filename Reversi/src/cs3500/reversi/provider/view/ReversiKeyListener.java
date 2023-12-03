package cs3500.reversi.provider.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import cs3500.reversi.provider.view.Panel;

/**
 * A Key listener for Reversi.
 */
public class ReversiKeyListener extends KeyAdapter {
  Panel panel;
  Features features;

  ReversiKeyListener(Panel panel, Features features) {
    this.panel = panel;
    this.features = features;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == '\n') {
      System.out.println("enter pressed");
      Hexagon hex = panel.selectedHex;
      features.playMove(hex.returnQ(), hex.returnR());
      panel.repaint();
    } else if (e.getKeyChar() == 'p') {
      System.out.println("p pressed");
      features.passMove();
    }
    panel.repaint();
  }
}
