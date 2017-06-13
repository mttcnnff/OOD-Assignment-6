package cs3500.music.util;


import cs3500.music.note.INote;
import cs3500.music.note.Note;
import cs3500.music.pitch.Pitch;

public class Utils {

  public static INote integerToNote(Integer value) {
    return new Note.Builder().pitch(integerToPitch(value)).octave(integerToOctave(value))
            .build();
  }

  public static Integer integerToOctave(Integer value) {
    return (value - 12) / 12;
  }

  public static Pitch integerToPitch(Integer value) {
    return Pitch.values()[value % 12];
  }

  public static Integer noteToInteger(INote note) {
    return note.toInteger();
  }

}
