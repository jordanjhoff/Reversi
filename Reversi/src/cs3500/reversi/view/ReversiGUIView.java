package cs3500.reversi.view;

import java.awt.*;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import cs3500.reversi.model.ReadonlyReversiModel;

/**
 * The graphical user interface view for a Reversi game. Extends JFrame and implements IReversiView.
 * It displays the game using a JReversiPanel and allows for user interaction.
 */
public class ReversiGUIView extends JFrame implements IReversiView {

  //the panel responsible for rendering the Reversi game board and handling user input
  private final JReversiPanel panel;

  /**
   * Constructs a ReversiGUIView with the given ReadonlyReversiModel.
   *
   * @param model The ReadonlyReversiModel representing the game state.
   */
  public ReversiGUIView(ReadonlyReversiModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new JReversiPanel(model);
    this.add(panel);
    this.pack();
  }

  /**
   * Sets the visibility of the GUI view.
   *
   * @param show  if {@code true}, makes the {@code Window} visible,
   * otherwise hides the {@code Window}.
   * If the {@code Window} and/or its owner
   * are not yet displayable, both are made displayable.  The
   * {@code Window} will be validated prior to being made visible.
   * If the {@code Window} is already visible, this will bring the
   * {@code Window} to the front.<p>
   * If {@code false}, hides this {@code Window}, its subcomponents, and all
   * of its owned children.
   * The {@code Window} and its subcomponents can be made visible again
   * with a call to {@code #setVisible(true)}.
   */
  @Override
  public void setVisible(boolean show) {
    super.setVisible(show);
  }
}
