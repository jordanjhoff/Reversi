package cs3500.reversi.controller;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;
import cs3500.reversi.strategy.ReversiStrategy;

public class PlayerImpl implements Player {
  private final TeamColor color;
  private ReversiStrategy strategy;

  public PlayerImpl(TeamColor color, ReversiStrategy strategy) {
    this.color = color;
    this.strategy = strategy;
  }

  @Override
  public HexPosition play(ReadonlyHexReversiModel model) {
    return this.strategy.choosePosn(model, this.color);
  }

  @Override
  public TeamColor getColor() {
    return this.color;
  }
}
