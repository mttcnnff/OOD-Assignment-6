package cs3500.music.view;

import cs3500.music.model.IPlayerModel;

public class ViewFactory {

  public static IView makeView(String type, IPlayerModel model) {
    switch (type) {
      case "console":
        return new TextualView(model);
      case "visual":
        return new VisualView(model);
      case "midi":
        return new AudibleView(model);
      default:
        throw new IllegalArgumentException("Invalid View requested: " + type + " doesn't exist.");
    }
  }

}
