package braille.gui;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import braille.convert.Ascii;
import braille.convert.Braille;
import braille.input.FileHandler;
import braille.utils.Constants;
import braille.utils.Args;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public final class Gui extends JFrame {
  private JPanel inContainer = new JPanel();
  private JPanel outContainer = new JPanel();
  private JLabel inLabel = new JLabel();
  private JTextArea outTextArea = new JTextArea();
  private FileHandler fHandler = new FileHandler();

  private JPanel uiContainer = new JPanel();
  private JButton nextButton = new JButton("Next");
  private JButton prevButton = new JButton("Previous");
  private JButton invButton = new JButton("Invert");
  private JSlider widthSlider = new JSlider(1, 500);
  private JSlider heightSlider = new JSlider(1, 500);
  private JSlider brightnessSlider = new JSlider(0, 255);
  private JRadioButton asciiButton = new JRadioButton("Ascii");
  private JRadioButton brailleButton = new JRadioButton("Braille");
  private JTextField paletteField = new JTextField();
  private ButtonGroup conversionButtons = new ButtonGroup();

  // ID of interactables 
  private static final int WIDTH_SLIDER = 0, HEIGHT_SLIDER = 1, BRIGHTNESS_SLIDER = 2;
  private static final int NEXT_BUTTON = 0, PREV_BUTTON = 1, INVERT_BUTTON = 2;
  
  private static Font font;
  private ArrayList<String> images = new ArrayList<>();
  private int currentImg = 0;
  private Dimension currSize;

  public Gui() {
    File fontFile = new File("/usr/local/share/braille/fonts/CaskaydiaMonoNerdFont-Regular.ttf");
    try {
     font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
     setup();
     
    } catch (IOException|FontFormatException e) {
     System.out.println("Error: Could not find or read font at path" + fontFile.getAbsoluteFile());
    }
  }
  

  public void addImage(String imgPath) {
    if (images.size() == 0) {
      nextButton.setEnabled(true);
      prevButton.setEnabled(true);
      setImage(imgPath);
    }
    images.add(imgPath);
  }

  private void setup() {
    // NEXT-BUTTON (switch to the next image)
    setupFrame();
    setupButtons();
    setupIOPanels();
    add(inContainer);
    add(outContainer);
    add(uiContainer);
    //add(nextButton);
    pack();
    setVisible(true);
  }

  private void setupFrame() {
    setName(Constants.FNAME);
    setLayout(new FlowLayout());
    setPreferredSize(new Dimension(Constants.FWIDTH, Constants.FHEIGHT));
    getContentPane().setBackground(Color.BLACK);
    //setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void setupButtons() {
    // BUTTONS
    nextButton.setMnemonic(KeyEvent.VK_N);
    nextButton.addActionListener(e -> buttonPressed(NEXT_BUTTON));
    nextButton.setEnabled(false);

    prevButton.setMnemonic(KeyEvent.VK_P);
    prevButton.addActionListener(e -> buttonPressed(PREV_BUTTON));
    prevButton.setEnabled(false);

    invButton.setMnemonic(KeyEvent.VK_I);
    invButton.addActionListener(e -> buttonPressed(INVERT_BUTTON));

    // RADIOBUTTONS

    asciiButton.setBackground(Color.BLACK);
    asciiButton.addActionListener(e -> radioButtonPressed(e));
    brailleButton.setBackground(Color.BLACK);
    brailleButton.addActionListener(e -> radioButtonPressed(e));
    conversionButtons.add(asciiButton);
    conversionButtons.add(brailleButton);

    // TEXTFIELD
    paletteField.setText(Args.getPalette());
    paletteField.setToolTipText("Ascii palette");
    paletteField.getDocument().addDocumentListener(new DocumentListener() {
    public void changedUpdate(DocumentEvent e) {}
    public void removeUpdate(DocumentEvent e) {paletteChanged();}
    public void insertUpdate(DocumentEvent e) {paletteChanged();}
    });

    // SLIDERS
    widthSlider.setBackground(Color.BLACK);
    widthSlider.addChangeListener(l -> sliderChanged(l, WIDTH_SLIDER));
    heightSlider.setBackground(Color.BLACK);
    heightSlider.addChangeListener(l -> sliderChanged(l, HEIGHT_SLIDER));
    brightnessSlider.setBackground(Color.BLACK);
    brightnessSlider.addChangeListener(l -> sliderChanged(l, BRIGHTNESS_SLIDER));

    uiContainer.setBackground(Color.BLACK);
    uiContainer.setPreferredSize(new Dimension(Constants.PWIDTH, Constants.PHEIGHT));
    uiContainer.add(nextButton);
    uiContainer.add(prevButton);
    uiContainer.add(invButton);
    uiContainer.add(asciiButton);
    uiContainer.add(brailleButton);
    uiContainer.add(widthSlider);
    uiContainer.add(heightSlider);
    uiContainer.add(brightnessSlider);
    uiContainer.add(paletteField);
  }

  private void setupIOPanels() {
    // output 
    outTextArea.setPreferredSize(new Dimension(Constants.PWIDTH, Constants.PHEIGHT));
    outTextArea.setBackground(Color.BLACK);
    outTextArea.setForeground(Color.WHITE);
    outTextArea.setFont(font);
    outTextArea.setEditable(false);
    
    outContainer.setBackground(Color.ORANGE);
    outContainer.add(outTextArea);

    // input
    inLabel.setPreferredSize(new Dimension(Constants.PWIDTH, Constants.PHEIGHT));
    inLabel.setOpaque(true);
    inLabel.setBackground(Color.BLACK);
    inLabel.setVerticalAlignment(SwingConstants.TOP);
    
    inContainer.setBackground(Color.MAGENTA);
    inContainer.add(inLabel);
  }

  private void buttonPressed(int btnID) {
    switch (btnID) {
      case NEXT_BUTTON:
        currentImg = (images.size()==currentImg+1) ? 0 : currentImg + 1;
        break;
      case PREV_BUTTON:
        currentImg = (0 == currentImg) ? images.size()-1 : currentImg - 1;
        break;
      case INVERT_BUTTON:
        Args.setInvert(!Args.getInvert());
        break;
    }
    setImage(images.get(currentImg));
  }

  private void radioButtonPressed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Ascii":
        Args.setAscii(true);
        Args.setBraille(false);
        break;
      case "Braille":
        Args.setBraille(true);
        Args.setAscii(false);
        break;
    }
    setImage(images.get(currentImg));
  }

  private void sliderChanged(ChangeEvent e, int sldrID) {
    switch (sldrID) {
      case WIDTH_SLIDER:
        Args.setWidth(((JSlider) e.getSource()).getValue());
        break;
      case HEIGHT_SLIDER:
        Args.setHeight(((JSlider) e.getSource()).getValue());
        break;
      case BRIGHTNESS_SLIDER:
        Args.setBrightness(((JSlider) e.getSource()).getValue());
        break;
    }
    setImage(images.get(currentImg));
  }

  private void paletteChanged() {
      Args.setPalette((paletteField.getText().length() != 0) ? paletteField.getText() : " ");
      setImage(images.get(currentImg));
  }

  private void setImage(String imgPath) {
    BufferedImage img; 
    ImageIcon icon;
    String output;
    int width;
    int height;
    boolean landscape;
    double ratio;

    // GET the Image from the filepath
    try 
    {
      img = fHandler.setImageFile(new File(imgPath));
    }
    catch (Exception e) 
    {
      System.err.println("Error while Reading image File. Skipping image from file" + imgPath);
      images.remove(imgPath);
      currentImg--;
      setImage(images.get(currentImg));
      return;
    }

    // SET font size (inversely propotrional to the image size)
    outTextArea.setFont(outTextArea.getFont().deriveFont(
          1655f / (float) Math.max(Args.getWidth(), Args.getHeight())));
    
    // SCALE Image to JPanel
    landscape = (Args.getWidth() > Args.getHeight());
    img = fHandler.getScaled(img, Args.getWidth(), Args.getHeight());
    ratio = (double) Math.min(Args.getWidth(), Args.getHeight()) / (double) Math.min(Args.getWidth(), Args.getHeight());
    
    icon = new ImageIcon(fHandler.getScaled(
          img,
          (landscape) ? Constants.PWIDTH : (int)((double)Constants.PHEIGHT * ratio), 
          (landscape) ? (int)((double) Constants.PWIDTH * ratio) : Constants.PHEIGHT));
    
    // SET the new Icon and output
    output = (Args.getAscii()) ? new Ascii(img).toString() : new Braille(img).toString();
    inLabel.setIcon(icon);
    outTextArea.setText(output);
    repaint();
  }
}
