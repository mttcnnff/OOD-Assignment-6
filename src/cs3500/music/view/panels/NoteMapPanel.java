package cs3500.music.view.panels;

import java.awt.*;
import java.util.TreeMap;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.IPlayerModel;
import cs3500.music.util.Utils;

/**
 * Class representation of Note Map Panel.
 * This shows the song in a visual manner with start beats as black boxes, sustains as green, and
 * the current beat marked by a red line.
 */
public class NoteMapPanel extends JPanel {

  private IPlayerModel model;
  private Integer currBeat;
  private BasicStroke lineStroke;

  /**
   * Constructor for Note Map Panel
   * @param model given model to represent.
   */
  NoteMapPanel(IPlayerModel model) {
    this.setBackground(Color.white);
    this.model = model;
    this.currBeat = 0;
    this.lineStroke = new BasicStroke(3);
    this.setPreferredSize(new Dimension(this.model.getLength()*25, 30 + this.model.getToneRange
            ().size()*30));
  }

  /**
   * Refresh note map panel to current beat.
   * (Move red line to current beat).
   * @param currBeat current beat view is at.
   */
  void refresh(Integer currBeat) {
    this.currBeat = currBeat;
  }

  /**
   * Paints this panel in the view.
   * @param g given graphics object.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setFont(new Font("default", Font.BOLD, 16));

    TreeMap<Integer, Integer> toneRange = this.model.getToneRange();
    Integer songLength = this.model.getLength();

    //determine view range
    Integer beginViewBeat = this.currBeat - 60 > 0 ? this.currBeat - 60 : 0;
    Integer endViewBeat = this.currBeat + 60 < songLength ? this.currBeat + 60 : songLength;

    for (int i = beginViewBeat; i < endViewBeat; i++) {
      List<Integer> currentTones = Utils.notesToIntegers(this.model.getPlayingNotes(i));
      List<Integer> currentStartNotes = Utils.notesToIntegers(this.model.getStartNotes(i));
      g2d.setColor(Color.green);
      for (Integer tone : currentTones) {
        g2d.fillRect(i*25, (toneRange.size()-toneRange.get(tone))*30 -5,25, 30);
      }
      g2d.setColor(Color.black);
      for (Integer startNote : currentStartNotes) {
        g2d.fillRect(i*25, (toneRange.size()-toneRange.get(startNote))*30 -5,25, 30);
      }
    }

    //print measures
    g2d.setColor(Color.black);
    for(Integer i = (beginViewBeat / 4) * 4; i < endViewBeat; i+=4) {
      g2d.drawString(i.toString(), (i/4)*100,20);
      for (Integer j = 0; j < toneRange.size(); j++) {
        g2d.drawRect((i/4)*100, 25 + 30*j, 100, 30);
      }
    }

    g2d.setColor(Color.red);
    g2d.setStroke(this.lineStroke);

    g2d.drawLine(this.currBeat*25, 25, this.currBeat*25, 25 + toneRange.size()*30);
  }
}
