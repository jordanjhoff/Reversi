package cs3500.reversi.view;

import java.awt.Component;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.TeamColor;

/**
 * The graphical user interface view for a Reversi game. Extends JFrame and implements IReversiView.
 * It displays the game using a JReversiPanel and allows for user interaction.
 */
public class ReversiGUIView extends JFrame implements IReversiView {

  private final IReversiPanel mainPanel;

  /**
   * Constructs a ReversiGUIView with the given ReadonlyReversiModel.
   *
   * @param model The ReadonlyReversiModel representing the game state.
   */
  public ReversiGUIView(ReadonlyReversiModel model, boolean hints, boolean square) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    if (square) {
      this.mainPanel = new JSquareReversiPanel(model);
    }
    else if (hints && !square) {
      this.mainPanel = new JReversiPanelHintDecorator(model);
    }
    else {
      this.mainPanel = new JReversiPanel(model);
    }
    this.add((Component) this.mainPanel);
    this.pack();
  }

  @Override
  public void setVisible(boolean show) {
    super.setVisible(show);
  }


  @Override
  public void renderView(TeamColor color) {
    this.repaint();
    this.mainPanel.setPlayer(color);
  }

  @Override
  public void addFeatureListener(MoveFeatures features) {
    this.mainPanel.addFeatureListener(Objects.requireNonNull(features));
  }

  @Override
  public void enableMoves(boolean enable) {
    this.mainPanel.enableMoves(enable);
    this.repaint();
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog((Component)mainPanel, message);
  }

}
