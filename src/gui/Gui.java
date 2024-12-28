package braille.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import braille.convert.Braille;

import java.awt.Color;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public final class Gui extends JFrame {
  private JLabel ImgContainer = new JLabel();
  private JLabel OutContainer = new JLabel();
  private ArrayList<BufferedImage> images = new ArrayList<>();

  public Gui(BufferedImage img) {
    setup();
    setImage(img);
  }
  
  public boolean addImage(BufferedImage image) {
    return images.add(image);
  }

  // privates
  private void setup() {
    setName("img2braille gui");
    setSize(1000,500);
    this.setLocationRelativeTo(null);
    setBackground(new Color(0,0,0));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    ImgContainer.setBounds(0,0,500,500);
    OutContainer.setBounds(500,0,500,500);
    add(ImgContainer);
  }

  private void setImage(BufferedImage img) {
    ImageIcon icon = new ImageIcon(
        img.getScaledInstance(
          ImgContainer.getWidth(), 
          ImgContainer.getHeight(), 
          Image.SCALE_SMOOTH));
    ImgContainer.setIcon(icon);
    String output = new Braille(img, false, 150).toString();
    System.out.println(output);
    OutContainer.setText(output);
    update(getGraphics());
  }
}
