package cs3500.music.view.panels;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.IPlayerModel;
import cs3500.music.note.INote;
import cs3500.music.util.Utils;

public class PianoPanel extends JPanel {

  Map<Integer, Rectangle> whiteKeys;
  Map<Integer, Rectangle> blackKeys;
  List<INote> currentNotes;

  public PianoPanel(List<INote> currentNotes) {
    this.setBackground(Color.lightGray);
    this.currentNotes = currentNotes;

    //white key sequence
    whiteKeys = new TreeMap<>();
    Integer[] pattern = {1, 2, 2, 1, 2, 2, 2};
    Integer pIndex = 5;
    Integer i = 21;
    Integer whiteKeyOffset = 0;
    while (i < 110) {
      whiteKeys.put(i, new Rectangle(whiteKeyOffset*20, 0, 20, 200));
      pIndex = (pIndex + 1) % 7;
      i = i + pattern[pIndex];
      whiteKeyOffset++;
    }

    //black key sequence
    blackKeys = new TreeMap<>();
    blackKeys.put(22, new Rectangle(15, 0, 10, 100));
    Integer[] bPattern = {3, 2, 3, 2, 2};
    Integer[] blackKeyOffsets = {30, 10, 30, 10, 10};
    Integer bPIndex = 0;
    Integer j = 25;
    Integer previousKeyEndX = 25;
    while (j < 107) {
      Integer newKeyBeginX = previousKeyEndX + blackKeyOffsets[bPIndex];
      blackKeys.put(j, new Rectangle(newKeyBeginX,0,10,100));
      previousKeyEndX = newKeyBeginX + 10;
      bPIndex = (bPIndex + 1) % 5;
      j = j + bPattern[bPIndex];
    }
  }

  public void sendUpdate(List<INote> currentNotes) {
    this.currentNotes = currentNotes;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    BasicStroke stroke = new BasicStroke(1);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setStroke(stroke);


    for (Integer key : whiteKeys.keySet()) {
      Rectangle rect = whiteKeys.get(key);
      g2d.setColor(Color.white);
      if (currentNotes.contains(Utils.integerToNote(key))) {
        g2d.setColor(Color.yellow);
      }
      g2d.fill(rect);
      g2d.setColor(Color.black);
      g2d.draw(rect);
    }

    g2d.setColor(Color.black);

    for (Integer key : blackKeys.keySet()) {
      Rectangle rect = blackKeys.get(key);
      g2d.fill(rect);
      g2d.draw(rect);

    }


  }

}
