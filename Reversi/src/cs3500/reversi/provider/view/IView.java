package cs3500.reversi.provider.view;

/**
 * An interface for the view of the game Reversi.
 */
public interface IView {
  /**
   * Takes in the features from the Features interface for the view to decide what to do with them.
   *
   * @param features methods invoked from the Features interface.
   */
  void addFeatures(Features features);

  void showMessage(String s);

  void updatePanel();
}
