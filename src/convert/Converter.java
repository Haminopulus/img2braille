package braille.convert;

import java.awt.Color;
import java.awt.image.BufferedImage;

abstract class Converter {
  protected BufferedImage img;
  protected StringBuilder output = new StringBuilder();
  protected int primary;

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
  
  protected abstract String getChar(int x, int y);

  @Override
  public String toString() {
    return output.toString();
  }
}
