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
    Assert.assertEquals("O", TeamColor.WHITE.getSymbol());
    Assert.assertEquals("X", TeamColor.BLACK.getSymbol());
  }

  @Test
  public void testHexPositionEquals() {
    Assert.assertEquals(new HexPosition(1,0), new HexPosition(1,0));
    Assert.assertEquals(new HexPosition(3,2), new HexPosition(3,2));
    Assert.assertEquals(new HexPosition(3,2), new HexPosition(3,2));

    Assert.assertNotEquals(new HexPosition(1,0), new HexPosition(-1,0));
    Assert.assertNotEquals(new HexPosition(3,2), new HexPosition(1,0));
    Assert.assertNotEquals(new HexPosition(0,0), new HexPosition(3,2));
    Assert.assertFalse(new HexPosition(0,0).equals(0));
  }

  @Test
  public void testHexPositionGetters() {
    HexPosition posn = new HexPosition(1,1);
    Assert.assertEquals(1, posn.getFirstCoordinate());
    Assert.assertEquals(1, posn.getSecondCoordinate());
  }

  @Test
  public void testHexPositionToString() {
    Assert.assertEquals("[1,0,-1]", new HexPosition(1,0).toString());
    Assert.assertEquals("[3,2,-5]", new HexPosition(3,2).toString());
    Assert.assertEquals("[0,0,0]", new HexPosition(0,0).toString());

  }




}
