package cs3500.reversi.provider.view;

import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.MoveFeatures;

/**
 * A class to adapt our provider's view to our ReversiView interface.
 */
public class AdaptedProviderView implements IReversiView {

  private IView adaptee;

  public AdaptedProviderView(IView adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void setVisible(boolean show) {
    //does nothing
  }

  @Override
  public void renderView(TeamColor color) {
    adaptee.updatePanel();
  }

  @Override
  public void addFeatureListener(MoveFeatures features) {
    adaptee.addFeatures(new AdaptedProviderFeatures(features));
  }

  @Override
  public void enableMoves(boolean enable) {
    //does nothing
  }

  @Override
  public void displayMessage(String message) {
    adaptee.showMessage(message);
  }
}
