package braille.input;

import java.io.File;
import java.io.IOException;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FileHandler {
  private File image;
  private BufferedImage bufImg;

  public FileHandler() {}
  
  public FileHandler(File image) throws IllegalArgumentException, IOException {
    this.image = image;
    try {
      bufImg = ImageIO.read(image);
    }
    catch (IOException e) {
      throw e;
    }
    catch (IllegalArgumentException e) {
      throw e;
    }
  }

  public File getImage() { return image; }
  public BufferedImage getBufImg() { return bufImg; }

  public void setImage(File image) throws IllegalArgumentException, IOException { 
    this.image = image; 
    try {
      bufImg = ImageIO.read(image);
    }
    catch (IOException e) {
      throw e;
    }
    if (bufImg == null) { 
      throw new IllegalArgumentException("IllegalArgumentException: File " 
          + image.getAbsolutePath() 
          + " is not a valid Image file."); 
    }
  }

  public void setBufImg(int x, int y, Color col) {
    bufImg.setRGB(x, y, col.getRGB());
  }
}
