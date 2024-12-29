package braille.input;

import java.io.File;
import java.io.IOException;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/** Class that handles the ImageIO, so the conversion from file to BufferedImage */
public class FileHandler {
  private File imageFile;
  private BufferedImage img;
  
  // Constructors
  public FileHandler() {}
  
  public FileHandler(File imageFile) throws IllegalArgumentException, IOException {
    this.imageFile = imageFile;
    try {
      img = ImageIO.read(imageFile);
    }
    catch (IOException e) {
      throw e;
    }
    catch (IllegalArgumentException e) {
      throw e;
    }
  }
  
  // Getter
  public BufferedImage getImg() { return img; }

  /** Converts given File to BufferedImage and returns it. 
   * @throws IOException
   * @throws IllegalArgumentException if the file is not a readable format. 
   * @param imageFile file to be converted 
   * @return BufferedImage instance
   **/
  public BufferedImage setImageFile(File imageFile) throws IllegalArgumentException, IOException { 
    this.imageFile = imageFile; 
    try {
      img = ImageIO.read(imageFile);
    }
    catch (IOException e) {
      throw e;
    }
    if (img == null) { 
      throw new IllegalArgumentException("IllegalArgumentException: File " 
          + imageFile.getAbsolutePath() 
          + " is not a valid Image file."); 
    }
    return img;
  }
}
