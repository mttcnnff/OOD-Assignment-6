import org.junit.Test;

import cs3500.music.notes.INote;
import cs3500.music.notes.Note;
import cs3500.music.pitch.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the Note class' functionality
 */
public class NoteTests {

  //Tests toString method with normal note
  @Test
  public void testToString() {
    INote A4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals("A4", A4.toString());
  }

  //Tests toString method with sharp note
  @Test
  public void testToString1() {
    INote aSharp4 = new Note.Builder().pitch(Pitch.ASHARP).octave(4).build();
    assertEquals("A#4", aSharp4.toString());
  }

  //Tests note builder with invalid duration (negative)
  @Test(expected = IllegalArgumentException.class)
  public void testBuilder() {
    INote A4 = new Note.Builder().pitch(Pitch.A).octave(4).duration(-1).build();
  }

  //Tests note builder with invalid octave (negative)
  @Test(expected = IllegalArgumentException.class)
  public void testBuilder1() {
    INote A4 = new Note.Builder().pitch(Pitch.A).octave(-1).build();
  }

  //Tests note getDuration
  @Test
  public void testGetDuration() {
    INote A4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals(0, (int)A4.getDuration());
    INote A5 = new Note.Builder().pitch(Pitch.A).octave(5).duration(5).build();
    assertEquals(5, (int)A5.getDuration());
  }

  //Tests editDuration with valid newDuration
  @Test
  public void testEditDuration() {
    INote A4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals(0, (int)A4.getDuration());
    A4.editDuration(3);
    assertEquals(3, (int)A4.getDuration());
  }

  //Tests editDuration with invalid newDuration (negative)
  @Test(expected = IllegalArgumentException.class)
  public void testEditDuration1() {
    INote A4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals(0, (int)A4.getDuration());
    A4.editDuration(-1);
    assertEquals(3, (int)A4.getDuration());
  }

  //Tests toInteger method
  @Test
  public void testToInteger() {
    INote A4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals(69, (int)A4.toInteger());

    INote A0 = new Note.Builder().pitch(Pitch.A).octave(0).build();
    assertEquals(21, (int)A0.toInteger());

    INote C8 = new Note.Builder().pitch(Pitch.C).octave(8).build();
    assertEquals(108, (int)C8.toInteger());
  }

  //Tests getInstrument method
  @Test
  public void testGetInstrument() {
    INote A4 = new Note.Builder().pitch(Pitch.A).octave(4).instrument(5).build();
    assertEquals(5, (int)A4.getInstrument());
  }

  //Tests getVolume method
  @Test
  public void testVolume() {
    INote A4 = new Note.Builder().pitch(Pitch.A).octave(4).instrument(5).volume(10).build();
    assertEquals(10, (int)A4.getVolume());
  }

}
