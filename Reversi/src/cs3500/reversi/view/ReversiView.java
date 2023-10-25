package cs3500.reversi.view;

import java.io.IOException;

public interface ReversiView {

  /**
   * Renders a Reversi model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if board cannot be rendered
   */
  void render() throws IOException;

  /**
   * Prints a message.
   *
   * @param message message to be printed
   * @throws IllegalStateException if message cant be sent
   */
  void writeMessage(String message) throws IOException;
}
