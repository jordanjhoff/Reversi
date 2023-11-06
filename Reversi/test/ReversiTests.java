import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;


/**
 * Class for testing the hexreversi model implementation
 * of reversimodel.
 */
public class ReversiTests {

  ReversiModel hex5;
  ReversiModel hex2;

  @Before
  public void init() {
    hex5 = new HexReversi(5);
    hex2 = new HexReversi(2);
  }

  //test getsize works
  @Test
  public void getSize() {
    Assert.assertEquals(5, hex5.getSize());
    Assert.assertEquals(2, hex2.getSize());
    Assert.assertEquals(100, new HexReversi(100).getSize());
  }

  //test the constructor doesn't throw on valid sizes
  @Test
  public void testHexReversiConstructor() {
    try {
      new HexReversi(5);
      new HexReversi(100);
      new HexReversi(23);

    }
    catch (Exception e) {
      Assert.fail("Test failed");
    }
  }

  //tests the constructor throws when incorrect sizes
  @Test
  public void testHexReversiConstructorIAE() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexReversi(1));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexReversi(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexReversi(-11));
  }

  //test getting a piece returns accurately
  @Test
  public void testGetPieceAt() {
    Assert.assertEquals(TeamColor.BLACK, hex5.getPieceAt(new HexPosition(0,-1,1)));
    Assert.assertEquals(TeamColor.WHITE, hex5.getPieceAt(new HexPosition(1,-1,0)));
    Assert.assertEquals(TeamColor.BLACK, hex5.getPieceAt(new HexPosition(1,0,-1)));
    Assert.assertEquals(TeamColor.WHITE, hex5.getPieceAt(new HexPosition(0,1,-1)));
  }

  //test getting a piece that isn't there returns null
  @Test
  public void testGetPieceAt2() {
    Assert.assertNull(hex5.getPieceAt(new HexPosition(-4,4,0)));
    Assert.assertNull(hex5.getPieceAt(new HexPosition(1,4,-5)));
  }

  //test getting a piece out of bounds throws IAE
  @Test
  public void testGetPieceAtIAE() {
    Assert.assertThrows(IllegalArgumentException.class, () -> hex2.getPieceAt(
            new HexPosition(-4,4,0)));
    Assert.assertThrows(IllegalArgumentException.class, () -> hex2.getPieceAt(
            new HexPosition(1,4,-5)));
  }

  //test getting current turn is correct
  @Test
  public void getCurrentTurn() {
    Assert.assertEquals(TeamColor.BLACK, hex5.getCurrentTurn());
    hex5.pass();
    Assert.assertEquals(TeamColor.WHITE, hex5.getCurrentTurn());
  }

  //test getting current turn is correct
  @Test
  public void getCurrentTurn2() {
    Assert.assertEquals(TeamColor.BLACK, hex5.getCurrentTurn());
    hex5.addPiece(new HexPosition(1,-2,1));
    Assert.assertEquals(TeamColor.WHITE, hex5.getCurrentTurn());
  }

  //test get current turn for finished game throws ISE
  @Test
  public void getCurrentTurnISE() {
    hex5.pass();
    hex5.pass();
    Assert.assertThrows(IllegalStateException.class, () -> hex5.getCurrentTurn());
  }

  //test pass advances the turn
  @Test
  public void testPass() {
    try {
      Assert.assertEquals(TeamColor.BLACK, hex5.getCurrentTurn());
      hex5.pass();
      Assert.assertEquals(TeamColor.WHITE, hex5.getCurrentTurn());
    }
    catch (Exception e) {
      Assert.fail("Test failed");
    }
  }

  //test pass throws ISE when game over
  @Test
  public void testPassISE() {
    hex5.pass();
    hex5.pass();
    Assert.assertThrows(IllegalStateException.class, () -> hex5.pass());
  }

  //test winner is correct
  @Test
  public void testGetWinner() {
    hex5.addPiece(new HexPosition(1,-2,1));
    hex5.pass();
    hex5.pass();
    Assert.assertEquals(TeamColor.BLACK, hex5.getWinner());
  }

  //test winner returns null if number of pieces are the same
  @Test
  public void testGetWinner2() {
    hex5.pass();
    hex5.pass();
    Assert.assertNull(hex5.getWinner());
  }

  //test winner is correct after full game
  @Test
  public void testGetWinner3() {
    hex2.addPiece(new HexPosition(1,-2,1));
    hex2.addPiece(new HexPosition(2,-1,-1));
    hex2.addPiece(new HexPosition(1,1,-2));
    hex2.addPiece(new HexPosition(-1,2,-1));
    hex2.addPiece(new HexPosition(-2,1,1));
    hex2.addPiece(new HexPosition(-1,-1,2));
    Assert.assertEquals(TeamColor.WHITE, hex2.getWinner());
  }


  //test winner throws if game not over
  @Test
  public void testGetWinnerISE() {
    hex5.addPiece(new HexPosition(1,-2,1));
    Assert.assertThrows(IllegalStateException.class, () -> hex5.getWinner());
  }

  //test game ends after two passes
  @Test
  public void testGameOver() {
    Assert.assertFalse(hex5.isGameOver());
    hex5.pass();
    Assert.assertFalse(hex5.isGameOver());
    hex5.pass();
    Assert.assertTrue(hex5.isGameOver());
  }

  //test game ends when no valid moves
  @Test
  public void testGameOver2() {
    hex2.addPiece(new HexPosition(1,-2,1));
    hex2.addPiece(new HexPosition(2,-1,-1));
    hex2.addPiece(new HexPosition(1,1,-2));
    hex2.addPiece(new HexPosition(-1,2,-1));
    hex2.addPiece(new HexPosition(-2,1,1));
    Assert.assertFalse(hex2.isGameOver());
    hex2.addPiece(new HexPosition(-1,-1,2));
    Assert.assertTrue(hex2.isGameOver());
  }

  //test valid moves are correct before adding any pieces (current turn is black)
  @Test
  public void testValidMoves() {
    Assert.assertEquals(6,hex5.getValidMoves().size());
    HashSet<HexPosition> expectedBlackValidMoves = new HashSet<>(Arrays.asList(
            new HexPosition(1,-2,1), new HexPosition(2,-1,-1),
            new HexPosition(1,1,-2), new HexPosition(-1,2,-1),
            new HexPosition(-2,1,1), new HexPosition(-1,-1,2)));
    Assert.assertEquals(expectedBlackValidMoves, new HashSet<>(hex5.getValidMoves()));
  }

  //test valid moves update correctly after adding pieces
  @Test
  public void testValidMoves2() {
    HashSet<HexPosition> validMovesBefore = new HashSet<>(hex5.getValidMoves());
    hex5.addPiece(new HexPosition(1,-2,1));
    //now its white's turn
    HashSet<HexPosition> expectedWhiteValidMoves = new HashSet<>(Arrays.asList(
            new HexPosition(2,-3,1), new HexPosition(-2,1,1),
            new HexPosition(-1,2,-1), new HexPosition(2,-1,-1)));
    Assert.assertNotEquals(validMovesBefore, new HashSet<>(hex5.getValidMoves()));
    Assert.assertEquals(4, hex5.getValidMoves().size());
    Assert.assertEquals(expectedWhiteValidMoves, new HashSet<>(hex5.getValidMoves()));
  }

  //test getValidMoves is empty if the game is over
  @Test
  public void testValidMoves3() {
    Assert.assertTrue(!hex5.getValidMoves().isEmpty());
    hex5.pass();
    hex5.pass();
    Assert.assertEquals(new ArrayList<>(), hex5.getValidMoves());
  }

  //test addPiece adds the correct color
  @Test
  public void testAddPiece() {
    //black's turn
    hex5.addPiece(new HexPosition(1,-2,1));
    Assert.assertEquals(TeamColor.BLACK, hex5.getPieceAt(new HexPosition(1,-2,1)));
    //white's turn
    hex5.addPiece(new HexPosition(2,-3,1));
    Assert.assertEquals(TeamColor.WHITE, hex5.getPieceAt(new HexPosition(2,-3,1)));
  }

  //test addPiece updates a single piece correctly
  @Test
  public void testAddPiece2() {
    hex5.addPiece(new HexPosition(1,-2,1));
    Assert.assertEquals(TeamColor.BLACK, hex5.getPieceAt(new HexPosition(1,-1,0)));
  }

  //test addPiece updates multiple pieces correctly
  @Test
  public void testAddPiece3() {
    //black's turn
    hex5.addPiece(new HexPosition(1,-2,1));
    //white's turn
    hex5.addPiece(new HexPosition(2,-3,1));
    Assert.assertEquals(TeamColor.WHITE, hex5.getPieceAt(new HexPosition(1,-2,1)));
    Assert.assertEquals(TeamColor.WHITE, hex5.getPieceAt(new HexPosition(0,-1,1)));
  }

  //test invalid addPiece doesn't change current turn
  @Test
  public void testAddPiece4() {
    //black's turn
    Assert.assertEquals(TeamColor.BLACK, hex5.getCurrentTurn());
    Assert.assertThrows(IllegalStateException.class,
        () -> hex5.addPiece(new HexPosition(2,-2,0)));
    Assert.assertEquals(TeamColor.BLACK, hex5.getCurrentTurn());
  }


  //test no more moves for one color but still for other must pass
  @Test
  public void testAddPiece5() {
    //black's turn
    hex2.addPiece(new HexPosition(1,-2,1));
    //white's turn
    hex2.addPiece(new HexPosition(2,-1,-1));
    //black's turn
    hex2.addPiece(new HexPosition(1,1,-2));
    //white's turn
    hex2.addPiece(new HexPosition(-1,2,-1));
    //black's turn
    hex2.pass();
    //white's turn
    hex2.addPiece(new HexPosition(-1,-1,2));
    //black's turn
    hex2.pass();
    //white's turn (no moves left)
    try {
      hex2.addPiece(new HexPosition(-2,2,0));
    }
    catch (Exception e) {
      Assert.assertEquals("No legal moves, player must pass", e.getMessage());
    }
  }

  //test addPiece with an out of bounds throws an IAE
  @Test
  public void testAddPieceIAE() {
    Assert.assertThrows(IllegalArgumentException.class, () -> hex2.addPiece(
            new HexPosition(-4,4,0)));
    Assert.assertThrows(IllegalArgumentException.class, () -> hex2.addPiece(
            new HexPosition(1,4,-5)));
  }

  //test addPiece with on an invalid empty board coordinate place throws ISE
  @Test
  public void testAddPieceISE() {
    Assert.assertThrows(IllegalStateException.class, () -> hex5.addPiece(
            new HexPosition(-4,4,0)));
    Assert.assertThrows(IllegalStateException.class, () -> hex5.addPiece(
            new HexPosition(1,4,-5)));
  }

  //test addPiece with on an invalid occupied board coordinate place throws ISE
  @Test
  public void testAddPieceISE2() {
    Assert.assertThrows(IllegalStateException.class, () -> hex5.addPiece(
            new HexPosition(0,-1,1)));
    Assert.assertThrows(IllegalStateException.class, () -> hex5.addPiece(
            new HexPosition(-1,1,0)));
  }

  //test model works with all moves
  @Test
  public void testFullGame() {
    //black's turn
    hex2.addPiece(new HexPosition(1,-2,1));
    //white's turn
    hex2.addPiece(new HexPosition(2,-1,-1));
    //black's turn
    hex2.addPiece(new HexPosition(1,1,-2));
    //white's turn
    hex2.pass();
    //black's turn
    Assert.assertThrows(IllegalStateException.class,
        () -> hex2.addPiece(new HexPosition(-2,2,0)));
    Assert.assertEquals(TeamColor.BLACK, hex2.getCurrentTurn());
    hex2.addPiece(new HexPosition(-1,-1,2));
    //white's turn
    hex2.addPiece(new HexPosition(-2,1,1));
    Assert.assertFalse(hex2.isGameOver());
    //black's turn
    hex2.addPiece(new HexPosition(-1,2,-1));
    //no more valid moves for either
    Assert.assertTrue(hex2.isGameOver());
  }






}
