import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.controller.HexReversiController;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerImpl;
import cs3500.reversi.controller.TextualController;
import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.HexReversiMock;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.view.ReversiTextualView;
import cs3500.reversi.view.ReversiView;

public class StrategyMockTests {
  ReversiModel mock;
  Appendable mockOut;
  Appendable viewOut;
  ReversiStrategy captureMost;
  HexReversiController controller;
  ReversiView view;
  Player player1;
  Player player2;


  @Before
  public void initData() {
    mockOut = new StringBuilder();
    mock = new HexReversiMock(mockOut, new HexReversi(2));
    captureMost = new CaptureMost();
    viewOut = new StringBuilder();
    view = new ReversiTextualView(new ReadonlyHexReversiModel(mock), viewOut);
    controller = new TextualController(mock, view);
    player1 = new PlayerImpl(TeamColor.BLACK, captureMost);
    player2 = new PlayerImpl(TeamColor.WHITE, captureMost);
    controller.addPlayer(player1);
    controller.addPlayer(player2);
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
  }

  @Test
  public void testCaptureMostNoValidMovesReturnsNull() {
    mock.addPiece(TeamColor.BLACK, new HexPosition(-1, 2, -1));
    mock.addPiece(TeamColor.WHITE, new HexPosition(1, 1, -2));
    mock.addPiece(TeamColor.BLACK, new HexPosition(-2, 1, 1));
    mock.addPiece(TeamColor.WHITE, new HexPosition(-1, -1, 2));
    mock.addPiece(TeamColor.BLACK, new HexPosition(2, -1, -1));
    Assert.assertNull(captureMost.choosePosn(new ReadonlyHexReversiModel(mock), TeamColor.WHITE));
    mock.pass();
    mock.addPiece(TeamColor.BLACK, captureMost.choosePosn(
            new ReadonlyHexReversiModel(mock), TeamColor.BLACK));

    Assert.assertTrue(mockOut.toString().contains("getValidMoves\n" +
            "flipCount: [1,-2,1], value: 2\n" +
            "addPiece: [1,-2,1]"));
  }
}
