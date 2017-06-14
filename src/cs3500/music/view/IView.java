package cs3500.music.view;

import java.util.function.Consumer;

import cs3500.music.model.IPlayerModel;

public interface IView {
  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  void makeVisible();

//  /**
//   * Provide the view with a callback option to
//   * process a command.
//   * @param callback object
//   */
//  void setCommandCallback(Consumer<String> callback);



  /**
   * Transmit an error message to the view, in case
   * the command could not be processed correctly
   * @param error
   */
  void showErrorMessage(String error);

  /**
   * Signal the view to draw itself
   */
  void refresh(Integer beat);

}
