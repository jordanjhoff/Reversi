package cs3500.reversi.model;

public interface IModelFeatures {

  void notifyUpdateView();

  void startGame();

  void notifyMessage(TeamColor color, String message);

  void notifyGameOver();

  void notifyCurrentTurn(TeamColor color);
}
