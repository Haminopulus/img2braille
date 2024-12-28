package braille.convert;

import java.awt.Color;
import java.awt.image.BufferedImage;

/** Class that takes in a BufferedImage and Converts it to an Ascii String. 
 * <p> Is accessed via usage of {@code toString() } method. </p>
 * <p> The size of the Image is dependent on width and height variables passed during initialization. </p>
 * @see <a href="https://en.wikipedia.org/wiki/ASCII_art">https://en.wikipedia.org/wiki/ASCII_art</a>
 */
public class Ascii {
  private BufferedImage img;
  private StringBuilder output = new StringBuilder();
  private String palette;
  private int primary;
  
  /** Given an image, inversion bool, a palette and the desired width/height in characters, creates an instance of Ascii class.  
   *  <p> Automatically converts the given image to its ascii counterpart, can be printed to stdout right away. </p>
   *  @param img the {@code BufferedImage} that should be converted 
   *  @param invert the {@code Boolean}, that decides whether color should be inverted
   *  @param palette the {@code String} of chars that should be used to draw the Ascii image, ordered from darkest to brightest
   */
  public Ascii(BufferedImage img, Boolean invert, String palette) {
    this.img = img;
    this.palette = palette;

    primary = (invert ? 255 : 0);
    
    toASCII();
  }
 
  /** Converts the internally saved BufferedImage to Ascii and saves it in the output StringBuilder. */
  private void toASCII() {
    // for each dh*dw Area of the BufferedImage   
    for (int i = 0; i < img.getHeight(); i+=4)
    {
      for (int j = 0; j < img.getWidth(); j+=2) 
      {
        output.append(matchGamma(getGammaMean(j,i)));
      }
      output.append("\n");
    }
  }

  /** Determines the Mean of all Gamma values in the intervall of size dw*dh at x,y.
 * @param x (int) starting horizontal position
 * @param y (int) starting vertical  position
 * @return the mean of all gamma values rounded down
*/
  private int getGammaMean(int x, int y) 
  {
    double gammaSum = 0;
    for (int i = x; i < Math.min(x + 2, img.getWidth()); i++) 
    {
      for (int j = y; j < Math.min(y + 4, img.getHeight()); j++) 
      {
        Color rgb = new Color(img.getRGB(i,j));
        gammaSum += (double)(rgb.getRed() + rgb.getBlue() + rgb.getGreen())/3.0;
      }
    }
    int meanGamma = (int) Math.floor(gammaSum/(double)(8));
    return Math.abs(primary - meanGamma);
  }

  /** Gives the character from the palette corresponding to the given gamma value. *
 * @param gamma (int) the brightness of some part of the image
 * @return corresponding {@code char} from the palette String
 */
  private char matchGamma(int gamma) {
    return palette.charAt((int) Math.floor((double) gamma / 255.0 * (double) (palette.length()-1)));
  }

  @Override
  public String toString() {
    return output.toString();
  }
}
