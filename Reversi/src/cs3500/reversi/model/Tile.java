package cs3500.reversi.model;

/**
 * Interface to represent a hexagonal tile of a board game.
 */
public interface Tile {

  public HexPosition getPosition();

  public State getState();


}
