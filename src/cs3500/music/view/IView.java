package cs3500.music.view;

import java.awt.event.KeyListener;

public interface IView {
  /**
   * Make the view visible. This is usually called
   * after the view is constructed. See classes that implement this interface for specifics.
   */
  void makeVisible();

  /**
   * this is to force the view to have a method to set up the keyboard.
   * The name has been chosen deliberately. This is the same method signature to
   * add a key listener in Java Swing.
   *
   * Thus our Swing-based implementation of this interface will already have such a
   * method.
   * @param listener given listener.
   */
  void addKeyListener(KeyListener listener);

  /**
   * Signal the view to refresh itself.
   */
  void refresh(Integer beat);

}
