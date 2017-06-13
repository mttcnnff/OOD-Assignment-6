package cs3500.music.song;

import java.util.List;
import java.util.Map;

import cs3500.music.note.INote;

public interface ISong {

  Map<Integer, List<INote>> getSong();

  Map<INote, Integer> getToneCount();

  List<INote> getBeat(Integer beat);

  Integer getLength();

  Boolean addNote(Integer beat, INote note);

  Boolean removeNote(Integer beat, INote note);

  Boolean editNoteDuration(Integer beat, INote note, Integer newDuration);

  void concat(ISong newSong);

  void combine(ISong newSong);
}
