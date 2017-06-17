package cs3500.music.mocks;

import java.util.ArrayList;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MockMidiReceiver implements Receiver {

  ArrayList<ShortMessage> log;

  MockMidiReceiver() {
    this.log = new ArrayList<>();
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage msg = (ShortMessage) message;
    this.log.add(msg);
  }

  @Override
  public void close() {

  }

  public ArrayList<ShortMessage> getLog() {
    return this.log;
  }
}
