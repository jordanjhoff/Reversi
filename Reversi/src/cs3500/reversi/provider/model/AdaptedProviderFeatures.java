package cs3500.reversi.provider.model;

import cs3500.reversi.model.ModelFeatures;
import cs3500.reversi.model.TeamColor;

public class AdaptedProviderFeatures implements ModelFeatures {

  private final cs3500.reversi.provider.view.ModelFeatures adaptee;

  // BOTH INTERFACES ARE CALLED MODELFEATURES

  //given THEIRS return OURS


  public AdaptedProviderFeatures(cs3500.reversi.provider.view.ModelFeatures features) {
    this.adaptee = features;
  }

  @Override
  public void notifyStartGame(TeamColor startingPlayer) {

  }

  @Override
  public void notifyMessage(TeamColor color, String message) {

  }

  @Override
  public void notifyGameOver() {

  }

  @Override
  public void notifyUpdatedGameState() {

  }

  @Override
  public void notifyAdvanceTurn(TeamColor currentTurn) {

  }
}
