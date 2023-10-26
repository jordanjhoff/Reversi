import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.view.ReversiTextualView;
import cs3500.reversi.view.ReversiView;

/**
 * Class for the testing of reversiTextualView.
 */
public class ReversiTextualViewTests {

  ReversiModel model;
  ReversiModel five;
  ReversiView view;
  ReversiView view5;
  Appendable out;

  @Before
  public void initData() {
    model = new HexReversi();
    five = new HexReversi();
    five.startGame(5);
    out = new StringBuilder();
    view = new ReversiTextualView(model, out);
    view5 = new ReversiTextualView(five, out);
  }

  @Test
  public void testNotStarted(){
    initData();
    Assert.assertThrows(IllegalStateException.class, () -> view.render());
  }

  @Test
  public void testDealtGameRad5() {
    try {
      initData();
      view5.render();
      Assert.assertEquals(
              "     _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _\n" +
                      "  _ _ _ _ _ _ _ _ _\n" +
                      " _ _ _ _ X O _ _ _ _\n" +
                      "_ _ _ _ O _ X _ _ _ _\n" +
                      " _ _ _ _ X O _ _ _ _\n" +
                      "  _ _ _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "     _ _ _ _ _ _", five.toString());
    } catch (IOException e) {
      Assert.assertFalse("Should have not thrown", true);
      // cant find assertfail
    }
  }

  @Test
  public void testDealtGameRad2() {
    try {
      initData();
      model.startGame(2);
      view.render();
      Assert.assertEquals(
              "  _ _ _\n" +
                      " _ X O _\n" +
                      "_ O _ X _\n" +
                      " _ X O _\n" +
                      "  _ _ _", out.toString());
    } catch (IOException e) {
      Assert.assertFalse("Should have not thrown", true);
      // cant find assertfail
    }
  }

  @Test
  public void testDealtGameRad10() {
    try {
      initData();
      model.startGame(10);
      view.render();
      Assert.assertEquals(
              "          _ _ _ _ _ _ _ _ _ _ _\n" +
                      "         _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "        _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "       _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "      _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "     _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "  _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      " _ _ _ _ _ _ _ _ _ X O _ _ _ _ _ _ _ _ _\n" +
                      "_ _ _ _ _ _ _ _ _ O _ X _ _ _ _ _ _ _ _ _\n" +
                      " _ _ _ _ _ _ _ _ _ X O _ _ _ _ _ _ _ _ _\n" +
                      "  _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "     _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "      _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "       _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "        _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "         _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "          _ _ _ _ _ _ _ _ _ _ _", out.toString());
    } catch (IOException e) {
      Assert.assertFalse("Should have not thrown", true);
      // cant find assertfail
    }
  }

  @Test
  public void testFiveAddPiece() {
    try {
      initData();
      five.addPiece(TeamColor.BLACK, new HexPosition(1, -2, 1));
      view5.render();
      Assert.assertEquals(
              "     _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _\n" +
                      "  _ _ _ _ X _ _ _ _\n" +
                      " _ _ _ _ X X _ _ _ _\n" +
                      "_ _ _ _ O _ X _ _ _ _\n" +
                      " _ _ _ _ X O _ _ _ _\n" +
                      "  _ _ _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "     _ _ _ _ _ _", out.toString());
      view5.render();
      five.addPiece(TeamColor.WHITE, new HexPosition(2, -3, 1));
      Assert.assertEquals(
              "     _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ O _ _ _\n" +
                      "  _ _ _ _ O _ _ _ _\n" +
                      " _ _ _ _ O X _ _ _ _\n" +
                      "_ _ _ _ O _ X _ _ _ _\n" +
                      " _ _ _ _ X O _ _ _ _\n" +
                      "  _ _ _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "     _ _ _ _ _ _", out.toString());
    } catch (IOException e) {
      Assert.assertFalse("Should have not thrown", true);
      // cant find assertfail
    }
  }



}
