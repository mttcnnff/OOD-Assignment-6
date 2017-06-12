package cs3500.music.model.song;

import java.util.List;
import java.util.Map;

import cs3500.music.model.note.INote;

public interface ISong {

  String toString();

  Map<Integer, List<INote>> readSong();

  Boolean addNote(Integer beat, INote note);

  Boolean removeNote(Integer beat, INote note);

  Boolean editNoteDuration(Integer beat, INote note, Integer newDuration);

  void concat(ISong newSong);

  void combine(ISong newSong);
}
