package cs3500.reversi.model;

public interface ReversiModel {

  public void startGame(int radius);

  public void addPiece(TeamColor piece, HexPosition posn);


  //returns null if empty
  public TeamColor getPieceAt(HexPosition posn);

  public TeamColor getCurrentTurn();

  public boolean isGameOver();

  public int getRadius();



}
