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
    model = new HexReversi(3);
    five = new HexReversi(5);
  }


}
