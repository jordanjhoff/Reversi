package cs3500.reversi.model;

import java.util.ArrayList;

public interface ReversiModel {

  void startGame(int radius);

  void addPiece(TeamColor piece, HexPosition posn);


  //returns null if empty
  TeamColor getPieceAt(HexPosition posn);

  TeamColor getCurrentTurn();

  boolean isGameOver();

  int getRadius();

  /**
   * Returns valid positions that can be played.
   * @param color a color
   * @return a list of valid positions that color can move to.
   */
  ArrayList<HexPosition> getValidMoves(TeamColor color);


}
