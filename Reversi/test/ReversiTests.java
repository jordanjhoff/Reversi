import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReversiModel;


/**
 * Class for testing the hexreversi model implementation
 * of reversimodel.
 */
public class ReversiTests {

  ReversiModel model;
  ReversiModel five;

  @Before
  public void initData() {
    model = new HexReversi();
    five = new HexReversi();
    five.startGame(5);
  }


  @Test
  public void testStartGameInvalidRad() {
    initData();
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(1));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(-1));
  }

  @Test
  public void testStartGameStarted() {
    initData();
    Assert.assertThrows(IllegalStateException.class, () -> five.startGame(5));
  }

}
