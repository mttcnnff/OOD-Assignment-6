package cs3500.music;

import java.io.FileReader;
import java.io.IOException;

import cs3500.music.model.IPlayerModel;
import cs3500.music.model.PlayerModel;
import cs3500.music.note.INote;
import cs3500.music.song.ISong;
import cs3500.music.song.Song;
import cs3500.music.util.MusicReader;
import cs3500.music.util.Utils;
import cs3500.music.view.IView;
import cs3500.music.view.ViewFactory;

public class MusicEditor {
  public static void main(String[] args) {
    String filename = "mary-little-lamb.txt";

    IPlayerModel model = new PlayerModel(4);
    model.readInSong(filename);
    IView consoleView = ViewFactory.makeView("visual", model);
    consoleView.makeVisible();

    for (Integer i = 21; i < 128; i++) {
      INote note = Utils.integerToNote(i);
      String out = i.toString() + " -> " + note.toString() + " -> " + Utils.noteToInteger(note)
              .toString();
      System.out.println(out);
    }


  }
}
