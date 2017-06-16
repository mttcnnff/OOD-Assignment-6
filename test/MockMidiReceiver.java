import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class MockMidiReceiver implements Receiver {

  StringBuilder log;

  MockMidiReceiver() {
    this.log = new StringBuilder();
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    this.log.append(message.toString());
  }

  @Override
  public void close() {

  }

  public String getLog() {
    return this.log.toString();
  }
}
