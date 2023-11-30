import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import cs3500.reversi.controller.AIPlayer;
import cs3500.reversi.controller.HexReversiController;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerMock;
import cs3500.reversi.controller.VisualController;
import cs3500.reversi.controller.VisualControllerMock;
import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.ReversiGUIView;
import cs3500.reversi.view.ReversiGUIViewMock;

/**
 * Class for testing the hexreversi model implementation of reversimodel.
 */
public class MockTests {

  ReversiModel hex5;
  ReversiModel hex2;

  ReadonlyReversiModel hex2read;

  ReadonlyReversiModel hex5read;

  Player player1;
  Player player2;

  Appendable out;
  Appendable out2;

  Player mock1;
  Player mock2;

  IReversiView view1mock;
  IReversiView view2mock;

  HexReversiController controller1;
  HexReversiController controllerMock1;

  @Before
  public void init() {
    hex5 = new HexReversi(5);
    hex2 = new HexReversi(2);
    hex2read = new ReadonlyHexReversiModel(hex2);
    hex5read = new ReadonlyHexReversiModel(hex5);

    // for a hexgame of size 5

    out = new StringBuilder();
    mock1 = new PlayerMock(out, new AIPlayer(TeamColor.BLACK, new CaptureMost(), hex5read));
    mock2 = new PlayerMock(out, new AIPlayer(TeamColor.WHITE, new CaptureMost(), hex5read));


    view1mock = new ReversiGUIViewMock(out);
    view2mock = new ReversiGUIViewMock(out);

    controller1 = new VisualController(hex5, view1mock, mock1);
    controllerMock1 = new VisualControllerMock(out, controller1);

    HexReversiController controller2 = new VisualController(hex5, view2mock, mock2);
    HexReversiController controllerMock2 = new VisualControllerMock(out, controller2);

    hex5.startGame();

    //for a hexgame of size 2

    out2 = new StringBuilder();
    Player mockTwo1 = new PlayerMock(out, new AIPlayer(TeamColor.BLACK, new CaptureMost(), hex2read));
    Player mockTwo2 = new PlayerMock(out, new AIPlayer(TeamColor.WHITE, new CaptureMost(), hex2read));


    IReversiView viewTwo1mock = new ReversiGUIViewMock(out2);

    IReversiView viewTwo2mock = new ReversiGUIViewMock(out2);

    HexReversiController controllerTwo1 = new VisualController(hex2, viewTwo1mock, mockTwo1);
    HexReversiController controllerMockTwo1 = new VisualControllerMock(out2, controllerTwo1);

    HexReversiController controllerTwo2 = new VisualController(hex2, viewTwo2mock, mockTwo2);
    HexReversiController controllerMockTwo2 = new VisualControllerMock(out2, controllerTwo2);

    hex2.startGame();
  }

  //test getsize works
  @Test
  public void getSize() {
    Assert.assertEquals(5, hex5.getSize());
    Assert.assertEquals(2, hex2.getSize());
    Assert.assertEquals(5, hex5read.getSize());
    Assert.assertEquals(2, hex2read.getSize());
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
    Assert.assertEquals(TeamColor.BLACK, hex5read.getCurrentTurn());
    hex5.pass();
    Assert.assertEquals(TeamColor.WHITE, hex5.getCurrentTurn());
    Assert.assertEquals(TeamColor.WHITE, hex5read.getCurrentTurn());
  }

  //test getting current turn is correct
  @Test
  public void getCurrentTurn2() {
    Assert.assertEquals(TeamColor.BLACK, hex5.getCurrentTurn());
    Assert.assertEquals(TeamColor.BLACK, hex5read.getCurrentTurn());
    hex5.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    Assert.assertEquals(TeamColor.WHITE, hex5.getCurrentTurn());
    Assert.assertEquals(TeamColor.WHITE, hex5read.getCurrentTurn());
  }

  //test get current turn for finished game throws ISE
  @Test
  public void getCurrentTurnISE() {
    hex5.pass();
    hex5.pass();
    Assert.assertThrows(IllegalStateException.class, () -> hex5.getCurrentTurn());
    Assert.assertThrows(IllegalStateException.class, () -> hex5read.getCurrentTurn());
  }

  //test pass advances the turn
  @Test
  public void testPass() {
    try {
      Assert.assertEquals(TeamColor.BLACK, hex5.getCurrentTurn());
      Assert.assertEquals(TeamColor.BLACK, hex5read.getCurrentTurn());
      hex5.pass();
      Assert.assertEquals(TeamColor.WHITE, hex5.getCurrentTurn());
      Assert.assertEquals(TeamColor.WHITE, hex5read.getCurrentTurn());
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
    hex5.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    hex5.pass();
    hex5.pass();
    Assert.assertEquals(TeamColor.BLACK, hex5.getWinner());
    Assert.assertEquals(TeamColor.BLACK, hex5read.getWinner());
  }

  //test winner returns null if number of pieces are the same
  @Test
  public void testGetWinner2() {
    hex5.pass();
    hex5.pass();
    Assert.assertNull(hex5.getWinner());
    Assert.assertNull(hex5read.getWinner());
  }

  //test winner is correct after full game of legam moves, where one player has more pieces
  // than another
  @Test
  public void testGetWinner3() {
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    hex2.addPiece(TeamColor.WHITE, new HexPosition(2,-1,-1));
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,1,-2));
    hex2.addPiece(TeamColor.WHITE, new HexPosition(-1,2,-1));
    hex2.addPiece(TeamColor.BLACK, new HexPosition(-2,1,1));
    hex2.addPiece(TeamColor.WHITE, new HexPosition(-1,-1,2));
    Assert.assertEquals(TeamColor.WHITE, hex2.getWinner());
    Assert.assertEquals(TeamColor.WHITE, hex2read.getWinner());
  }


  //test winner throws if game not over
  @Test
  public void testGetWinnerISE() {
    hex5.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    Assert.assertThrows(IllegalStateException.class, () -> hex5.getWinner());
    Assert.assertThrows(IllegalStateException.class, () -> hex5read.getWinner());
  }

  //test game ends after two passes
  @Test
  public void testGameOver() {
    Assert.assertFalse(hex5.isGameOver());
    Assert.assertFalse(hex5read.isGameOver());
    hex5.pass();
    Assert.assertFalse(hex5.isGameOver());
    Assert.assertFalse(hex5read.isGameOver());
    hex5.pass();
    Assert.assertTrue(hex5.isGameOver());
    Assert.assertTrue(hex5read.isGameOver());
  }

  //test game ends when no valid moves
  @Test
  public void testGameOver2() {
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    hex2.addPiece(TeamColor.WHITE, new HexPosition(2,-1,-1));
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,1,-2));
    hex2.addPiece(TeamColor.WHITE, new HexPosition(-1,2,-1));
    hex2.addPiece(TeamColor.BLACK, new HexPosition(-2,1,1));
    Assert.assertFalse(hex2.isGameOver());
    Assert.assertFalse(hex2read.isGameOver());
    hex2.addPiece(TeamColor.WHITE, new HexPosition(-1,-1,2));
    Assert.assertTrue(hex2.isGameOver());
    Assert.assertTrue(hex2read.isGameOver());
  }

  //test valid moves are correct before adding any pieces (current turn is black)
  @Test
  public void testValidMoves() {
    Assert.assertEquals(6,hex5.getValidMoves().size());
    Assert.assertEquals(6,hex5read.getValidMoves().size());
    HashSet<HexPosition> expectedBlackValidMoves = new HashSet<>(Arrays.asList(
            new HexPosition(1,-2,1), new HexPosition(2,-1,-1),
            new HexPosition(1,1,-2), new HexPosition(-1,2,-1),
            new HexPosition(-2,1,1), new HexPosition(-1,-1,2)));
    Assert.assertEquals(expectedBlackValidMoves, new HashSet<>(hex5.getValidMoves()));
    Assert.assertEquals(expectedBlackValidMoves, new HashSet<>(hex5read.getValidMoves()));
  }

  //test valid moves update correctly after adding pieces
  @Test
  public void testValidMoves2() {
    HashSet<HexPosition> validMovesBefore = new HashSet<>(hex5.getValidMoves());
    hex5.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    //now its white's turn
    HashSet<HexPosition> expectedWhiteValidMoves = new HashSet<>(Arrays.asList(
            new HexPosition(2,-3,1), new HexPosition(-2,1,1),
            new HexPosition(-1,2,-1), new HexPosition(2,-1,-1)));
    Assert.assertNotEquals(validMovesBefore, new HashSet<>(hex5.getValidMoves()));
    Assert.assertNotEquals(validMovesBefore, new HashSet<>(hex5read.getValidMoves()));
    Assert.assertEquals(4, hex5.getValidMoves().size());
    Assert.assertEquals(expectedWhiteValidMoves, new HashSet<>(hex5.getValidMoves()));
    Assert.assertEquals(expectedWhiteValidMoves, new HashSet<>(hex5read.getValidMoves()));
  }

  //test getValidMoves is empty if the game is over
  @Test
  public void testValidMoves3() {
    Assert.assertTrue(!hex5.getValidMoves().isEmpty());
    Assert.assertTrue(!hex5read.getValidMoves().isEmpty());
    hex5.pass();
    hex5.pass();
    Assert.assertEquals(new ArrayList<>(), hex5.getValidMoves());
    Assert.assertEquals(new ArrayList<>(), hex5read.getValidMoves());
  }

  //test addPiece adds the correct color
  @Test
  public void testAddPiece() {
    //black's turn
    hex5.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    Assert.assertEquals(TeamColor.BLACK, hex5.getPieceAt(new HexPosition(1,-2,1)));
    Assert.assertEquals(TeamColor.BLACK, hex5read.getPieceAt(new HexPosition(1,-2,1)));
    //white's turn
    hex5.addPiece(TeamColor.WHITE, new HexPosition(2,-3,1));
    Assert.assertEquals(TeamColor.WHITE, hex5.getPieceAt(new HexPosition(2,-3,1)));
    Assert.assertEquals(TeamColor.WHITE, hex5read.getPieceAt(new HexPosition(2,-3,1)));
  }

  //test addPiece updates a single piece correctly
  @Test
  public void testAddPiece2() {
    hex5.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    Assert.assertEquals(TeamColor.BLACK, hex5.getPieceAt(new HexPosition(1,-1,0)));
    Assert.assertEquals(TeamColor.BLACK, hex5read.getPieceAt(new HexPosition(1,-1,0)));
  }

  //test addPiece updates multiple pieces correctly
  @Test
  public void testAddPiece3() {
    //black's turn
    hex5.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    //white's turn
    hex5.addPiece(TeamColor.WHITE, new HexPosition(2,-3,1));
    Assert.assertEquals(TeamColor.WHITE, hex5.getPieceAt(new HexPosition(1,-2,1)));
    Assert.assertEquals(TeamColor.WHITE, hex5.getPieceAt(new HexPosition(0,-1,1)));
    Assert.assertEquals(TeamColor.WHITE, hex5read.getPieceAt(new HexPosition(1,-2,1)));
    Assert.assertEquals(TeamColor.WHITE, hex5read.getPieceAt(new HexPosition(0,-1,1)));
  }

  //test invalid addPiece doesn't change current turn
  @Test
  public void testAddPiece4() {
    //black's turn
    Assert.assertEquals(TeamColor.BLACK, hex5.getCurrentTurn());
    Assert.assertEquals(TeamColor.BLACK, hex5read.getCurrentTurn());
    Assert.assertThrows(IllegalStateException.class,
            () -> hex5.addPiece(TeamColor.BLACK, new HexPosition(2,-2,0)));
    Assert.assertEquals(TeamColor.BLACK, hex5.getCurrentTurn());
    Assert.assertEquals(TeamColor.BLACK, hex5read.getCurrentTurn());

  }


  //test no more moves for one color but still for other must pass
  @Test
  public void testAddPiece5() {
    //black's turn
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    //white's turn
    hex2.addPiece(TeamColor.WHITE, new HexPosition(2,-1,-1));
    //black's turn
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,1,-2));
    //white's turn
    hex2.addPiece(TeamColor.WHITE, new HexPosition(-1,2,-1));
    //black's turn
    hex2.pass();
    //white's turn
    hex2.addPiece(TeamColor.WHITE, new HexPosition(-1,-1,2));
    //black's turn
    hex2.pass();
    //white's turn (no moves left)
    try {
      hex2.addPiece(TeamColor.WHITE, new HexPosition(-2,2,0));
    }
    catch (Exception e) {
      Assert.assertEquals("No valid moves. You must pass", e.getMessage());
    }
  }

  //test addPiece with an out of bounds throws an IAE
  @Test
  public void testAddPieceIAE() {
    Assert.assertThrows(IllegalArgumentException.class, () -> hex2.addPiece(TeamColor.BLACK,
            new HexPosition(-4,4,0)));
    Assert.assertThrows(IllegalArgumentException.class, () -> hex2.addPiece(TeamColor.BLACK,
            new HexPosition(1,4,-5)));
  }

  //test addPiece with on an invalid empty board coordinate place throws ISE
  @Test
  public void testAddPieceISE() {
    Assert.assertThrows(IllegalStateException.class, () -> hex5.addPiece(TeamColor.BLACK,
            new HexPosition(-4,4,0)));
    Assert.assertThrows(IllegalStateException.class, () -> hex5.addPiece(TeamColor.BLACK,
            new HexPosition(1,4,-5)));
  }

  //test addPiece with on an invalid occupied board coordinate place throws ISE
  @Test
  public void testAddPieceISE2() {
    Assert.assertThrows(IllegalStateException.class, () -> hex5.addPiece(TeamColor.BLACK,
            new HexPosition(0,-1,1)));
    Assert.assertThrows(IllegalStateException.class, () -> hex5.addPiece(TeamColor.BLACK,
            new HexPosition(-1,1,0)));
  }

  //test addPiece with same player making a move twice
  @Test
  public void testAddPieceISE3() {
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    Assert.assertThrows(IllegalStateException.class, () ->
            hex2.addPiece(TeamColor.BLACK, new HexPosition(1,1,-2)));

  }


  //checks the size of the board is correct
  @Test
  public void testGetBoard() {
    Assert.assertEquals(6, hex2.getBoard().size());
    Assert.assertEquals(6, hex2read.getBoard().size());
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    Assert.assertEquals(7, hex2.getBoard().size());
    Assert.assertEquals(7, hex2read.getBoard().size());
  }

  //test score updates after move
  @Test
  public void getScore() {
    Assert.assertEquals(3, hex5.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(3, hex5read.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(3, hex5.getScoreColor(TeamColor.BLACK));
    Assert.assertEquals(3, hex5read.getScoreColor(TeamColor.BLACK));
    hex5.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    Assert.assertEquals(2, hex5.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(2, hex5read.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(5, hex5.getScoreColor(TeamColor.BLACK));
    Assert.assertEquals(5, hex5read.getScoreColor(TeamColor.BLACK));
    hex5.addPiece(TeamColor.WHITE, new HexPosition(2,-1,-1));
    Assert.assertEquals(4, hex5.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(4, hex5read.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(4, hex5.getScoreColor(TeamColor.BLACK));
    Assert.assertEquals(4, hex5read.getScoreColor(TeamColor.BLACK));
  }

  //test flipCount works
  @Test
  public void flipCount() {
    Assert.assertEquals(1, hex5.flipCount(new HexPosition(1,-2,1)));
    Assert.assertEquals(1, hex5read.flipCount(new HexPosition(1,-2,1)));
    Assert.assertEquals(0, hex5.flipCount(new HexPosition(0,0,0)));
    Assert.assertEquals(0, hex5read.flipCount(new HexPosition(0,0,0)));
    hex5.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    hex5.addPiece(TeamColor.WHITE, new HexPosition(2,-1,-1));
    Assert.assertEquals(2, hex5.flipCount(new HexPosition(1,1,-2)));
    Assert.assertEquals(2, hex5read.flipCount(new HexPosition(1,1,-2)));
  }

  //test model works with all moves
  @Test
  public void testFullGame() {
    //black's turn
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,-2,1));
    //white's turn
    hex2.addPiece(TeamColor.WHITE, new HexPosition(2,-1,-1));
    //black's turn
    hex2.addPiece(TeamColor.BLACK, new HexPosition(1,1,-2));
    //white's turn
    hex2.pass();
    //black's turn
    Assert.assertThrows(IllegalStateException.class,
            () -> hex2.addPiece(TeamColor.BLACK, new HexPosition(-2,2,0)));
    Assert.assertEquals(TeamColor.BLACK, hex2.getCurrentTurn());
    hex2.addPiece(TeamColor.BLACK, new HexPosition(-1,-1,2));
    //white's turn
    hex2.addPiece(TeamColor.WHITE, new HexPosition(-1,2,-1));
    //black's turn
    hex2.pass();
    //white's turn
    hex2.addPiece(TeamColor.WHITE, new HexPosition(-2,1,1));
    //no more valid moves for either
    Assert.assertTrue(hex2.isGameOver());
  }



}
