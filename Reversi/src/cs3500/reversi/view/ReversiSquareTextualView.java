package cs3500.reversi.view;


import java.io.IOException;
import cs3500.reversi.model.Position;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.SquarePos;
import cs3500.reversi.model.TeamColor;

/**
 * Textual implementation of a square reversi view. Outputs will
 * be added to the appendable out to be viewed. Possible moves
 * are highlighted by the *.
 */
public class ReversiSquareTextualView implements IReversiView {

  private Appendable out;
  private ReversiModel model;
  private MoveFeatures features;

  /**
   * generates a textual view with generic stringbuilder output.
   *
   * @param model to be represented and rendered
   */
  public ReversiSquareTextualView(ReversiModel model) {
    this(model, System.out);
  }

  /**
   * Generates a textual view with a specific appendable output.
   *
   * @param model to be rendered and represented
   * @param out where outputs get appended
   */
  public ReversiSquareTextualView(ReversiModel model, Appendable out) {
    this.model = model;
    this.out = out;
  }


  @Override
  public void setVisible(boolean show) {
    //does nothing
  }

  @Override
  public void renderView(TeamColor color) {
    int size = this.model.getSize();
    int row;

    for (int r = 0; r < size; r++) {
      try {
        for (int c = 0; c < size; c++) {
          Position piece = new SquarePos(r, c);
          if (this.model.getPieceAt(piece) == null) {
            if (model.getValidMoves().contains(piece)) {
              this.out.append("*"); //available move = *
            } else {
              this.out.append("_");
            }
          } else {
            this.out.append(this.model.getPieceAt(piece).getSymbol());
          }
          this.out.append(" ");
        }
        this.out.append(System.lineSeparator());
      } catch (IOException e) {
        throw new IllegalStateException("Append failed");
      }
    }
  }

  @Override
  public void addFeatureListener(MoveFeatures features) {
    this.features = features;
  }

  @Override
  public void enableMoves(boolean enable) {
    //does nothing
  }

  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }
}