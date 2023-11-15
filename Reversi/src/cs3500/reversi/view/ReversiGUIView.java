package cs3500.reversi.view;

import javax.swing.JFrame;

import cs3500.reversi.model.ReadonlyReversiModel;

/**
 * The graphical user interface view for a Reversi game. Extends JFrame and implements IReversiView.
 * It displays the game using a JReversiPanel and allows for user interaction.
 */
public class ReversiGUIView extends JFrame implements IReversiView {

  /**
   * Constructs a ReversiGUIView with the given ReadonlyReversiModel.
   *
   * @param model The ReadonlyReversiModel representing the game state.
   */
  public ReversiGUIView(ReadonlyReversiModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(new JReversiPanel(model));
    this.pack();
  }

  @Override
  public void setVisible(boolean show) {
    super.setVisible(show);
  }
}
