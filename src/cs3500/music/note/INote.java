package cs3500.music.note;


import cs3500.music.pitch.Pitch;

public interface INote {

  String toString();

  Integer getDuration();

  void editDuration(Integer newDur);

  Note getTone();

  Note nextHighestTone();

  Integer getInstrument();

  Integer compareDuration(INote that);

  Integer toInteger();
}
