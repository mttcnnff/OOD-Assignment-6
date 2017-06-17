import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.sound.midi.ShortMessage;

import cs3500.music.mocks.MockMidiDevice;
import cs3500.music.model.IPlayerModel;
import cs3500.music.model.PlayerModel;
import cs3500.music.model.PlayerModelReadOnly;
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
    for (INote note : notes) {
      model.addNote(0, note);
    }
    IView audibleView = new AudibleView(new PlayerModelReadOnly(model), mockDevice);
    audibleView.refresh(0);
    ArrayList<ShortMessage> log = mockDevice.getLog();

    for (int i = 0; i < log.size(); i++) {
      INote note = notes.get(i / 2);
      ShortMessage msg = log.get(i);
      assertEquals((int) note.toInteger(), msg.getData1());
      assertEquals((int) note.getInstrument(), msg.getChannel());
      assertEquals((int) note.getVolume(), msg.getData2());
    }
  }

  @Test
  public void maryTestMIDI() {
    IPlayerModel model = new PlayerModel(4);
    MockMidiDevice mockDevice = new MockMidiDevice();
    model.readInSong("mary-little-lamb.txt");
    IView audibleView = new AudibleView(new PlayerModelReadOnly(model), mockDevice);

    audibleView.refresh(0);
    ArrayList<ShortMessage> log = mockDevice.getLog();
    StringBuilder logString = new StringBuilder();

    for (ShortMessage msg : log) {
      logString.append("Command:");
      logString.append(String.valueOf(msg.getCommand()));
      logString.append(" Instrument:");
      logString.append(String.valueOf(msg.getChannel()));
      logString.append(" MIDI Note:");
      logString.append(String.valueOf(msg.getData1()));
      logString.append(" Volume:");
      logString.append(String.valueOf(msg.getData2()));
      logString.append("\n");
    }

    try {
      writeToFile("midi-transcript.txt", logString.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(64, (int) model.getLength());
  }


  public static void writeToFile(String fileName, String textLine) throws IOException {
    FileWriter write = new FileWriter(fileName, false);
    PrintWriter println = new PrintWriter(write);

    println.print(textLine);
    println.close();
  }

  /**
   * Parses short message into string for comparison
   *
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
