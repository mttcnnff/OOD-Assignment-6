package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.IPlayerModel;
import cs3500.music.view.panels.MusicPanel;
import cs3500.music.view.panels.PianoPanel;

public class VisualView extends JFrame implements IView {

  private IPlayerModel model;
  private JPanel musicPanel;
  private JPanel pianoPanel;
  private JScrollPane musicPanelScroller;

  VisualView(IPlayerModel model) {
    this.model = model;

    this.setTitle("Music Player");
    this.setSize(2000, 500);
    this.setResizable(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    //music panel
    this.musicPanel = new MusicPanel(this.model);
    this.musicPanelScroller = new JScrollPane(musicPanel);
    this.musicPanelScroller.setMinimumSize(new Dimension(4000, 500));
    this.musicPanelScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.musicPanelScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.add(musicPanelScroller,BorderLayout.NORTH);

    //piano panel
    this.pianoPanel = new PianoPanel(this.model);
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
  public void refresh() {
    this.repaint();

  }


}
