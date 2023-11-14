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

  @Override
  public HexPosition choosePosn(ReadonlyHexReversiModel model, TeamColor color) {
    System.out.println("Enter q, r, and s coordinates");
    int[] nums = new int[3];
    int i = 0;
    while (input.hasNext()) {
      try {
        nums[i] = Integer.parseInt(input.next());
        i++;
      } catch (NumberFormatException e) {
        System.out.println("Unable to read coordinate, please try again");
      }
    }
    try {
      HexPosition pos = new HexPosition(nums[0], nums[1], nums[2]);
      return pos;
    } catch (IllegalArgumentException e) {
      System.out.println("invalid coordinates");
      return choosePosn(model, color);
    }
  }
}
