import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.TeamColor;


/**
 * A class for testing the subcomponents for the ReversiModel class.
 */
public class ReversiSubcomponentTests {

  @Test
  public void testCycleColor() {
    Assert.assertEquals(TeamColor.BLACK, TeamColor.WHITE.cycle());
    Assert.assertEquals(TeamColor.WHITE, TeamColor.BLACK.cycle());
  }

  @Test
  public void testColorSymbol() {
    Assert.assertEquals("O", TeamColor.WHITE.symbol);
    Assert.assertEquals("X", TeamColor.BLACK.symbol);
  }

  @Test
  public void testHexPositionEquals() {
    Assert.assertEquals(new HexPosition(1,0,-1), new HexPosition(1,0,-1));
    Assert.assertEquals(new HexPosition(3,2,-5), new HexPosition(3,2,-5));
    Assert.assertEquals(new HexPosition(3,2,-5), new HexPosition(3,2,-5));

    Assert.assertNotEquals(new HexPosition(1,0,-1), new HexPosition(-1,0,1));
    Assert.assertNotEquals(new HexPosition(3,2,-5), new HexPosition(1,0,-1));
    Assert.assertNotEquals(new HexPosition(0,0,0), new HexPosition(3,2,-5));
    Assert.assertFalse(new HexPosition(0,0,0).equals(0));
  }

  @Test
  public void testHexPositionIAE() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexPosition(1,1,1));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexPosition(1,-11,3));
  }

  @Test
  public void testHexPositionGetters() {
    HexPosition posn = new HexPosition(1,1,-2);
    Assert.assertEquals(1, posn.getQPosition());
    Assert.assertEquals(1, posn.getRPosition());
    Assert.assertEquals(-2, posn.getSPosition());
  }

  @Test
  public void testHexPositionToString() {
    Assert.assertEquals("[1,0,-1]", new HexPosition(1,0,-1).toString());
    Assert.assertEquals("[3,2,-5]", new HexPosition(3,2,-5).toString());
    Assert.assertEquals("[0,0,0]", new HexPosition(0,0,0).toString());

  }




}
