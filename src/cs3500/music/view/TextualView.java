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
    this.refresh();
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
  public void refresh() {
    this.song = this.model.getSong();
    this.toneCount = this.model.getToneCount();
    this.length = this.model.getLength();
  }

  /**
   * Prints song contained in this model to console according to the specs found in Assignment 5
   */
  private String getText() {
    StringBuilder result = new StringBuilder();

    TreeSet<INote> presentTones = new TreeSet<>(this.toneCount.keySet());
    if (!presentTones.isEmpty()) {

      ArrayList<String> firstRow = new ArrayList<>();
      HashMap<INote, Integer> colMap = new HashMap<>();
      int firstColPad = this.length.toString().length();


      INote lowestTone = presentTones.first();
      INote highestTone = presentTones.last();
      INote currTone = lowestTone;
      firstRow.add(padNumber(" ", firstColPad));
      int count = 1;
      while (!currTone.equals(highestTone.nextHighestTone())) {
        colMap.put(currTone, count);
        count++;
        firstRow.add(padNoteName(currTone));
        currTone = currTone.nextHighestTone();
      }

      String[] fr = firstRow.toArray(new String[firstRow.size()]);
      Integer rows = this.length + 1;
      Integer cols = firstRow.size();


      String[][] printMap = new String[rows][cols];
      printMap[0] = fr;

      for (Integer i = 1; i < rows; i++) {
        printMap[i] = rowFill(i, cols, firstColPad);
      }

      for (Integer beat : this.song.keySet()) {
        for (INote note : this.song.get(beat)) {
          printMap[beat + 1][colMap.get(note.getTone())] = padNote("X");
          for (int i = 1; i < note.getDuration(); i++) {
            printMap[beat + 1 + i][colMap.get(note.getTone())] = padNote("|");
          }
        }
      }

      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          if (printMap[i][j] != null) {
            result.append(printMap[i][j]);
          }
        }
        result.append("\n");
      }

      result.deleteCharAt(result.length() - 1);

    } else {
      result.append("");
    }

    return result.toString();

  }

  //PRIVATE METHODS:

  private String[] rowFill(Integer row, Integer width, Integer padding) {
    String[] result = new String[width];
    Arrays.fill(result, "     ");
    String num = padNumber(String.valueOf(row - 1), padding);
    result[0] = num;
    return result;
  }

  private String padNumber(String num, Integer padding) {
    return String.format("%1$" + padding + "s", num);
  }

  private String padNoteName(INote note) {
    return String.format("%1$" + 4 + "s ", note.toString());
  }

  private String padNote(String note) {
    return String.format("  %s  ", note);
  }

}
