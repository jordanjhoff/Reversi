import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import cs3500.reversi.controller.AIPlayer;
import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerMock;
import cs3500.reversi.controller.VisualController;
import cs3500.reversi.controller.VisualControllerMock;
import cs3500.reversi.model.SquarePos;
import cs3500.reversi.model.Position;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.ReversiGUIViewMock;

/**
 * Class for testing the hexreversi model implementation of reversimodel.
 */
public class SquareReversiTests {


  ReversiModel square4;
  ReadonlyReversiModel square4read;

  Appendable out;

  Player mock1;
  Player mock2;

  IReversiView view1mock;
  IReversiView view2mock;

  ReversiController controller1;
  ReversiController controllerMock1;

  ReversiController controller2;
  ReversiController controllerMock2;

  @Before
  public void init() {
    square4 = new SquareReversi(4);
    square4read = new ReadonlyHexReversiModel(square4);

    // for a hexgame of size 5

    out = new StringBuilder();
    mock1 = new PlayerMock(out, new AIPlayer(TeamColor.BLACK, new CaptureMost(), square4read));
    mock2 = new PlayerMock(out, new AIPlayer(TeamColor.WHITE, new CaptureMost(), square4read));


    view1mock = new ReversiGUIViewMock(out);
    view2mock = new ReversiGUIViewMock(out);

    controller1 = new VisualController(square4, view1mock, mock1);
    controllerMock1 = new VisualControllerMock(out, controller1);

    controller2 = new VisualController(square4, view2mock, mock2);
    controllerMock2 = new VisualControllerMock(out, controller2);

    square4.startGame();
  }


  //test getsize works
  @Test
  public void getSize() {
    Assert.assertEquals(4, square4.getSize());
    Assert.assertEquals(4, square4read.getSize());
    Assert.assertEquals(100, new SquareReversi(100).getSize());
  }

  //test the constructor doesn't throw on valid sizes
  @Test
  public void testSquareReversiConstructor() {
    try {
      new SquareReversi(4);
      new SquareReversi(100);
      new SquareReversi(22);

    }
    catch (Exception e) {
      Assert.fail("Test failed");
    }
  }

  //tests the constructor throws when incorrect sizes
  @Test
  public void testSquareReversiConstructorIAE() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new SquareReversi(1));
    Assert.assertThrows(IllegalArgumentException.class, () -> new SquareReversi(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> new SquareReversi(13));
    Assert.assertThrows(IllegalArgumentException.class, () -> new SquareReversi(-11));
  }

  //test getting a piece returns accurately
  @Test
  public void testGetPieceAt() {
    System.out.println(square4.getBoard());
    Assert.assertEquals(TeamColor.WHITE, square4.getPieceAt(new SquarePos(1,1)));
    Assert.assertEquals(TeamColor.BLACK, square4.getPieceAt(new SquarePos(1,2)));
    Assert.assertEquals(TeamColor.BLACK, square4.getPieceAt(new SquarePos(2,1)));
    Assert.assertEquals(TeamColor.WHITE, square4.getPieceAt(new SquarePos(2,2)));
  }

  //test getting a piece that isn't there returns null
  @Test
  public void testGetPieceAt2() {
    Assert.assertNull(square4.getPieceAt(new SquarePos(0,0)));
    Assert.assertNull(square4.getPieceAt(new SquarePos(0,1)));
  }

  //test getting a piece out of bounds throws IAE
  @Test
  public void testGetPieceAtIAE() {
    System.out.println(square4.getPieceAt(
            new SquarePos(0,-1)));
    Assert.assertThrows(IllegalArgumentException.class, () -> square4.getPieceAt(
            new SquarePos(-4,4)));
    Assert.assertThrows(IllegalArgumentException.class, () -> square4.getPieceAt(
            new SquarePos(0,5)));
  }

  //test getting current turn is correct
  @Test
  public void getCurrentTurn() {
    Assert.assertEquals(TeamColor.BLACK, square4.getCurrentTurn());
    Assert.assertEquals(TeamColor.BLACK, square4read.getCurrentTurn());
    square4.pass();
    Assert.assertEquals(TeamColor.WHITE, square4.getCurrentTurn());
    Assert.assertEquals(TeamColor.WHITE, square4read.getCurrentTurn());
  }

  //test getting current turn is correct
  @Test
  public void getCurrentTurn2() {
    Assert.assertEquals(TeamColor.BLACK, square4.getCurrentTurn());
    Assert.assertEquals(TeamColor.BLACK, square4read.getCurrentTurn());
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    Assert.assertEquals(TeamColor.WHITE, square4.getCurrentTurn());
    Assert.assertEquals(TeamColor.WHITE, square4read.getCurrentTurn());
  }

  //test get current turn for finished game throws ISE
  @Test
  public void getCurrentTurnISE() {
    square4.pass();
    square4.pass();
    Assert.assertThrows(IllegalStateException.class, () -> square4.getCurrentTurn());
    Assert.assertThrows(IllegalStateException.class, () -> square4read.getCurrentTurn());
  }

  //test pass advances the turn
  @Test
  public void testPass() {
    try {
      Assert.assertEquals(TeamColor.BLACK, square4.getCurrentTurn());
      Assert.assertEquals(TeamColor.BLACK, square4read.getCurrentTurn());
      square4.pass();
      Assert.assertEquals(TeamColor.WHITE, square4.getCurrentTurn());
      Assert.assertEquals(TeamColor.WHITE, square4read.getCurrentTurn());
    }
    catch (Exception e) {
      Assert.fail("Test failed");
    }
  }

  //test pass throws ISE when game over
  @Test
  public void testPassISE() {
    square4.pass();
    square4.pass();
    Assert.assertThrows(IllegalStateException.class, () -> square4.pass());
  }

  //test winner is correct
  @Test
  public void testGetWinner() {
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    square4.pass();
    square4.pass();
    Assert.assertEquals(TeamColor.BLACK, square4.getWinner());
    Assert.assertEquals(TeamColor.BLACK, square4read.getWinner());
  }

  //test winner returns null if number of pieces are the same
  @Test
  public void testGetWinner2() {
    square4.pass();
    square4.pass();
    Assert.assertNull(square4.getWinner());
    Assert.assertNull(square4read.getWinner());
  }


  //test winner throws if game not over
  @Test
  public void testGetWinnerISE() {
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    Assert.assertThrows(IllegalStateException.class, () -> square4.getWinner());
    Assert.assertThrows(IllegalStateException.class, () -> square4read.getWinner());
  }

  //test game ends after two passes
  @Test
  public void testGameOver() {
    Assert.assertFalse(square4.isGameOver());
    Assert.assertFalse(square4read.isGameOver());
    square4.pass();
    Assert.assertFalse(square4.isGameOver());
    Assert.assertFalse(square4read.isGameOver());
    square4.pass();
    Assert.assertTrue(square4.isGameOver());
    Assert.assertTrue(square4read.isGameOver());
  }

  //test valid moves are correct before adding any pieces (current turn is black)
  @Test
  public void testValidMoves() {
    Assert.assertEquals(4, square4.getValidMoves().size());
    Assert.assertEquals(4, square4read.getValidMoves().size());
    HashSet<SquarePos> expectedBlackValidMoves = new HashSet<>(Arrays.asList(
            new SquarePos(1,0), new SquarePos(3,2),
            new SquarePos(0,1), new SquarePos(2,3)));
    Assert.assertEquals(expectedBlackValidMoves, new HashSet<>(square4.getValidMoves()));
    Assert.assertEquals(expectedBlackValidMoves, new HashSet<>(square4read.getValidMoves()));
  }

  //test valid moves update correctly after adding pieces
  @Test
  public void testValidMoves2() {
    HashSet<Position> validMovesBefore = new HashSet<>(square4.getValidMoves());
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    //now its white's turn
    HashSet<SquarePos> expectedWhiteValidMoves = new HashSet<>(Arrays.asList(
            new SquarePos(0,0), new SquarePos(0,2),
            new SquarePos(2,0)));
    Assert.assertNotEquals(validMovesBefore, new HashSet<>(square4.getValidMoves()));
    Assert.assertNotEquals(validMovesBefore, new HashSet<>(square4read.getValidMoves()));
    Assert.assertEquals(3, square4.getValidMoves().size());
    Assert.assertEquals(expectedWhiteValidMoves, new HashSet<>(square4.getValidMoves()));
    Assert.assertEquals(expectedWhiteValidMoves, new HashSet<>(square4read.getValidMoves()));
  }

  //test getValidMoves is empty if the game is over
  @Test
  public void testValidMoves3() {
    Assert.assertTrue(!square4.getValidMoves().isEmpty());
    Assert.assertTrue(!square4read.getValidMoves().isEmpty());
    square4.pass();
    square4.pass();
    Assert.assertEquals(new ArrayList<>(), square4.getValidMoves());
    Assert.assertEquals(new ArrayList<>(), square4read.getValidMoves());
  }

  //test addPiece adds the correct color
  @Test
  public void testAddPiece() {
    //black's turn
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    Assert.assertEquals(TeamColor.BLACK, square4.getPieceAt(new SquarePos(0,1)));
    Assert.assertEquals(TeamColor.BLACK, square4read.getPieceAt(new SquarePos(0,1)));
    //white's turn
    square4.addPiece(TeamColor.WHITE, new SquarePos(0,0));
    Assert.assertEquals(TeamColor.WHITE, square4.getPieceAt(new SquarePos(0,0)));
    Assert.assertEquals(TeamColor.WHITE, square4read.getPieceAt(new SquarePos(0,0)));
  }

  //test addPiece updates a single piece correctly
  @Test
  public void testAddPiece2() {
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    Assert.assertEquals(TeamColor.BLACK, square4.getPieceAt(new SquarePos(1,1)));
    Assert.assertEquals(TeamColor.BLACK, square4read.getPieceAt(new SquarePos(1,1)));
  }

  //test addPiece updates multiple pieces correctly
  @Test
  public void testAddPiece3() {
    //black's turn
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    //white's turn
    square4.addPiece(TeamColor.WHITE, new SquarePos(0,0));
    Assert.assertEquals(TeamColor.WHITE, square4.getPieceAt(new SquarePos(1,1)));
    Assert.assertEquals(TeamColor.WHITE, square4.getPieceAt(new SquarePos(1,1)));
  }

  //test invalid addPiece doesn't change current turn
  @Test
  public void testAddPiece4() {
    //black's turn
    Assert.assertEquals(TeamColor.BLACK, square4.getCurrentTurn());
    Assert.assertEquals(TeamColor.BLACK, square4read.getCurrentTurn());
    Assert.assertThrows(IllegalStateException.class, () -> square4.addPiece(
            TeamColor.BLACK, new SquarePos(2,2)));
    Assert.assertEquals(TeamColor.BLACK, square4.getCurrentTurn());
    Assert.assertEquals(TeamColor.BLACK, square4read.getCurrentTurn());

  }


  //test addPiece with an out of bounds throws an IAE
  @Test
  public void testAddPieceIAE() {
    Assert.assertThrows(IllegalArgumentException.class, () -> square4.addPiece(TeamColor.BLACK,
            new SquarePos(-4,4)));
    Assert.assertThrows(IllegalArgumentException.class, () -> square4.addPiece(TeamColor.BLACK,
            new SquarePos(1,4)));
  }

  //test addPiece with on an invalid empty board coordinate place throws ISE
  @Test
  public void testAddPieceISE() {
    Assert.assertThrows(IllegalStateException.class, () -> square4.addPiece(TeamColor.BLACK,
            new SquarePos(0,0)));
    Assert.assertThrows(IllegalStateException.class, () -> square4.addPiece(TeamColor.BLACK,
            new SquarePos(3,3)));
  }

  //test addPiece with on an invalid occupied board coordinate place throws ISE
  @Test
  public void testAddPieceISE2() {
    Assert.assertThrows(IllegalStateException.class, () -> square4.addPiece(TeamColor.BLACK,
            new SquarePos(0,-1)));
    Assert.assertThrows(IllegalStateException.class, () -> square4.addPiece(TeamColor.BLACK,
            new SquarePos(-1,1)));
  }

  //test addPiece with same player making a move twice
  @Test
  public void testAddPieceISE3() {
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    Assert.assertThrows(IllegalStateException.class, () ->
            square4.addPiece(TeamColor.BLACK, new SquarePos(3,2)));

  }


  //checks the size of the board is correct
  @Test
  public void testGetBoard() {
    Assert.assertEquals(4, square4.getBoard().size());
    Assert.assertEquals(4, square4read.getBoard().size());
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    Assert.assertEquals(5, square4.getBoard().size());
    Assert.assertEquals(5, square4read.getBoard().size());
  }

  //test score updates after move
  @Test
  public void getScore() {
    Assert.assertEquals(2, square4.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(2, square4read.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(2, square4.getScoreColor(TeamColor.BLACK));
    Assert.assertEquals(2, square4read.getScoreColor(TeamColor.BLACK));
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    Assert.assertEquals(1, square4.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(1, square4read.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(4, square4.getScoreColor(TeamColor.BLACK));
    Assert.assertEquals(4, square4read.getScoreColor(TeamColor.BLACK));
    square4.addPiece(TeamColor.WHITE, new SquarePos(0,0));
    Assert.assertEquals(3, square4.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(3, square4read.getScoreColor(TeamColor.WHITE));
    Assert.assertEquals(3, square4.getScoreColor(TeamColor.BLACK));
    Assert.assertEquals(3, square4read.getScoreColor(TeamColor.BLACK));
  }

  //test flipCount works
  @Test
  public void flipCount() {
    Assert.assertEquals(1, square4.flipCount(new SquarePos(0,1)));
    Assert.assertEquals(1, square4read.flipCount(new SquarePos(0,1)));
    Assert.assertEquals(0, square4.flipCount(new SquarePos(0,0)));
    Assert.assertEquals(0, square4read.flipCount(new SquarePos(0,0)));
    square4.addPiece(TeamColor.BLACK, new SquarePos(0,1));
    square4.addPiece(TeamColor.WHITE, new SquarePos(0,0));
    Assert.assertEquals(0, square4.flipCount(new SquarePos(2,1)));
    Assert.assertEquals(0, square4read.flipCount(new SquarePos(2,1)));
  }


}
