import org.junit.Test;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;

import javax.sound.midi.ShortMessage;

import cs3500.music.mocks.MockMidiDevice;
import cs3500.music.model.IPlayerModel;
import cs3500.music.model.PlayerModel;
import cs3500.music.notes.INote;
import cs3500.music.notes.Note;
import cs3500.music.pitch.Pitch;
import cs3500.music.view.AudibleView;
import cs3500.music.view.IView;

public class MIDITests {

  @Test
  public void MidiTest() {
    IPlayerModel model = new PlayerModel(4);
    MockMidiDevice mockDevice = new MockMidiDevice();

    ArrayList<INote> notes = new ArrayList<>();
    notes.add(new Note.Builder().pitch(Pitch.A).octave(0).instrument(0).volume(70).build());
    notes.add(new Note.Builder().pitch(Pitch.C).octave(5).instrument(1).volume(69).build());
    notes.add(new Note.Builder().pitch(Pitch.FSHARP).octave(4).instrument(1).volume(14).build());
    notes.add(new Note.Builder().pitch(Pitch.D).octave(6).instrument(3).volume(15).build());
    for(INote note : notes) {
      model.addNote(0, note);
    }
    IView audibleView = new AudibleView(model, mockDevice);
    audibleView.refresh(0);
    ArrayList<ShortMessage> log = mockDevice.getLog();

    for (int i = 0; i < log.size(); i++) {
      INote note = notes.get(i/2);
      ShortMessage msg = log.get(i);
      assertEquals((int)note.toInteger(), msg.getData1());
      assertEquals((int)note.getInstrument(), msg.getChannel());
      assertEquals((int)note.getVolume(), msg.getData2());
    }
  }

  /**
   * Parses short message into string for comparison
   * @param msg msg to parse.
   * @return String representation of msg.
   */
  private String parseShortMessage(ShortMessage msg) {
    Integer command = msg.getCommand();
    Integer channel = msg.getChannel();
    Integer note = msg.getData1();
    Integer volume = msg.getData2();
    return command.toString() + " " + channel.toString() + " " + note.toString() + " " +
    volume.toString() + "\n";
  }
}
