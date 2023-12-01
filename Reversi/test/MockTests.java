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
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.CaptureMost;
import cs3500.reversi.view.IReversiView;
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

  HexReversiController controller2;
  HexReversiController controllerMock2;

  Player mockTwo1;
  Player mockTwo2;

  IReversiView viewTwo1mock;
  IReversiView viewTwo2mock;

  HexReversiController controllerTwo1;
  HexReversiController controllerMockTwo1;

  HexReversiController controllerTwo2;
  HexReversiController controllerMockTwo2;

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

    controller2 = new VisualController(hex5, view2mock, mock2);
    controllerMock2 = new VisualControllerMock(out, controller2);

    hex5.startGame();

    //for a hexgame of size 2

    out2 = new StringBuilder();
    mockTwo1 = new PlayerMock(out, new AIPlayer(TeamColor.BLACK, new CaptureMost(), hex2read));
    mockTwo2 = new PlayerMock(out, new AIPlayer(TeamColor.WHITE, new CaptureMost(), hex2read));


    viewTwo1mock = new ReversiGUIViewMock(out2);

    viewTwo2mock = new ReversiGUIViewMock(out2);

    controllerTwo1 = new VisualController(hex2, viewTwo1mock, mockTwo1);
    controllerMockTwo1 = new VisualControllerMock(out2, controllerTwo1);

    controllerTwo2 = new VisualController(hex2, viewTwo2mock, mockTwo2);
    controllerMockTwo2 = new VisualControllerMock(out2, controllerTwo2);

    hex2.startGame();
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
    Assert.assertThrows(IllegalStateException.class, () -> hex2.addPiece(
            TeamColor.BLACK, new HexPosition(-2,2,0)));
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

  @Test
  public void testControllerObservesMove() {
    controllerMock1.notifyMakeMove(new HexPosition(1,1,-2));
    Assert.assertTrue(out.toString().contains("makeMove: [1,1,-2]"));
    controllerMock1.notifyMakeMove(new HexPosition(1,1,-2));
    Assert.assertTrue(out.toString().contains("makeMove: [1,1,-2]"));
    Assert.assertTrue(out.toString().contains("Not your turn"));
  }

  @Test
  public void testControllerObservesPassTurn() {
    controllerMock1.notifyPassTurn();
    Assert.assertTrue(out.toString().contains("pass"));
    controllerMock1.notifyPassTurn();
    Assert.assertTrue(out.toString().contains("pass"));
    Assert.assertTrue(out.toString().contains("Game over"));
  }

  @Test
  public void testControllerObservesModelFeatures() {
    controllerMock1.notifyStartGame(TeamColor.BLACK);
    Assert.assertTrue(out.toString().contains("displayMessage: Welcome to Reversi!"));

    controllerMock1.notifyUpdatedGameState();
    Assert.assertTrue(out.toString().contains("notifyUpdatedGamestate"));
    Assert.assertTrue(out.toString().contains("renderView: BLACK"));

    controllerMock1.notifyMessage(TeamColor.BLACK, "Testing");
    Assert.assertTrue(out.toString().contains("notifyMessage: BLACK, Testing"));
    Assert.assertTrue(out.toString().contains("displayMessage: Testing"));
    Assert.assertTrue(out.toString().contains("renderView: BLACK"));

    controllerMock1.notifyAdvanceTurn(TeamColor.WHITE);
    Assert.assertTrue(out.toString().contains("notifyAdvanceTurn: WHITE"));
    Assert.assertTrue(out.toString().contains("enableMoves: false"));
  }
}
