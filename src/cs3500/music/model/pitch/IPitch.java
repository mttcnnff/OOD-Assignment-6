package cs3500.music.model.pitch;

public interface IPitch {

  String toString();

  Integer getRank();

  Pitch getNextHighest();

}
