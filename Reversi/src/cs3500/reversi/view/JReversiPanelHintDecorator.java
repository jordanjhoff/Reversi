package cs3500.reversi.view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import cs3500.reversi.model.ReadonlyReversiModel;


public class JReversiPanelHintDecorator extends JReversiPanel   {

  private boolean hints;

  /**
   * Constructs a new JReversiPanel with the specified ReadonlyReversiModel.
   *
   * @param model
   * @throws IllegalArgumentException iff the provided model is null
   */
  public JReversiPanelHintDecorator(ReadonlyReversiModel model) {
    super(model);
    this.hints = true;
    this.addKeyListener(new DecoratedKeyListener());
  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.hints && selectedHex.isPresent()) {
      Point center = hexToPixel(selectedHex.get().getFirstCoordinate(), selectedHex.get().getSecondCoordinate());
      g.drawString(gameState.flipCount(selectedHex.get()) + "",
              center.x - (int) (this.hexagonSize * .3),
              center.y - (int) (this.hexagonSize * .3));
    }
  }

  /**
   * Private inner class for handling key events within the Reversi panel.
   */
  private class DecoratedKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      super.keyPressed(e);
      if (e.getKeyChar() == 'h') {
        hints = !hints;
        repaint();
      }

    }
  }
}
