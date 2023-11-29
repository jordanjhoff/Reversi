package cs3500.reversi.view;

import java.util.Objects;

import javax.swing.*;

import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.TeamColor;

/**
 * The graphical user interface view for a Reversi game. Extends JFrame and implements IReversiView.
 * It displays the game using a JReversiPanel and allows for user interaction.
 */
public class ReversiGUIView extends JFrame implements IReversiView {

  private final JReversiPanel mainPanel;


  /**
   * Constructs a ReversiGUIView with the given ReadonlyReversiModel.
   *
   * @param model The ReadonlyReversiModel representing the game state.
   */
  public ReversiGUIView(ReadonlyReversiModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.mainPanel = new JReversiPanel(model);
    this.add(this.mainPanel);
    this.pack();
  }

  @Override
  public void setVisible(boolean show) {
    super.setVisible(show);
  }


  @Override
  public void renderView(TeamColor color) {
    this.repaint();
    this.mainPanel.thisPlayer = color;
  }

  @Override
  public void addFeatureListener(IViewFeatures features) {
    this.mainPanel.addFeatureListener(Objects.requireNonNull(features));
  }

  @Override
  public void enableMoves(boolean enable) {
    this.mainPanel.enableMoves = enable;
    this.repaint();
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(mainPanel, message);
  }

}
