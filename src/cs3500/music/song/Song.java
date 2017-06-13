package cs3500.music.song;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;

import cs3500.music.note.INote;
import cs3500.music.note.Note;
import cs3500.music.pitch.Pitch;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.Utils;

public class Song implements ISong {
  private Map<Integer, List<INote>> song;
  private Map<INote, Integer> toneCount;
  private Integer length;
  private Integer measure;
  private Integer tempo;
  public static Comparator<INote> durationSort = (o1, o2) -> {
    int ans = o2.compareDuration(o1);
    return ans == 0 ? 1 : ans;
  };

  public Song(Integer tempo) throws IllegalArgumentException {
    this.song = new TreeMap<>();
    this.toneCount = new HashMap<>();
    this.tempo = tempo;
    this.updateLength();
  }

  public Song(Builder b) throws IllegalArgumentException {
    this.song = b.builderSong;
    this.toneCount = b.builderToneCount;
    this.tempo = b.builderTempo;
    this.measure = b.builderMeasure;
    this.updateLength();
  }

  public Map<Integer, List<INote>> getSong() {
    TreeMap<Integer, List<INote>> songContents = new TreeMap<>();
    for (Integer beat : this.song.keySet()) {
      songContents.put(beat, Collections.unmodifiableList(this.song.get(beat)));
    }
    return Collections.unmodifiableMap(songContents);
  }

  @Override
  public Map<INote, Integer> getToneCount() {
    return Collections.unmodifiableMap(this.toneCount);
  }

  public List<INote> getBeat(Integer beat) {
    return Collections.unmodifiableList(this.song.get(beat));
  }

  @Override
  public Integer getLength() {
    this.updateLength();
    return this.length;
  }

  /**
   * Adds specified note at specified beat.
   * You cannot add duplicate notes!
   * A duplicate note is defined as a note with the same tone, octave, and instrument.
   * If you try to add an already existing note, an exception with be thrown.
   *
   * @param beat beat at which you want to add this note.
   * @param note note that you would like to add.
   */
  public Boolean addNote(Integer beat, INote note) {
    Objects.requireNonNull(note, "Note is null");
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number : " + beat.toString());
    }
    if (addNoteHelper(beat, note, this.song, this.toneCount)) {
      this.updateLength();
      return true;
    } else {
      return false;
    }
  }

  private static Boolean addNoteHelper(Integer beat, INote note, Map<Integer, List<INote>> song,
                                   Map<INote,
          Integer> toneCount) {
    Objects.requireNonNull(note, "Note is null");
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number : " + beat.toString());
    }
    song.putIfAbsent(beat, new ArrayList<>());
    List<INote> beatNotes = song.get(beat);

    if (!beatNotes.contains(note)) {
      beatNotes.add(note);
      toneCount.putIfAbsent(note.getTone(), 0);
      toneCount.put(note.getTone(), toneCount.get(note.getTone()) + 1);
    } else {
      return false;
    }
    beatNotes.sort(durationSort);
    return true;
  }

  /**
   * Removes specified note at the specified beat.
   * You cannot remove notes that do not exist at this beat!
   * You must address the note you want to remove by the correct tone, octave, and instrument.
   * If you try and remove a note which does not exist at this beat OR remove anything from a
   * beat which contains no notes an exception will be thrown.
   *
   * @param beat beat at which you want to add this note.
   * @param note note that you would like to remove.
   */
  public Boolean removeNote(Integer beat, INote note) {
    Objects.requireNonNull(note, "Notes are null");
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number.");
    }
    List<INote> beatNotes = this.song.get(beat);
    if (beatNotes != null && beatNotes.contains(note)) {
      beatNotes.remove(note);
      this.toneCount.put(note.getTone(), this.toneCount.get(note.getTone()) - 1);
      if (this.toneCount.get(note.getTone()) == 0) {
        this.toneCount.remove(note.getTone());
      }
    } else {
      return false;
    }
    beatNotes.sort(this.durationSort);
    if (beatNotes.isEmpty()) {
      this.song.remove(beat);
    }
    this.updateLength();
    return true;
  }

  /**
   * Edits specified notes duration with given duration.
   * You must address the note you want to edit by the correct tone, octave, and instrument.
   * Duration must be > 0.
   *
   * @param beat        beat at which you are editing.
   * @param note        note in this beat that you are editing.
   * @param newDuration new duration you wish to set.
   */
  public Boolean editNoteDuration(Integer beat, INote note, Integer newDuration) {
    List<INote> beatNotes = this.song.get(beat);
    if (beatNotes == null || newDuration < 1) {
      return false;
    }
    Integer index = beatNotes.indexOf(note);
    if (index != -1) {
      beatNotes.get(index).editDuration(newDuration);
    } else {
      return false;
    }
    beatNotes.sort(this.durationSort);
    this.updateLength();
    return true;
  }

  /**
   * Concatenates this model's song with the given song by appending it to the end.
   *
   * @param newSong song you wish to concatenate with current one in your model.
   */
  public void concat(ISong newSong) {
    Integer offset = this.length;
    Map<Integer, List<INote>> newSongContents = newSong.getSong();
    for (Integer beat : newSongContents.keySet()) {
      List<INote> beatNotes = newSongContents.get(beat);
      for (INote note : beatNotes) {
        this.addNote(beat + offset, note);
      }
    }
  }

  /**
   * Combines this model's song with given song by "overlaying" it from the start.
   * Notes of the same beat, tone, octave, and instrument will be combined according to which one
   * is longer.
   *
   * @param newSong song you wish to combine with current one in your model.
   */
  public void combine(ISong newSong) {
    Map<Integer, List<INote>> newSongContents = newSong.getSong();
    for (Integer beat : newSongContents.keySet()) {
      List<INote> oldNotes = this.song.get(beat);
      List<INote> beatNotes = newSongContents.get(beat);
      for (INote note : beatNotes) {
        Integer index = oldNotes.indexOf(note);
        if (index == -1) {
          this.addNote(beat, note);
        } else {
          int oldDur = oldNotes.get(index).getDuration();
          int newDur = note.getDuration() > oldDur ? note.getDuration() : oldDur;
          this.editNoteDuration(beat, note, newDur);
        }

      }
    }


  }

  private void updateLength() {
    this.length = 0;
    for (Integer beat : this.song.keySet()) {
      Integer beatDuration = this.song.get(beat).get(0).getDuration();
      if (beat + beatDuration > this.length) {
        this.length = beat + beatDuration;
      }
    }
  }

  /**
   * Placeholder method for measure for now.
   */
  private String printMeasure() {
    return this.measure.toString();
  }

  public static class Builder implements CompositionBuilder<ISong> {

    private Map<Integer, List<INote>> builderSong = new TreeMap<>();
    private Map<INote, Integer> builderToneCount = new HashMap<>();
    private Integer builderTempo;
    private Integer builderMeasure = 0;

    @Override
    public ISong build() {
      return new Song(this);
    }

    @Override
    public CompositionBuilder<ISong> setTempo(int tempo) {
      this.builderTempo = tempo;
      return this;
    }

    public CompositionBuilder<ISong> setMeasure(Integer measure) {
      this.builderMeasure = measure;
      return this;
    }

    @Override
    public CompositionBuilder<ISong> addNote(int start, int end, int instrument, int pitch, int
            volume) {
      Integer octave = Utils.integerToOctave(pitch);
      Pitch tone = Utils.integerToPitch(pitch);
      Integer duration = end - start;
      addNoteHelper(start, new Note.Builder().pitch(tone).octave(octave).instrument(instrument)
              .duration(duration).volume(volume).build(), this.builderSong, this.builderToneCount);
      return this;
    }
  }
}
