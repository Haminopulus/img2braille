package braille.input;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FileHandler {
  private File image;
  private BufferedImage bufImg;
  
  public FileHandler(File image) {
    this.image = image;
    try {
      bufImg = ImageIO.read(image);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public File getImage() { return image; }
  public BufferedImage getBufImg() { return bufImg; }

  public void setImage(File image) { 
    this.image = image; 
    try {
      bufImg = ImageIO.read(image);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setBufImg(int x, int y, Color col) {
    bufImg.setRGB(x, y, col.getRGB());
  }
}
