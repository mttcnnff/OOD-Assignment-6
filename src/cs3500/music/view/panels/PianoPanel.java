package cs3500.music.view.panels;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.IPlayerModel;
import cs3500.music.note.INote;

public class PianoPanel extends JPanel {

  private IPlayerModel model;
  HashMap<INote, Rectangle> keyboard;

  public PianoPanel(IPlayerModel model) {
    this.model = model;
    this.setBackground(Color.lightGray);
    this.keyboard = new HashMap<>();

    for (int i = 21; i < 109; i++) {

    }



  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    BasicStroke stroke = new BasicStroke(1);
    Graphics2D g2d = (Graphics2D) g;



    g2d.setStroke(stroke);


    for (int i = 0; i < 12; i++) {
      g2d.setColor(Color.white);
      g2d.fillRect(i*20, 0, 20, 200);
      g2d.setColor(Color.black);
      g2d.drawRect(i*20, 0, 20, 200);
    }


  }



}
