package cs3500.music.view.panels;

import java.awt.*;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.model.IPlayerModel;
import cs3500.music.note.INote;

public class MusicPanel extends JPanel {

  private IPlayerModel model;
  private TreeMap<INote, Integer> noteRange;
  NoteMapPanel noteMapPanel;
  int[][] printMap;
  Integer currBeat;


  public MusicPanel(TreeMap<INote, Integer> noteRange, int[][] printMap) {
    this.setBackground(Color.DARK_GRAY);
    this.setLayout(new BorderLayout());
    this.noteRange = noteRange;
    this.printMap = printMap;
    this.currBeat = 0;
    this.noteMapPanel = new NoteMapPanel(this.noteRange, this.printMap);

    this.add(noteMapPanel, BorderLayout.CENTER);
    this.add(new NoteListPanel(this.noteRange), BorderLayout.WEST);
  }

  public void sendUpdate(Integer beat) {
    this.currBeat = beat;
    this.noteMapPanel.setBeat(beat);
  }






}
