package cs3500.reversi.strategy;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.reversi.model.HexPosition;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.TeamColor;

/**
 * Class to represent the strategy for getting the user inputs.
 * It prompts to the terminal and will respond with the position its given.
 * There is also error handling for when there are issues interpreting coordinates.
 */
@Deprecated
public class UserTextInput implements ReversiStrategy {
  Scanner input; //where the inputs are from

  /**
   * Generate a usertextinput strategy.
   * @param input The input from which to read the coordinates
   */
  public UserTextInput(Scanner input) {
    this.input = input;
  }

  @Override
  public HexPosition choosePosn(ReadonlyReversiModel model, TeamColor color) {
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
