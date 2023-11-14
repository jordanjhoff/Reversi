package cs3500.reversi.strategy;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
    try {
      return new HexPosition(input.nextInt(), input.nextInt(), input.nextInt());
    } catch (Exception e) {
      if (e instanceof InputMismatchException) {
        System.out.println("Invalid input, please input three integers");
      }
      else if (e instanceof NoSuchElementException) {
        System.out.println("Could not read data");
      }
      else if (e instanceof IllegalStateException) {
        System.out.println("Scanner is closed");
      }
      else if (e instanceof IllegalArgumentException) {
        System.out.println("Invalid coordinates");
      }
      else {
        throw e;
      }
      input.next();
      return choosePosn(model, color);
    }
  }
}
