package cs3500.music.model.note;


public interface INote {

  String toString();

  Integer getDuration();

  void editDuration(Integer newDur);

  Note getTone();

  Note nextHighestTone();

  String getInstrument();

  Integer compareDuration(INote that);

  Integer getRank();

}
