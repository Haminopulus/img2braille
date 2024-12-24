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
  private int width, height, dw, dh;
  private int primary;
  
  /** Given an image, inversion bool, a palette and the desired width/height in characters, creates an instance of Ascii class.  
   *  <p> Automatically converts the given image to its ascii counterpart, can be printed to stdout right away. </p>
   *  @param img the {@code BufferedImage} that should be converted 
   *  @param invert the {@code Boolean}, that decides whether color should be inverted
   *  @param palette the {@code String} of chars that should be used to draw the Ascii image, ordered from darkest to brightest
   *  @param width the {@code int} value representing the desired image width
   *  @param height the {@code int} value represetning the desired image height
   */
  public Ascii(BufferedImage img, Boolean invert, String palette, int width, int height) {
    this.img = img;
    this.width = width;
    this.height = height;
    this.palette = palette;

    primary = (invert ? 255 : 0);
    dw = (int)Math.floor((double)img.getWidth() / (double)width);
    dh = (int)Math.floor((double)img.getHeight() / (double)height);
    
    toASCII();
  }
 
  /** Converts the internally saved BufferedImage to Ascii and saves it in the output StringBuilder. */
  private void toASCII() {
    // for each dh*dw Area of the BufferedImage   
    for (int i = 0; i < img.getHeight(); i+=dh)
    {
      for (int j = 0; j < img.getWidth(); j+=dw) 
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
    for (int i = x; i < Math.min(x + dw, img.getWidth()); i++) 
    {
      for (int j = y; j < Math.min(y + dh, img.getHeight()); j++) 
      {
        Color rgb = new Color(img.getRGB(i,j));
        gammaSum += (double)(rgb.getRed() + rgb.getBlue() + rgb.getGreen())/3.0;
      }
    }
    int meanGamma = (int) Math.floor(gammaSum/(double)(dw * dh));
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
