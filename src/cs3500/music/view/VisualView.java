package cs3500.music.view;

import java.awt.*;

import javax.swing.*;
import cs3500.music.model.IPlayerModel;
import cs3500.music.view.panels.MusicPanel;
import cs3500.music.view.panels.PianoPanel;

public class VisualView extends JFrame implements IView {

  private PianoPanel pianoPanel;
  private MusicPanel musicPanel;

  /**
   * Constructor for visual view.
   * @param model given model this view is going to represent.
   */
  public VisualView(IPlayerModel model) {

    //main frame
    this.setTitle("Music Player");
    this.setSize(2000, 700);
    this.setResizable(false);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    //music panel
    this.musicPanel = new MusicPanel(model);
    this.add(musicPanel, BorderLayout.NORTH);

    //piano panel
    this.pianoPanel = new PianoPanel(model);
    this.pianoPanel.setPreferredSize(new Dimension(1040, 200));
    this.add(pianoPanel, BorderLayout.SOUTH);

    this.pack();
  }

  /**
   * Displays the visual view.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
    this.refresh(0);
  }

  /**
   * Refreshes this visual view to specified beat.
   * @param beat desired beat to view song at.
   */
  @Override
  public void refresh(Integer beat) {
    this.musicPanel.refresh(beat);
    this.pianoPanel.refresh(beat);
    this.repaint();
  }


}
