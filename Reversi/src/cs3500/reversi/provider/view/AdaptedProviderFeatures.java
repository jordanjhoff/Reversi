package cs3500.reversi.provider.view;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.view.MoveFeatures;

public class AdaptedProviderFeatures implements Features {
  private MoveFeatures ourFeatures;
  public AdaptedProviderFeatures(MoveFeatures ourFeatures) {
    this.ourFeatures = ourFeatures;
  }

  @Override
  public void playMove(int q, int r) {
    ourFeatures.notifyMakeMove(new HexPosition(q, r, -q - r));
  }

  @Override
  public void passMove() {
    ourFeatures.notifyPassTurn();
  }
}
