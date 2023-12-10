import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.controller.AIPlayer;
import cs3500.reversi.controller.HexReversiController;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerMock;
import cs3500.reversi.controller.VisualController;
import cs3500.reversi.controller.VisualControllerMock;
import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.HexReversiMock;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.ReversiGUIViewMock;

/**
 * A class to test the CaptureMost strategy implementation and its interaction
 * with the HexReversi game model.
 */
public class StrategyMockTests {
  ReversiModel mock;
  Appendable mockOut;
  Appendable viewOut;
  ReversiStrategy captureMost;

  Player mock1;
  Player mock2;

  IReversiView view1mock;
  IReversiView view2mock;

  HexReversiController controller1;
  HexReversiController controllerMock1;

  HexReversiController controller2;
  HexReversiController controllerMock2;



  @Before
  public void initData() {
    mockOut = new StringBuilder();
    mock = new HexReversiMock(mockOut, new HexReversi(2));
    captureMost = new CaptureMost();
    viewOut = new StringBuilder();

    mock1 = new PlayerMock(viewOut, new AIPlayer(TeamColor.BLACK, new CaptureMost(), mock));
    mock2 = new PlayerMock(viewOut, new AIPlayer(TeamColor.WHITE, new CaptureMost(), mock));


    view1mock = new ReversiGUIViewMock(viewOut);
    view2mock = new ReversiGUIViewMock(viewOut);

    controller1 = new VisualController(mock, view1mock, mock1);
    controllerMock1 = new VisualControllerMock(viewOut, controller1);

    controller2 = new VisualController(mock, view2mock, mock2);
    controllerMock2 = new VisualControllerMock(viewOut, controller2);

    mock.startGame();
  }

  @Test
  public void testCaptureMostBreakingTie() {
    initData();
    mock.addPiece(TeamColor.BLACK, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.BLACK));
    // Strategy checks all possible valid moves, in top down, left to right, in order
    // flip count is 1 for every single one here (first move),
    // so its breaking ties by picking top leftmost
    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [1,-2,1], value: 1\n" +
            "flipCount: [-1,-1,2], value: 1\n" +
            "flipCount: [2,-1,-1], value: 1\n" +
            "flipCount: [-2,1,1], value: 1\n" +
            "flipCount: [1,1,-2], value: 1\n" +
            "flipCount: [-1,2,-1], value: 1\n" +
            "addPiece: [1,-2,1]"));
    mock.addPiece(TeamColor.WHITE, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.WHITE));

    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [2,-1,-1], value: 1\n" +
            "flipCount: [-2,1,1], value: 1\n" +
            "flipCount: [-1,2,-1], value: 1\n" +
            "addPiece: [2,-1,-1]"));
    // another example
  }

  @Test
  public void testCaptureMostPickingHighest() {
    initData();
    mock.addPiece(TeamColor.BLACK, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.BLACK));
    mock.addPiece(TeamColor.WHITE, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.WHITE));
    mock.addPiece(TeamColor.BLACK, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.BLACK));

    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [-1,-1,2], value: 1\n" +
            "flipCount: [-2,1,1], value: 1\n" +
            "flipCount: [1,1,-2], value: 2\n" +
            "addPiece: [1,1,-2]"));
    // out of these three, picks last because highest value, even though its bottom right

    mock.addPiece(TeamColor.WHITE, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.WHITE));
    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [-1,-1,2], value: 2\n" +
            "flipCount: [-1,2,-1], value: 3\n" +
            "addPiece: [-1,2,-1]"));
    // again

    mock.addPiece(TeamColor.BLACK, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.BLACK));
    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [-2,1,1], value: 3\n" +
            "addPiece: [-2,1,1]"));
    // one valid move picks it

    System.out.println(mockOut);
  }

  @Test
  public void testCaptureMostNoValidMovesReturnsNull() {
    mock.addPiece(TeamColor.BLACK, new HexPosition(-1, 2));
    mock.addPiece(TeamColor.WHITE, new HexPosition(1, 1));
    mock.addPiece(TeamColor.BLACK, new HexPosition(-2, 1));
    mock.addPiece(TeamColor.WHITE, new HexPosition(-1, -1));
    mock.addPiece(TeamColor.BLACK, new HexPosition(2, -1));
    Assert.assertNull(captureMost.choosePosn(new ReadonlyHexReversiModel(mock), TeamColor.WHITE));
    mock.pass();
    mock.addPiece(TeamColor.BLACK, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.BLACK));

    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [1,-2,1], value: 2\n" +
            "addPiece: [1,-2,1]"));
  }
}
