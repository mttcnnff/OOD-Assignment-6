package cs3500.music.model.note;

import java.util.Objects;

import cs3500.music.model.pitch.IPitch;
import cs3500.music.model.pitch.Pitch;

/**
 * Note class used to represent a musical note being played in piece of music.
 */
public class Note implements Comparable<INote>, INote {
  private IPitch pitch;
  private Integer octave;
  private String instrument;
  private Integer duration;

  /**
   * Constructor used only by this class's builder
   *
   * @param b builder used to create this instance.
   */
  private Note(Builder b) {
    Objects.requireNonNull(b.pitch, "Pitch cannot be null.");
    if (b.octave < 0) {
      throw new IllegalArgumentException();
    }
    if (b.duration < 1) {
      throw new IllegalArgumentException();
    }
    this.duration = b.duration;
    this.instrument = b.instrument;
    this.pitch = b.pitch;
    this.octave = b.octave;
  }

  /**
   * @return string representation of this note (i.e. A0 or A#0).
   */
  @Override
  public String toString() {
    return this.pitch.toString() + this.octave.toString();
  }

  /**
   * @return this notes duration.
   */
  public Integer getDuration() {
    return this.duration;
  }

  /**
   * Changes this notes duration to the given one.
   * The duration must be > 1.
   *
   * @param newDur newly desired duration for this note.
   */
  public void editDuration(Integer newDur) {
    if (newDur < 1) {
      throw new IllegalArgumentException("Bad Duration.");
    }
    this.duration = newDur;
  }

  public Note getTone() {
    return new Builder().pitch(this.pitch).octave(this.octave).build();
  }

  public Integer getRank() {
    return this.pitch.getRank() + this.octave;
  }

  public Note nextHighestTone() {
    Pitch newPitch = pitch.getNextHighest();
    if (newPitch.equals(Pitch.C)) {
      return new Builder().pitch(newPitch).octave(this.octave + 1).build();
    } else {
      return new Builder().pitch(newPitch).octave(this.octave).build();
    }
  }

  /**
   * @return string representation of this note's instrument.
   */
  public String getInstrument() {
    return this.instrument;
  }

  /**
   * Compare's this note's and the note given, duration.
   * The shorter duration note should come before the longer.
   *
   * @param that other note to compare.
   * @return difference of their duration.
   */
  public Integer compareDuration(INote that) {
    return this.duration - that.getDuration();
  }

  /**
   * Overriden compareTo method to compare note's by tone.
   * (lowest to highest tone).
   *
   * @param o other note to compare.
   * @return this's tone compared to o's tone.
   */
  @Override
  public int compareTo(INote o) {
    return this.getRank() - o.getRank();
  }

  /**
   * Overriden equals method, leaves out duration in operation.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Note note = (Note) o;

    if (pitch != note.pitch) return false;
    if (octave != null ? !octave.equals(note.octave) : note.octave != null) return false;
    return instrument != null ? instrument.equals(note.instrument) : note.instrument == null;
  }

  /**
   * Overriden hashCode method, leaves out duration in operation.
   */
  @Override
  public int hashCode() {
    int result = pitch != null ? pitch.hashCode() : 0;
    result = 31 * result + (octave != null ? octave.hashCode() : 0);
    result = 31 * result + (instrument != null ? instrument.hashCode() : 0);
    return result;
  }

  /**
   * Builder class for Note.
   */
  public static class Builder {
    private IPitch pitch;
    private Integer octave;
    private String instrument = "default";
    private Integer duration = 1;

    public Builder pitch(IPitch pitch) {
      this.pitch = pitch;
      return this;
    }

    public Builder octave(Integer octave) {
      this.octave = octave;
      return this;
    }

    public Builder instrument(String instrument) {
      this.instrument = instrument;
      return this;
    }

    public Builder duration(Integer duration) {
      this.duration = duration;
      return this;
    }

    public Note build() {
      return new Note(this);
    }

  }

}
