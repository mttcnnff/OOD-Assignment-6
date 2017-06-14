package cs3500.music.view.panels;

import java.awt.*;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import cs3500.music.note.INote;

class NoteListPanel extends JPanel {

  private TreeMap<INote, Integer> noteRange;

  NoteListPanel(TreeMap<INote, Integer> noteRange) {
    this.setBackground(Color.white);
    this.noteRange = noteRange;
    this.setPreferredSize(new Dimension(40, 600));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setFont(new Font("default", Font.BOLD, 16));
    for (INote note : this.noteRange.keySet()) {
      g2d.drawString(note.toString(), 0, 10 + (this.noteRange.size() - this.noteRange.get(note))
              *30);
    }
  }





}
