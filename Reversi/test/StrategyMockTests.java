import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.controller.AIPlayer;
import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerMock;
import cs3500.reversi.controller.VisualController;
import cs3500.reversi.controller.VisualControllerMock;
import cs3500.reversi.model.Position;
import cs3500.reversi.model.ReversiMock;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.SquareReversi;
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

  ReversiController controller1;
  ReversiController controllerMock1;

  ReversiController controller2;
  ReversiController controllerMock2;

  ReadonlyHexReversiModel ror;

  @Before
  public void initData() {
    mockOut = new StringBuilder();
    mock = new ReversiMock(mockOut, new SquareReversi(4));
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

    ror =  new ReadonlyHexReversiModel(mock);

    mock.startGame();
  }

  @Test
  public void testCaptureMostBreakingTie() {
    initData();
    Position pos = captureMost.choosePosn(ror, TeamColor.BLACK);
    mock.addPiece(TeamColor.BLACK, pos);
    // Strategy checks all possible valid moves, in top down, left to right, in order
    // flip count is 1 for every single one here (first move),
    // so its breaking ties by picking top leftmost
    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [0,1], value: 1\n" +
            "flipCount: [1,0], value: 1\n" +
            "flipCount: [2,3], value: 1\n" +
            "flipCount: [3,2], value: 1\n" +
            "addPiece: [0,1]"));
    mock.addPiece(TeamColor.WHITE, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.WHITE));

    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [0,0], value: 1\n" +
            "flipCount: [0,2], value: 1\n" +
            "flipCount: [2,0], value: 1\n" +
            "addPiece: [0,0]"));
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
    Position pos = captureMost.choosePosn(ror, TeamColor.WHITE);
    mock.addPiece(TeamColor.WHITE, pos);
    pos = captureMost.choosePosn(ror, TeamColor.BLACK);
    mock.addPiece(TeamColor.BLACK, pos);
    pos = captureMost.choosePosn(ror, TeamColor.WHITE);
    mock.addPiece(TeamColor.WHITE, pos);

    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [2,0], value: 3\n" +
            "flipCount: [2,3], value: 1\n" +
            "flipCount: [3,1], value: 2\n" +
            "addPiece: [2,0]"));
    pos = captureMost.choosePosn(ror, TeamColor.BLACK);
    mock.addPiece(TeamColor.BLACK, pos);
    // out of these three, picks last because highest value, even though its bottom right
    System.out.println(mockOut);
    mock.addPiece(TeamColor.WHITE, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.WHITE));
    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [3,0], value: 1\n" +
            "flipCount: [3,2], value: 1\n" +
            "addPiece: [3,0]"));
  }
}
