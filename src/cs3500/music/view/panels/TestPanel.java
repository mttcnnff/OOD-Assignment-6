package cs3500.music.view.panels;


import java.awt.*;

import javax.swing.*;

public class TestPanel extends JPanel {

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    BasicStroke stroke = new BasicStroke(3);
    g2d.setStroke(stroke);

    g2d.drawRect(0, 0, 100, 100);

  }


}
