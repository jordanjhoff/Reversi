package cs3500.reversi.view;

import java.io.IOException;

import cs3500.reversi.model.ReversiModel;

public class ReversiTextualView implements ReversiView {

  private Appendable out;
  private ReversiModel model;

  public ReversiTextualView(ReversiModel model) {
    this(model, new StringBuilder());
  }

  public ReversiTextualView(ReversiModel model, Appendable out) {
    this.model = model;
    this.out = out;

    /*    example with radius of 5
         _ _ _ _ _ _
        _ _ _ _ _ _ _
       _ _ O O O _ _ _
      _ _ _ _ O _ _ _ _
     _ _ _ X X X _ _ _ _
    _ _ _ _ X _ X _ _ _ _
     _ _ _ O X O _ _ _ _
      _ _ O _ _ _ _ _ _
       _ _ _ _ _ _ _ _
        _ _ _ _ _ _ _
         _ _ _ _ _ _
     */
  }

  @Override
  public void render() throws IOException {
    int radius = model.getRadius();
    int row;
    int counter

    for (int r = -radius; r <= radius; r++) {
      row = Math.abs(r);
      int rMin = Math.max(-radius, -r - radius);
      int rMax = Math.min(radius, -r + radius);

      for (int i = 0; i < row; i++) {
        out.append(" ")
      }

      for (int q = rMin; q <= rMax; q++) {
        int s = -r - q;

        TeamColor color = model.getPieceAt(new HexPosition(q, r, s));
        if (color == null) {
          out.append("_");
        }
        else {
          out.append(color.symbol);
        }
        out.append(" ");
      }
    }
  }


  @Override
  public void writeMessage(String message) {
    out.append(message);
  }

}
