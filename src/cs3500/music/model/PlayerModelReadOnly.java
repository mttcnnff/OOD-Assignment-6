package cs3500.music.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cs3500.music.notes.INote;

public class PlayerModelReadOnly implements IPlayerModelReadOnly {

  private IPlayerModel readOnlyModel;

  public PlayerModelReadOnly(IPlayerModel model) {
    this.readOnlyModel = model;
  }


  @Override
  public Map<Integer, List<INote>> getSong() {
    return this.readOnlyModel.getSong();
  }

  @Override
  public TreeMap<Integer, Integer> getToneRange() {
    return this.readOnlyModel.getToneRange();
  }

  @Override
  public List<INote> getStartNotes(Integer beat) {
    return this.readOnlyModel.getStartNotes(beat);
  }

  @Override
  public List<INote> getPlayingNotes(Integer beat) {
    return this.readOnlyModel.getPlayingNotes(beat);
  }

  @Override
  public Integer getLength() {
    return this.readOnlyModel.getLength();
  }

  @Override
  public Integer getTempo() {
    return this.readOnlyModel.getTempo();
  }
}
