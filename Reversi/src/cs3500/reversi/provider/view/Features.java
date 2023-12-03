package cs3500.reversi.provider.view;

/**
 * An interface that processes the requests made from the view into application specific methods.
 */

public interface Features {
  /**
   * play a move.
   */
  void playMove(int q, int r);

  /**
   * pass a move.
   */
  void passMove();
}
