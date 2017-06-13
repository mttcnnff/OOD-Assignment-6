package cs3500.music.view;

import java.util.function.Consumer;

import cs3500.music.model.IPlayerModel;

public class AudibleView implements IView {

  private IPlayerModel model;

  public AudibleView(IPlayerModel model) {
    this.model = model;
  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void showErrorMessage(String error) {

  }

  @Override
  public void refresh() {

  }

}
