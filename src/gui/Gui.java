package braille.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public final class Gui extends JFrame {
  JLabel orgImgContainer = new JLabel();
  public Gui() {
    setName("img2braille gui");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
}
