package cs3500.music.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import cs3500.music.model.IPlayerModel;
import cs3500.music.note.INote;

public class TextualView implements IView {

  private IPlayerModel model;
  private Map<Integer, List<INote>> song;
  private Map<INote, Integer> toneCount;
  private Integer length;

  TextualView(IPlayerModel model) {
    this.model = model;
    this.refresh(0);
  }

  @Override
  public void makeVisible() {
    System.out.println(this.getText());

  }

  @Override
  public void showErrorMessage(String error) {
    System.out.println(error);
  }

  @Override
  public void refresh(Integer beat) {
    this.song = this.model.getSong();
    this.toneCount = this.model.getToneCount();
    this.length = this.model.getLength();
  }

  /**
   * Prints song contained in this model to console according to the specs found in Assignment 5
   */
  private String getText() {
    StringBuilder result = new StringBuilder();
    int[][] printMap1 = this.model.getPrintMap();
    int firstColPad = String.valueOf(printMap1.length).length();

    if (printMap1.length == 0) {
      result.append("");
      return result.toString();
    }

    result.append(padNumber(" ", firstColPad));
    for (INote note : this.model.getToneRange().keySet()) {
      result.append(padNoteName(note));
    }
    result.append("\n");

    for (Integer i = 0; i < printMap1.length; i++) {
      result.append(padNumber(i.toString(), firstColPad));
      for (Integer j = 0; j < printMap1[0].length; j++) {
        result.append(padNote(printMap1[i][j]));
      }

      result.append("\n");
    }
    return result.toString();
  }

  //PRIVATE METHODS:
  private String padNumber(String num, Integer padding) {
    return String.format("%1$" + padding + "s", num);
  }

  private String padNoteName(INote note) {
    return String.format("%1$" + 4 + "s ", note.toString());
  }

  private String padNote(int code) {
    switch (code) {
      case 0:
        return String.format("  %s  ", " ");
      case 1:
        return String.format("  %s  ", "|");
      case -1:
        return String.format("  %s  ", "X");
      default:
        throw new IllegalArgumentException("Bad note map!");

    }
  }

}
