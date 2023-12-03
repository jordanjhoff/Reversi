package cs3500.reversi.provider.view;

/**
 * Represents the interface for features specific to the model.
 */
public interface ModelFeatures {
  void invalidMove();

  void playMoveView();

  void switchTurn();
}
