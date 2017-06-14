package cs3500.music.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import cs3500.music.note.INote;
import cs3500.music.song.ISong;
import cs3500.music.song.Song;
import cs3500.music.util.MusicReader;

public class PlayerModel implements IPlayerModel {
  private ISong song;

  public PlayerModel(Integer measure) {
    this.song = new Song(measure);
  }

  public PlayerModel(ISong song) { this.song = song; }

  @Override
  public String print() {
    return this.song.toString();
  }

  @Override
  public void addNote(Integer beat, INote note) throws IllegalArgumentException {
    if (!this.song.addNote(beat, note)) {
      throw new IllegalArgumentException("Not already exists at that beat.");
    }
  }

  @Override
  public void removeNote(Integer beat, INote note) {
    if (!this.song.removeNote(beat, note)) {
      throw new IllegalArgumentException("Trying to remove note that doesn't exist.");
    }
  }

  @Override
  public void editNoteDuration(Integer beat, INote note, Integer newDuration) {
    if (!this.song.editNoteDuration(beat, note, newDuration)) {
      throw new IllegalArgumentException("Trying to edit not which doesn't exist.");
    }
  }

  @Override
  public void combine(ISong otherSong) {
    Objects.requireNonNull(otherSong, "Can't combine with null song.");
    this.song.combine(otherSong);
  }

  @Override
  public void concat(ISong otherSong) {
    Objects.requireNonNull(otherSong, "Can't concat with null song.");
    this.song.concat(otherSong);

  }

  @Override
  public Map<Integer, List<INote>> getSong() {
    return this.song.getSong();
  }

  @Override
  public Map<INote, Integer> getToneCount() {
    return this.song.getToneCount();
  }

  @Override
  public TreeMap<INote, Integer> getToneRange() {
    return this.song.getToneRange();
  }

  @Override
  public int[][] getPrintMap() {
    return this.song.getPrintMap();
  }

  @Override
  public List<INote> getBeatState(Integer beat) {
    return this.song.getBeatState(beat);
  }

  @Override
  public List<INote> getBeat(Integer beat) {
    return this.song.getBeat(beat);
  }

  @Override
  public Integer getLength() {
    return this.song.getLength();
  }

  @Override
  public Integer getTempo() {
    return this.song.getTempo();
  }

  @Override
  public void readInSong(String filename) {
    try (FileReader fileReader = new FileReader(filename)){
      this.song = MusicReader.parseFile(fileReader, new Song.Builder());
    } catch (IOException e){
      e.printStackTrace();
    }
  }


}
