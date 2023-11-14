import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.controller.HexReversiController;
import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerImpl;
import cs3500.reversi.controller.TextualController;
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
    mock = new HexReversiMock(mockOut);
    captureMost = new CaptureMost();
    viewOut = new StringBuilder();
    view = new ReversiTextualView(new ReadonlyHexReversiModel(mock), viewOut);
    controller = new TextualController(mock, view);
    player1 = new PlayerImpl(TeamColor.WHITE, captureMost);
    player2 = new PlayerImpl(TeamColor.BLACK, captureMost);
  }

  @Test
  public void testCaptureMost() {
    controller.play();
    System.out.println(mockOut.toString());
  }

}
