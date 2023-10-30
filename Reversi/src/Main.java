import java.io.IOException;

import cs3500.reversi.model.HexReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.ReversiTextualView;
import cs3500.reversi.view.ReversiView;

public class Main {
  public static void main(String[] args) {
    ReversiModel model = new HexReversi(5);
    Appendable out = new StringBuilder();
    ReversiView view = new ReversiTextualView(model, out);
    try {
      view.render();
    } catch (IOException e) {
      //dont care!
    }
    System.out.println(out.toString());
  }
}