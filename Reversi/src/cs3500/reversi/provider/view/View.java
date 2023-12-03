package cs3500.reversi.provider.view;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.reversi.provider.model.ReadonlyReversiModel;


/**
 * The class which holds the view of the game Reversi. Visualizes the Graphics of Reversi through
 * the main class.
 */
public class View extends JFrame implements IView {
  ReadonlyReversiModel model; //-> no longer takes in the model
  Panel panel;

  /**
   * The constructor for the view of the game Reversi.
   * @param model the un-mutable model of Reversi.
   */
  public View(ReadonlyReversiModel model) {
    this.model = model;
    this.panel = new Panel(model);
    this.add(panel);

    this.setTitle("Reversi"); //sets the title of the frame
    //this.setLayout(new FlowLayout());
    this.setBackground(Color.DARK_GRAY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits out of the application
    this.setResizable(false); //prevent frame from being resized
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    this.addKeyListener(new ReversiKeyListener(panel, features));
    this.panel.addMouseListener(new ReversiMouseListener(panel));
  }

  public void showMessage(String s) {
    JOptionPane.showMessageDialog(this, s);
  }

  public void updatePanel() {
    this.repaint();
  }
}
