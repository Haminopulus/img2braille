package braille.convert;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

abstract class Converter {
  protected BufferedImage img;
  protected StringBuilder output = new StringBuilder();
  protected int primary;

  // Constructor
  protected Converter(BufferedImage img, int primary) {
    this.img = img;
    this.primary = primary;
  }
  
  /** Returns the Gamma Value of the pixel in the BufferedImage at position (x, y) 
   * @param x (int) the horizontal coordinate
   * @param y (int) the vertical coordinate
   * @return the {@code int} value corresponding to the gamma value at the given coordinate
   **/
  protected int getGamma(int x, int y) 
  {
    Color rgb = new Color(img.getRGB(x,y));
    return (int) Math.ceil((double)(rgb.getRed() + rgb.getBlue() + rgb.getGreen())/3.0);
  }

  /** Converts the internally saved BufferedImage to Ascii and saves it in the output StringBuilder. */
  protected void convert() {
    for (int i = 0; i < img.getHeight(); i+=4)
    {
      for (int j = 0; j < img.getWidth(); j+=2) 
      {
        output.append(getChar(j,i));
      }
      output.append("\n");
    }
  }
 
  /** Should convert a given part of the image to a Unicode Character, 
   *  depending on some metric like the average gamma value.
   *  @param x horizontal start position 
   *  @param y vertical start position 
   *  @return {@code single character String} (most unicode chars cannot be stored in a singular char variable)
   **/
  protected abstract String getChar(int x, int y);
  
  protected BufferedImage resizeImg(int width, int height) {
    BufferedImage resized = new BufferedImage(width, height, img.getType());
    Graphics2D g = resized.createGraphics();
    g.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(), img.getHeight(), null);
    g.dispose();
    return (img = resized);
  }
  @Override
  public String toString() {
    return output.toString();
  }
}
