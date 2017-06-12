package cs3500.music.model;

import java.util.List;
import java.util.Objects;

import cs3500.music.model.song.Song;
import cs3500.music.model.note.Note;

public class PlayerModel implements IPlayerModel {
  private Song song;

  public PlayerModel(Integer measure) {
    this.song = new Song(measure);
  }

  @Override
  public String print() {
    return this.song.display();
  }

  @Override
  public void addNote(Integer beat, Note note) throws IllegalArgumentException {
    if (!this.song.addNote(beat, note)) {
      throw new IllegalArgumentException("Not already exists at that beat.");
    }
  }

  @Override
  public void removeNote(Integer beat, Note note) {
    if (!this.song.removeNote(beat, note)) {
      throw new IllegalArgumentException("Trying to remove note that doesn't exist.");
    }
  }

  @Override
  public void editNoteDuration(Integer beat, Note note, Integer newDuration) {
    if (!this.song.editNoteDuration(beat, note, newDuration)) {
      throw new IllegalArgumentException("Trying to edit not which doesn't exist.");
    }
  }

  @Override
  public void combine(Song otherSong) {
    Objects.requireNonNull(otherSong, "Can't combine with null song.");
    this.song.combine(otherSong);
  }

  @Override
  public void concat(Song otherSong) {
    Objects.requireNonNull(otherSong, "Can't concat with null song.");
    this.song.concat(otherSong);

  }

  @Override
  public List<Note> getBeat(Integer beat) {
    return this.song.getBeat(beat);
  }
}
