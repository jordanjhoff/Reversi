package cs3500.reversi.view;

import javax.swing.JFrame;

import cs3500.reversi.model.ReadonlyReversiModel;

public class ReversiGUIView extends JFrame implements IReversiView {

  private final JReversiPanel panel;

  public ReversiGUIView(ReadonlyReversiModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new JReversiPanel(model);
    this.add(panel);
    this.pack();
  }


  @Override
  public void addFeatureListener(IViewFeatures features) {

  }
}
