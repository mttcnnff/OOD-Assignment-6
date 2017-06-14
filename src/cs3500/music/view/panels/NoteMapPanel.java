package cs3500.music.view.panels;

import java.awt.*;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.note.INote;

public class NoteMapPanel extends JPanel {

  private int[][] printMap;
  private TreeMap<INote, Integer> noteRange;
  Integer currBeat;
  BasicStroke lineStroke;

  public NoteMapPanel(TreeMap<INote, Integer> noteRange, int[][] printMap) {
    this.setBackground(Color.white);
    this.printMap = printMap;
    this.noteRange = noteRange;
    this.currBeat = 0;
    this.lineStroke = new BasicStroke(3);
  }

  public void setBeat(Integer beat) {
    this.currBeat = beat;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setFont(new Font("default", Font.BOLD, 16));

    //print notes
    for(int i = 0; i < this.printMap.length; i++) {
      for (int j = 0; j < this.printMap[0].length; j++) {
        switch (printMap[i][j]) {

          case -1:
            g2d.setColor(Color.black);
            g2d.fillRect(i*25, (noteRange.size()-j)*30 -5,25, 30);
            break;
          case 1:
            g2d.setColor(Color.green);
            g2d.fillRect(i*25, (noteRange.size()-j)*30 -5,25, 30);
            break;
          default:
            break;

        }
      }
    }

    //print measures
    g2d.setColor(Color.black);
    for(Integer i = 0; i < this.printMap.length; i+=4) {
      g2d.drawString(i.toString(), (i/4)*100,20);
      for (Integer j = 0; j < this.noteRange.size(); j++) {
        g2d.drawRect((i/4)*100, 25 + 30*j, 100, 30);
      }
    }

    g2d.setColor(Color.red);
    g2d.setStroke(this.lineStroke);

    g2d.drawLine(this.currBeat*25, 25, this.currBeat*25, 25 + this.noteRange.size()*30);
  }
}
