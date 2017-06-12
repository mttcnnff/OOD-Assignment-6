package cs3500.music.model;

import java.util.List;

import cs3500.music.model.song.Song;
import cs3500.music.model.note.Note;

public interface IPlayerModel {
  /**
   * Prints song contained in this model as a string according to the specs found in Assignment 5
   *
   * @return String representation of the song this model contains.
   */
  String print();

  /**
   * Adds specified note at specified beat.
   * You cannot add duplicate notes!
   * A duplicate note is defined as a note with the same tone, octave, and instrument.
   * If you try to add an already existing note, an exception with be thrown.
   *
   * @param beat beat at which you want to add this note.
   * @param note note that you would like to add.
   */
  void addNote(Integer beat, Note note);

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
  void removeNote(Integer beat, Note note);

  /**
   * Edits specified notes duration with given duration.
   * You must address the note you want to edit by the correct tone, octave, and instrument.
   * Duration must be > 0.
   *
   * @param beat        beat at which you are editing.
   * @param note        note in this beat that you are editing.
   * @param newDuration new duration you wish to set.
   */
  void editNoteDuration(Integer beat, Note note, Integer newDuration);

  /**
   * Combines this model's song with given song by "overlaying" it from the start.
   * Notes of the same beat, tone, octave, and instrument will be combined according to which one
   * is longer.
   *
   * @param song song you wish to combine with current one in your model.
   */
  void combine(Song song);

  /**
   * Concatenates this model's song with the given song by appending it to the end.
   *
   * @param song song you wish to concatenate with current one in your model.
   */
  void concat(Song song);

  /**
   * Gets a list of notes to play based on given beat.
   *
   * @param beat desired beat to play/reference.
   * @return READ-ONLY copy of given beat's notes.
   */
  List<Note> getBeat(Integer beat);
}
