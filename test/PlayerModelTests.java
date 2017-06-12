import org.junit.Test;

import cs3500.music.model.IPlayerModel;
import cs3500.music.model.PlayerModel;
import cs3500.music.model.song.Song;
import cs3500.music.model.note.Note;
import cs3500.music.model.pitch.Pitch;

import static org.junit.Assert.assertEquals;

public class PlayerModelTests {

  //**Most of these tests test print as well**

  //Tests adding several notes
  @Test
  public void TestAdd() {
    IPlayerModel model = new PlayerModel(4);
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    Note b4 = new Note.Builder().pitch(Pitch.B).octave(0).duration(3).build();
    Note d4 = new Note.Builder().pitch(Pitch.D).octave(0).duration(6).build();
    model.addNote(0, a4);
    model.addNote(0, b4);
    model.addNote(0, c4);
    model.addNote(0, d4);
    model.addNote(1, c4);
    String expected = "   C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0 \n" +
            "0  X         X                                  X         X  \n" +
            "1  X         |                                  |         |  \n" +
            "2  |         |                                            |  \n" +
            "3  |         |                                               \n" +
            "4            |                                               \n" +
            "5            |                                               ";
    assertEquals(expected, model.print());
  }

  //Tests trying to add same note at same beat from same instrument
  @Test(expected = IllegalArgumentException.class)
  public void TestAdd1() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    model.addNote(0, a4);
    model.addNote(0, a4);
  }

  //Tests trying to add same note at same beat from same instrument with different duration
  @Test(expected = IllegalArgumentException.class)
  public void TestAdd3() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    Note a41 = new Note.Builder().pitch(Pitch.A).octave(0).duration(3).build();
    model.addNote(0, a4);
    model.addNote(0, a41);
    assertEquals(true, a4.equals(a41));
  }

  //Tests trying to add same note at same beat with different instruments
  @Test
  public void TestAdd4() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    Note a41 = new Note.Builder().pitch(Pitch.A).octave(0).duration(3).instrument("guitar").build();
    model.addNote(0, a4);
    model.addNote(0, a41);
    assertEquals(false, a4.equals(a41));
  }

  //Tests removing note that was just added
  @Test
  public void TestRemove1() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    model.addNote(0, a4);
    model.removeNote(0, a4);
    assertEquals("", model.print());
  }

  //Tests removing several notes
  @Test
  public void TestRemove3() {
    IPlayerModel model = new PlayerModel(4);
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    Note b4 = new Note.Builder().pitch(Pitch.B).octave(0).duration(3).build();
    Note d4 = new Note.Builder().pitch(Pitch.D).octave(0).duration(6).build();
    model.addNote(0, a4);
    model.addNote(0, b4);
    model.addNote(0, c4);
    model.addNote(0, d4);
    model.addNote(1, c4);
    model.removeNote(0, a4);
    model.removeNote(0, b4);
    model.removeNote(0, c4);
    model.removeNote(0, d4);
    model.removeNote(1, c4);
    assertEquals("", model.print());
  }

  //Tests removing note that doesn't exist at beat (different instrument)
  @Test(expected = IllegalArgumentException.class)
  public void TestRemove4() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    Note a41 = new Note.Builder().pitch(Pitch.A).octave(0).duration(3).instrument("guitar").build();
    model.addNote(0, a4);
    model.removeNote(0, a41);
  }

  //Tests removing note that doesn't exist at beat (different note)
  @Test(expected = IllegalArgumentException.class)
  public void TestRemove5() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    Note b4 = new Note.Builder().pitch(Pitch.B).octave(0).duration(3).build();
    model.addNote(0, a4);
    model.removeNote(0, b4);
  }

  //Tests removing from beat that doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void TestRemove6() {
    IPlayerModel model = new PlayerModel(4);
    Note b4 = new Note.Builder().pitch(Pitch.B).octave(0).duration(3).build();
    model.removeNote(0, b4);
  }

  //Tests removing same note from beat but different instrument
  @Test
  public void TestRemove7() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    Note a41 = new Note.Builder().pitch(Pitch.A).octave(0).duration(3).instrument("guitar").build();
    model.addNote(0, a4);
    model.addNote(0, a41);
    model.removeNote(0, a41);
    String expected = "   A0 \n" +
            "0  X  \n" +
            "1  |  ";
    assertEquals(expected, model.print());
  }

  //Tests editing note duration
  @Test
  public void TestEditDuration1() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(6).build();
    model.addNote(0, a4);
    model.editNoteDuration(0, a4, 2);
    String expected = "   A0 \n" +
            "0  X  \n" +
            "1  |  ";
    assertEquals(expected, model.print());
  }

  //Tests editing duration with invalid duration
  @Test(expected = IllegalArgumentException.class)
  public void TestEditDuration3() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(6).build();
    model.addNote(0, a4);
    model.editNoteDuration(0, a4, -1);
    String expected = "   A0 \n" +
            "0  X  \n" +
            "1  |  ";
    assertEquals(expected, model.print());
  }

  //Tests editing same notes of different instrument does affect both notes
  @Test
  public void TestEditDuration4() {
    IPlayerModel model = new PlayerModel(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(6).build();
    Note a41 = new Note.Builder().pitch(Pitch.A).octave(0).duration(3).instrument("guitar").build();
    model.addNote(0, a4);
    model.addNote(0, a41);
    model.editNoteDuration(0, a4, 1);
    String expected = "   A0 \n" +
            "0  X  \n" +
            "1  |  \n" +
            "2  |  ";
    assertEquals(expected, model.print());
  }

  //Tests concat for a single note song
  @Test
  public void TestConcat() {
    IPlayerModel model = new PlayerModel(4);
    Song song = new Song(4);
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(6).build();
    model.addNote(0, a4);
    song.addNote(0, a4);
    model.concat(song);
    String expected = "    A0 \n" +
            " 0  X  \n" +
            " 1  |  \n" +
            " 2  |  \n" +
            " 3  |  \n" +
            " 4  |  \n" +
            " 5  |  \n" +
            " 6  X  \n" +
            " 7  |  \n" +
            " 8  |  \n" +
            " 9  |  \n" +
            "10  |  \n" +
            "11  |  ";
    assertEquals(expected, model.print());
  }

  //Tests concat for multiple notes
  @Test
  public void TestConcat1() {
    IPlayerModel model = new PlayerModel(4);
    Song song = new Song(4);
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    Note b4 = new Note.Builder().pitch(Pitch.B).octave(0).duration(3).build();
    Note d4 = new Note.Builder().pitch(Pitch.D).octave(0).duration(6).build();
    model.addNote(0, a4);
    model.addNote(0, b4);
    model.addNote(0, c4);
    model.addNote(0, d4);
    model.addNote(1, c4);
    song.addNote(0, a4);
    song.addNote(0, b4);
    song.addNote(0, c4);
    song.addNote(0, d4);
    song.addNote(1, c4);
    model.concat(song);
    String expected = "    C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0 \n" +
            " 0  X         X                                  X         X  \n" +
            " 1  X         |                                  |         |  \n" +
            " 2  |         |                                            |  \n" +
            " 3  |         |                                               \n" +
            " 4            |                                               \n" +
            " 5            |                                               \n" +
            " 6  X         X                                  X         X  \n" +
            " 7  X         |                                  |         |  \n" +
            " 8  |         |                                            |  \n" +
            " 9  |         |                                               \n" +
            "10            |                                               \n" +
            "11            |                                               ";
    assertEquals(expected, model.print());
  }

  //Tests trying to concat with null song
  @Test(expected = NullPointerException.class)
  public void TestConcat3() {
    IPlayerModel model = new PlayerModel(4);
    Song song = null;
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    model.addNote(0, c4);
    model.concat(song);
    String expected = "   C0 \n" +
            "0  X  \n" +
            "1  |  \n" +
            "2  |  \n" +
            "3  |  \n" +
            "4  |  \n" +
            "5  |  ";
    assertEquals(expected, model.print());
  }

  //Tests combining songs with same notes
  @Test
  public void TestCombine() {
    IPlayerModel model = new PlayerModel(4);
    Song song = new Song(4);
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    Note a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    Note b4 = new Note.Builder().pitch(Pitch.B).octave(0).duration(3).build();
    Note d4 = new Note.Builder().pitch(Pitch.D).octave(0).duration(6).build();
    model.addNote(0, a4);
    model.addNote(0, b4);
    model.addNote(0, c4);
    model.addNote(0, d4);
    model.addNote(1, c4);
    song.addNote(0, a4);
    song.addNote(0, b4);
    song.addNote(0, c4);
    song.addNote(0, d4);
    song.addNote(1, c4);
    model.combine(song);
    String expected = "   C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0 \n" +
            "0  X         X                                  X         X  \n" +
            "1  X         |                                  |         |  \n" +
            "2  |         |                                            |  \n" +
            "3  |         |                                               \n" +
            "4            |                                               \n" +
            "5            |                                               ";
    assertEquals(expected, model.print());
  }

  //Tests combing songs with different notes
  @Test
  public void TestCombine1() {
    IPlayerModel model = new PlayerModel(4);
    Song song = new Song(4);
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    Note d4 = new Note.Builder().pitch(Pitch.D).octave(0).duration(6).build();
    model.addNote(0, c4);
    song.addNote(0, d4);
    model.combine(song);
    String expected = "   C0  C#0   D0 \n" +
            "0  X         X  \n" +
            "1  |         |  \n" +
            "2  |         |  \n" +
            "3            |  \n" +
            "4            |  \n" +
            "5            |  ";
    assertEquals(expected, model.print());
  }

  //Tests combing notes with different lengths (should print longest note)
  @Test
  public void TestCombine3() {
    IPlayerModel model = new PlayerModel(4);
    Song song = new Song(4);
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    Note c41 = new Note.Builder().pitch(Pitch.C).octave(0).duration(6).build();
    model.addNote(0, c4);
    song.addNote(0, c41);
    model.combine(song);
    String expected = "   C0 \n" +
            "0  X  \n" +
            "1  |  \n" +
            "2  |  \n" +
            "3  |  \n" +
            "4  |  \n" +
            "5  |  ";
    assertEquals(expected, model.print());
  }

  //Tests combing notes with different lengths & different instruments
  @Test
  public void TestCombine4() {
    IPlayerModel model = new PlayerModel(4);
    Song song = new Song(4);
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    Note c41 = new Note.Builder().pitch(Pitch.C).octave(0).duration(6).instrument("guitar").build();
    model.addNote(0, c4);
    song.addNote(0, c41);
    model.combine(song);
    String expected = "   C0 \n" +
            "0  X  \n" +
            "1  |  \n" +
            "2  |  \n" +
            "3  |  \n" +
            "4  |  \n" +
            "5  |  ";
    assertEquals(expected, model.print());
  }

  //Tests trying to combine with null song
  @Test(expected = NullPointerException.class)
  public void TestCombine5() {
    IPlayerModel model = new PlayerModel(4);
    Song song = null;
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    model.addNote(0, c4);
    model.combine(song);
    String expected = "   C0 \n" +
            "0  X  \n" +
            "1  |  \n" +
            "2  |  \n" +
            "3  |  \n" +
            "4  |  \n" +
            "5  |  ";
    assertEquals(expected, model.print());
  }


  //Tests print adds padding to number column
  @Test
  public void TestPrint1() {
    IPlayerModel model = new PlayerModel(4);
    Note c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(15).build();
    model.addNote(0, c4);
    String expected = "    C0 \n" +
            " 0  X  \n" +
            " 1  |  \n" +
            " 2  |  \n" +
            " 3  |  \n" +
            " 4  |  \n" +
            " 5  |  \n" +
            " 6  |  \n" +
            " 7  |  \n" +
            " 8  |  \n" +
            " 9  |  \n" +
            "10  |  \n" +
            "11  |  \n" +
            "12  |  \n" +
            "13  |  \n" +
            "14  |  ";
    assertEquals(expected, model.print());
  }

}
