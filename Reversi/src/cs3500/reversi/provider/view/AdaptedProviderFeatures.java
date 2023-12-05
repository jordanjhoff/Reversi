package cs3500.reversi.provider.view;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.view.MoveFeatures;

/**
 * This class implements the Features interface, acting as an adapter for the MoveFeatures
 * interface. It allows for the translation from Features (our provider) to MoveFeatures
 * (our features).
 */
public class AdaptedProviderFeatures implements Features {

  private MoveFeatures ourFeatures;

  /**
   * A constructor to create an adapted Feature object.
   * @param ourFeatures our implementation of MoveFeatures
   */
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
