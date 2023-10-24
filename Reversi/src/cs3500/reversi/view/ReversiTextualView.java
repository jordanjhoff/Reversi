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
  }

  @Override
  public void render() throws IOException {
    int radius = model.getRadius();

    for (int r = -radius; r <= radius; r++) {
      int rMin = Math.max(-radius, -r - radius);
      int rMax = Math.min(radius, -r + radius);
      for (int q = rMin; q <= rMax; q++) {
        int s = -r - q;

        System.out.println("Hexagon at (" + q + ", " + r + ", " + s + ")");
      }
    }

  }

  @Override
  public void writeMessage(String message) {

  }


}
