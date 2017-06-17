package cs3500.music.view.visualview;

import java.awt.*;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.model.IPlayerModel;
import cs3500.music.util.Utils;

/**
 * Class for Note List Panel.
 */
class NoteListPanel extends JPanel {

  private IPlayerModel model;

  /**
   * Constructor for Note List Panel to represent given model.
   * @param model given model to represent.
   */
  NoteListPanel(IPlayerModel model) {
    this.model = model;
    this.setBackground(Color.white);
    this.setPreferredSize(new Dimension(40, 800/*10 + this.noteRange.size()* 30*/));
  }

  /**
   * Paints this panel in the view.
   * @param g given graphics object.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    TreeMap<Integer, Integer> toneRange = this.model.getToneRange();

    g2d.setFont(new Font("default", Font.BOLD, 16));
    for (Integer tone : toneRange.keySet()) {
      g2d.drawString(Utils.toneToString(tone), 0, 10 + (toneRange.size() - toneRange.get
              (tone))
              *30);
    }
  }
}
