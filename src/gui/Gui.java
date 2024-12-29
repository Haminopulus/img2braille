package braille.gui;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.StyledDocument;

import braille.convert.Ascii;
import braille.convert.Braille;
import braille.utils.Constants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public final class Gui extends JFrame {
  private JLabel inContainer = new JLabel();
  private JTextArea outTextArea = new JTextArea();
  private JButton nextButton = new JButton("Next");
  private ArrayList<BufferedImage> images;
  private int currentImg = 0;

  public Gui() {
    setup();
  }

  public void addImage(BufferedImage image) {
    if (images.size() == 0) {
      nextButton.setEnabled(true);
      setImage(image);
    }
    images.add(image);
  }

  private void setup() {
    this.images = new ArrayList<>();
    // JFRAME (main frame)
    setName("img2braille gui");
    setSize(Constants.FWIDTH, Constants.FHEIGHT);
    setBackground(new Color(0,0,0));
    this.setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    // NEXT-BUTTON (switch to the next image)
    nextButton.setBounds(0,0,100,50);
    nextButton.setMnemonic(KeyEvent.VK_N);
    nextButton.addActionListener(e -> nextButtonPressed());
    nextButton.setEnabled(false);
    add(nextButton);
    
    // JTEXTPANE (the output character art)
    outTextArea.setBounds(500, 0, Constants.PWIDTH, Constants.PHEIGHT);
    outTextArea.setBackground(new Color(0,0,0));
    outTextArea.setForeground(new Color(255,255,255));
    outTextArea.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 500));
    outTextArea.setEditable(false);
    add(outTextArea);
    
    // JPANEL (input image)
    inContainer.setBounds(0, 0, Constants.PWIDTH, Constants.PHEIGHT);
    inContainer.setBackground(new Color(0,0,0));
    add(inContainer);
    
    setVisible(true);
  }

  private void nextButtonPressed() {
    currentImg = (images.size()==currentImg+1) ? 0 : currentImg + 1;
    setImage(images.get(currentImg));
  }

  private void setImage(BufferedImage img) {
    outTextArea.setFont(outTextArea.getFont().deriveFont(1600f/(float)img.getHeight()));
    ImageIcon icon = new ImageIcon(
        img.getScaledInstance(
          Constants.PWIDTH, 
          Constants.PHEIGHT, 
          Image.SCALE_SMOOTH));

    inContainer.setIcon(icon);
    String output = new Ascii(img, false, " .-~=*%#@").toString();
    //String output = new Braille(img, false, 150).toString();

    outTextArea.setText(output);
    repaint();
  }
}
