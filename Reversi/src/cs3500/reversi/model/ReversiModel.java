package cs3500.reversi.model;

public interface ReversiModel {

  public void startGame(int radius);

  public void addPiece(State tile, HexPosition posn);

  public State getPiece(HexPosition posn);

  public List<HexPosition> getValidMoves(State piece);

  public boolean isGameOver();


}
