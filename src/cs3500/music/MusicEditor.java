package cs3500.music;


import java.util.List;

import cs3500.music.model.IPlayerModel;
import cs3500.music.model.PlayerModel;
import cs3500.music.note.INote;
import cs3500.music.view.IView;
import cs3500.music.view.ViewFactory;

public class MusicEditor {


  public static void main(String[] args) throws InterruptedException {
    String filename = "mary-little-lamb.txt";
    IPlayerModel model = new PlayerModel(4);
    model.readInSong(filename);
    IView consoleView = ViewFactory.makeView("visual", model);
    consoleView.makeVisible();
    consoleView.refresh(3);
  }
}
