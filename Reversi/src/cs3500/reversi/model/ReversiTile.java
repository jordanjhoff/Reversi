package cs3500.reversi.model;

public class ReversiTile implements Tile {
  HexPosition pos;
  State state;

  public ReversiTile(HexPosition pos, State state) {
    this.pos = pos;
    this.state = state;
  }


  @Override
  public HexPosition getPosition() {
    return null;
  }

  @Override
  public State getState() {
    return null;
  }
}
