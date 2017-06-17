package cs3500.music;

import cs3500.music.controller.Controller;
import cs3500.music.model.IPlayerModel;
import cs3500.music.model.PlayerModel;
import cs3500.music.model.PlayerModelReadOnly;
import cs3500.music.view.IView;
import cs3500.music.view.ViewFactory;

public class MusicEditor {

  public static void main(String[] args) throws InterruptedException {
    IPlayerModel model = new PlayerModel(4);
    model.readInSong("mystery-2.txt");
    Controller controller = new Controller(model);
    IView consoleView = ViewFactory.makeView("visual", new PlayerModelReadOnly(model));
    controller.setView(consoleView);
    consoleView.makeVisible();
  }
}
