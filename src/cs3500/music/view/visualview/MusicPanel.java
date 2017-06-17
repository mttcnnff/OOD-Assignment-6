package cs3500.music.view.visualview;

import java.awt.*;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.model.IPlayerModel;

/**
 * Class for music panel of view.
 * This will show a piece of music and the live piano rendering of what is being played.
 */
public class MusicPanel extends JPanel {

  private NoteMapPanel noteMapPanel;
  private NoteListPanel noteListPanel;
  private Integer currBeat;

  /**
   * Constructor for MusicPanel.
   * @param model given model this panel will represent.
   */
  public MusicPanel(IPlayerModel model) {
    Objects.requireNonNull(model, "Null model passed in");
    this.setBackground(Color.DARK_GRAY);
    this.setLayout(new BorderLayout());
    this.currBeat = 0;

    this.noteMapPanel = new NoteMapPanel(model);
    JScrollPane scrollPane = new JScrollPane(this.noteMapPanel);
    scrollPane.setPreferredSize(new Dimension(500, 400));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setAutoscrolls(true);

    this.noteListPanel = new NoteListPanel(model);
    JScrollPane notesScrollPane = new JScrollPane(this.noteListPanel);
    notesScrollPane.setPreferredSize(new Dimension(50, 400));
    notesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    notesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    notesScrollPane.setAutoscrolls(true);

    this.add(scrollPane, BorderLayout.CENTER);
    this.add(notesScrollPane, BorderLayout.WEST);
  }

  /**
   * Refreshes this panel to the given beat.
   * @param beat desired beat to view.
   */
  public void refresh(Integer beat) {
    this.currBeat = beat;
    this.noteMapPanel.refresh(this.currBeat);
  }

}
