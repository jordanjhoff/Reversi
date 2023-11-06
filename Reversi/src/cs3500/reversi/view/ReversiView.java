package cs3500.reversi.view;

import java.io.IOException;

/**
 * Represents a generic view for representing a reversi model.
 */
public interface ReversiView {

  /**
   * Renders a Reversi model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if board cannot be rendered
   * @throws IllegalStateException if model has not been started
   */
  void render() throws IOException;

  /**
   * renders a message.
   *
   * @param message message to be printed
   * @throws IOException if message cant be sent
   */
  void writeMessage(String message) throws IOException;
}
