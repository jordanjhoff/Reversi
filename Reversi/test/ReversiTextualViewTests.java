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
    model = new HexReversi(3);
    five = new HexReversi(5);
    out = new StringBuilder();
    view = new ReversiTextualView(model, out);
    view5 = new ReversiTextualView(five, out);
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
                      "  _ _ _ _ * _ _ _ _\n" +
                      " _ _ _ * X O * _ _ _\n" +
                      "_ _ _ _ O _ X _ _ _ _\n" +
                      " _ _ _ * X O * _ _ _\n" +
                      "  _ _ _ _ * _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "     _ _ _ _ _ _\n", out.toString());
    } catch (IOException e) {
      Assert.assertFalse("Should have not thrown", true);
      // cant find assertfail
    }
  }

  @Test
  public void testDealtGameRad2() {
    try {
      initData();
      model = new HexReversi(2);
      view = new ReversiTextualView(model, out);
      view.render();
      Assert.assertEquals(
              "  _ * _\n" +
                      " * X O *\n" +
                      "_ O _ X _\n" +
                      " * X O *\n" +
                      "  _ * _\n", out.toString());
    } catch (IOException e) {
      Assert.assertFalse("Should have not thrown", true);
      // cant find assertfail
    }
  }

  @Test
  public void testDealtGameRad10() {
    try {
      initData();
      model = new HexReversi(10);
      view = new ReversiTextualView(model, out);
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
                      "  _ _ _ _ _ _ _ _ _ * _ _ _ _ _ _ _ _ _\n" +
                      " _ _ _ _ _ _ _ _ * X O * _ _ _ _ _ _ _ _\n" +
                      "_ _ _ _ _ _ _ _ _ O _ X _ _ _ _ _ _ _ _ _\n" +
                      " _ _ _ _ _ _ _ _ * X O * _ _ _ _ _ _ _ _\n" +
                      "  _ _ _ _ _ _ _ _ _ * _ _ _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "     _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "      _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "       _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "        _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "         _ _ _ _ _ _ _ _ _ _ _ _\n" +
                      "          _ _ _ _ _ _ _ _ _ _ _\n", out.toString());
    } catch (IOException e) {
      Assert.assertFalse("Should have not thrown", true);
      // cant find assertfail
    }
  }

  @Test
  public void testFiveAddPiece() {
    try {
      initData();
      five.addPiece(new HexPosition(1, -2, 1));
      view5.render();
      Assert.assertEquals(
              "     _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ * _ _ _\n" +
                      "  _ _ _ _ X _ _ _ _\n" +
                      " _ _ _ _ X X * _ _ _\n" +
                      "_ _ _ _ O _ X _ _ _ _\n" +
                      " _ _ _ * X O _ _ _ _\n" +
                      "  _ _ _ _ * _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "     _ _ _ _ _ _\n", out.toString());
      view5.render();
      five.addPiece(new HexPosition(2, -3, 1));
      view5.render();
      System.out.println(out.toString());
      Assert.assertTrue(out.toString().contains(
              "_ _ _ _ _ _\n" +
              "    _ _ _ _ _ _ _\n" +
              "   _ _ _ * O _ _ _\n" +
              "  _ _ _ _ O _ _ _ _\n" +
              " _ _ _ * O X _ _ _ _\n" +
              "_ _ _ _ O _ X _ _ _ _\n" +
              " _ _ _ _ X O * _ _ _\n" +
              "  _ _ _ _ * _ _ _ _\n" +
              "   _ _ _ _ _ _ _ _\n" +
              "    _ _ _ _ _ _ _\n" +
              "     _ _ _ _ _ _"));
    } catch (IOException e) {
      Assert.assertFalse("Should have not thrown", true);
      // cant find assertfail
    }
  }

  @Test
  public void testFiveInvalid() {
    try {
      initData();
      five.addPiece(new HexPosition(1, -2, 1));
      view5.render();
      Assert.assertEquals(
              "     _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "   _ _ _ _ * _ _ _\n" +
                      "  _ _ _ _ X _ _ _ _\n" +
                      " _ _ _ _ X X * _ _ _\n" +
                      "_ _ _ _ O _ X _ _ _ _\n" +
                      " _ _ _ * X O _ _ _ _\n" +
                      "  _ _ _ _ * _ _ _ _\n" +
                      "   _ _ _ _ _ _ _ _\n" +
                      "    _ _ _ _ _ _ _\n" +
                      "     _ _ _ _ _ _\n", out.toString());
      five.addPiece(new HexPosition(0, -3, 3));
      view5.render();
      Assert.assertFalse(true); // should not have arrived here
    } catch (Exception e) {
      Assert.assertTrue(out.toString().contains(
              "     _ _ _ _ _ _\n" +
              "    _ _ _ _ _ _ _\n" +
              "   _ _ _ _ * _ _ _\n" +
              "  _ _ _ _ X _ _ _ _\n" +
              " _ _ _ _ X X * _ _ _\n" +
              "_ _ _ _ O _ X _ _ _ _\n" +
              " _ _ _ * X O _ _ _ _\n" +
              "  _ _ _ _ * _ _ _ _\n" +
              "   _ _ _ _ _ _ _ _\n" +
              "    _ _ _ _ _ _ _\n" +
              "     _ _ _ _ _ _\n"));
    }
  }
}
