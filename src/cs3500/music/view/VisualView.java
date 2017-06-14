package cs3500.music.view;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.IPlayerModel;
import cs3500.music.note.INote;
import cs3500.music.view.panels.MusicPanel;
import cs3500.music.view.panels.PianoPanel;

public class VisualView extends JFrame implements IView {

  private IPlayerModel model;
  private JPanel baseMusicPanel;
  private PianoPanel pianoPanel;
  private MusicPanel musicPanel;
  List<INote> currentNotes;
  int[][] printMap;
  TreeMap<INote, Integer> noteRange;
  Boolean isPlaying;

  public VisualView(IPlayerModel model) {
    this.model = model;
    this.currentNotes = this.model.getBeatState(0);
    this.printMap = this.model.getPrintMap();
    this.noteRange = this.model.getToneRange();
    this.isPlaying = false;

    this.setTitle("Music Player");
    this.setSize(2000, 1000);
    this.setResizable(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    //music panel
    this.musicPanel = new MusicPanel(this.noteRange, this.printMap);
    this.add(musicPanel, BorderLayout.NORTH);

    //piano panel
    this.pianoPanel = new PianoPanel(this.currentNotes);
    this.pianoPanel.setPreferredSize(new Dimension(1040, 200));
    this.add(pianoPanel, BorderLayout.SOUTH);

    this.pack();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this,error,"Error",JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh(Integer beat) {
    this.currentNotes = this.model.getBeatState(beat);
    this.printMap = this.model.getPrintMap();
    this.noteRange = this.model.getToneRange();
    this.musicPanel.setBeat(beat);
    this.pianoPanel.sendUpdate(this.model.getBeatState(beat));
    this.repaint();
  }


}
