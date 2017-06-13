package cs3500.music.pitch;

public interface IPitch {

  String toString();

  Integer getRank();

  Pitch getNextHighest();

}
