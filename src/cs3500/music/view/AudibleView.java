package cs3500.music.view;

import java.util.List;
import java.util.function.Consumer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.IPlayerModel;
import cs3500.music.note.INote;
import cs3500.music.util.Utils;

public class AudibleView implements IView {

  private IPlayerModel model;
  private Synthesizer synthesizer;
  private Receiver receiver;

  public AudibleView(IPlayerModel model) {
    this.model = model;
    try {
      this.synthesizer = MidiSystem.getSynthesizer();
      this.receiver = synthesizer.getReceiver();
      this.synthesizer.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void showErrorMessage(String error) {

  }

  @Override
  public void refresh(Integer beat) {
    List<INote> notesToPlay = this.model.getBeat(beat);
    for (INote note : notesToPlay) {
      try {
        ShortMessage msgOn = new ShortMessage(ShortMessage.NOTE_ON, note.getInstrument(), Utils
                .noteToInteger(note), note.getVolume());
        ShortMessage msgOff = new ShortMessage(ShortMessage.NOTE_OFF, note.getInstrument(), Utils
                .noteToInteger(note), note.getVolume());

        this.receiver.send(msgOn, -1);
        this.receiver.send(msgOff, this.model.getTempo()*note.getDuration());
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }

    }

  }

}
