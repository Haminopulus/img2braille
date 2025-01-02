package braille.input;

import java.io.File;
import java.io.IOException;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageOutputStream;

import braille.utils.Constants;

/** Class that handles the ImageIO, so the conversion from file to BufferedImage */
public class FileHandler {
  private File imageFile;
  private ImageReader imageReader;
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

  public String resizeIfNeeded(BufferedImage img, String imgPath) 
  {
    if (img.getWidth() > Constants.MAXSIZE || img.getHeight() > Constants.MAXSIZE) 
    {
      try
      {
        this.imageFile = File.createTempFile("ImgScaled-", ".tmp");
      }
      catch (IOException e) {
        System.err.println(e.getMessage());
        return imgPath;
      }
      imgPath = imageFile.getAbsolutePath();
      int width = img.getWidth();
      int height = img.getHeight();
      boolean landscape = (width > height);
      double ratio = (double) Math.min(width, height) / (double) Math.max(width, height);
      try 
      {
        ImageIO.write(getScaled(
            img, 
            (int) ((landscape) ? width : height * ratio), 
            (int) ((landscape) ? width * ratio : height)), "jpg", imageFile);
      }
      catch (IOException e) 
      {
        System.err.println(e.getMessage());
      }
    }
    return imgPath;
  }

  public BufferedImage getScaled(BufferedImage img, int width, int height) 
  {
    BufferedImage newImage = new BufferedImage(width, height, img.getType()); 
    Graphics2D g = newImage.createGraphics();
    g.drawImage(img, 0, 0, width, height, null);
    g.dispose();
    return newImage;
  }

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
