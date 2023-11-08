package cs3500.reversi.strategy;

import java.util.Scanner;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyHexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TeamColor;

public class UserTextInput implements ReversiStrategy {
  Scanner input;

  public UserTextInput(Scanner input) {
    this.input = input;
  }

  //shitty temp implementation
  @Override
  public HexPosition choosePosn(ReadonlyHexReversiModel model, TeamColor color) {
    System.out.println("Enter q, r, and s coordinates");
    int q = input.nextInt();
    int r = input.nextInt();
    int s = input.nextInt();
    return new HexPosition(q, r, s);
  }
}
