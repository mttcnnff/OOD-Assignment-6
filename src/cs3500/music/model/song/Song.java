package cs3500.music.model.song;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;

import cs3500.music.model.note.Note;

public class Song {
  private TreeMap<Integer, List<Note>> song;
  private HashMap<Note, Integer> toneCount;
  private Integer length;
  private Integer measure;
  private Comparator<Note> durationSort;

  /**
   * Constructor.
   *
   * @param measure desired measure of song.
   * @throws IllegalArgumentException if invalid measure is passed.
   */
  public Song(Integer measure) throws IllegalArgumentException {
    if (measure < 0) {
      throw new IllegalArgumentException("Invalid measure.");
    }
    this.song = new TreeMap<>();
    this.toneCount = new HashMap<>();
    this.measure = measure;
    this.length = 0;
    this.durationSort = (o1, o2) -> {
      int ans = o2.compareDuration(o1);
      return ans == 0 ? 1 : ans;
    };

  }

  /**
   * Gets a list of notes to play based on given beat.
   *
   * @param beat desired beat to play/reference.
   * @return READ-ONLY copy of given beat's notes.
   */
  public List<Note> getBeat(Integer beat) {
    return Collections.unmodifiableList(this.song.get(beat));
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
  public boolean addNote(Integer beat, Note note) {
    Objects.requireNonNull(note, "Note is null");
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number : " + beat.toString());
    }
    this.song.putIfAbsent(beat, new ArrayList<>());
    List<Note> beatNotes = this.song.get(beat);
    if (!beatNotes.contains(note)) {
      beatNotes.add(note);
      this.toneCount.putIfAbsent(note.getTone(), 0);
      this.toneCount.put(note.getTone(), this.toneCount.get(note.toString()) + 1);
    } else {
      return false;
    }
    beatNotes.sort(this.durationSort);
    this.updateLength();
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
  public boolean removeNote(Integer beat, Note note) {
    Objects.requireNonNull(note, "Notes are null");
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number.");
    }
    List<Note> beatNotes = this.song.get(beat);
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
  public boolean editNoteDuration(Integer beat, Note note, Integer newDuration) {
    List<Note> beatNotes = this.song.get(beat);
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
  public void concat(Song newSong) {
    Integer offset = this.length;
    for (Integer beat : newSong.song.keySet()) {
      List<Note> beatNotes = newSong.song.get(beat);
      for (Note note : beatNotes) {
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
  public void combine(Song newSong) {
    for (Integer beat : newSong.song.keySet()) {
      List<Note> oldNotes = this.song.get(beat);
      List<Note> beatNotes = newSong.song.get(beat);
      for (Note note : beatNotes) {
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

  /**
   * Prints song contained in this model as a string according to the specs found in Assignment 5
   *
   * @return String representation of the song this model contains.
   */
  public String display() {
    TreeSet<Note> presentTones = new TreeSet<>(this.toneCount.keySet());
    if (presentTones.isEmpty()) {
      return "";
    }
    ArrayList<String> firstRow = new ArrayList<>();
    HashMap<Note, Integer> colMap = new HashMap<>();
    int firstColPad = this.length.toString().length();


    Note lowestTone = presentTones.first();
    Note highestTone = presentTones.last();
    Note currTone = lowestTone;
    firstRow.add(padNumber(" ", firstColPad));
    int count = 1;
    while (!currTone.equals(highestTone.nextHighestTone())) {
      colMap.put(currTone, count);
      count++;
      firstRow.add(padNoteName(currTone));
      currTone = currTone.nextHighestTone();
    }

    String[] fr = firstRow.toArray(new String[firstRow.size()]);
    Integer rows = this.length + 1;
    Integer cols = firstRow.size();


    String[][] printMap = new String[rows][cols];
    printMap[0] = fr;

    for (Integer i = 1; i < rows; i++) {
      printMap[i] = rowFill(i, cols, firstColPad);
    }

    for (Integer beat : this.song.keySet()) {
      for (Note note : this.song.get(beat)) {
        printMap[beat + 1][colMap.get(note.getTone())] = padNote("X");
        for (int i = 1; i < note.getDuration(); i++) {
          printMap[beat + 1 + i][colMap.get(note.getTone())] = padNote("|");
        }
      }
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (printMap[i][j] != null) {
          result.append(printMap[i][j]);
        }
      }
      result.append("\n");
    }

    result.deleteCharAt(result.length() - 1);
    return result.toString();
  }

  //PRIVATE METHODS:

  private String[] rowFill(Integer row, Integer width, Integer padding) {
    String[] result = new String[width];
    Arrays.fill(result, "     ");
    String num = padNumber(String.valueOf(row - 1), padding);
    result[0] = num;
    return result;
  }

  private String padNumber(String num, Integer padding) {
    return String.format("%1$" + padding + "s", num);
  }

  private String padNoteName(Note note) {
    return String.format("%1$" + 4 + "s ", note.toString());
  }

  private String padNote(String note) {
    return String.format("  %s  ", note);
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
}
