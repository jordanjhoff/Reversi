package cs3500.reversi.view;

import java.io.IOException;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;

/**
 * Textual implementation of a reversi view. Outputs will
 * be added to the appendable out to be viewed.
 */
public class ReversiTextualView implements ReversiView {

  private Appendable out;
  private ReversiModel model;

  /**
   * generates a textual view with generic stringbuilder output.
   *
   * @param model to be represented and rendered
   */
  public ReversiTextualView(ReversiModel model) {
    this(model, new StringBuilder());
  }

  /**
   *  generates a textual view with a specific appendable output;
   * @param model to be rendered and represented
   * @param out where outputs get appended
   */
  public ReversiTextualView(ReversiModel model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  @Override
  public void render() throws IOException {
    int radius = this.model.getSize();
    int row;

    for (int r = -radius; r <= radius; r++) {
      row = Math.abs(r);
      int rMin = Math.max(-radius, -r - radius);
      int rMax = Math.min(radius, -r + radius);

      for (int i = 0; i < row; i++) {
        this.out.append(" ");
      }

      for (int q = rMin; q <= rMax; q++) {
        int s = -r - q;

        TeamColor color = this.model.getPieceAt(new HexPosition(q, r, s));
        if (color == null) {
          this.out.append("_");
        }
        else {
          this.out.append(color.symbol);
        }
        if (q != rMax) {
          this.out.append(" ");
        }
      }
      if (r != radius) {
        this.out.append(System.lineSeparator());
      }
    }
  }

  @Override
  public void writeMessage(String message) throws IOException {
    this.out.append(message);
  }
}
