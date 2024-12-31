package braille.convert;

import java.awt.Color;
import java.awt.image.BufferedImage;

import braille.convert.Converter;
import braille.utils.Args;

/** Class that takes in a BufferedImage and Converts it to an Ascii String. 
 * <p> Is accessed via usage of {@code toString() } method. </p>
 *<p> The size of the Image is dependent on width and height variables passed during initialization. </p>
 * @see <a href="https://en.wikipedia.org/wiki/ASCII_art">https://en.wikipedia.org/wiki/ASCII_art</a>
 */
public class Ascii extends Converter { 
  private String palette;

  /** Given an image, inversion bool, a palette and the desired width/height in characters, creates an instance of Ascii class.  
   *  <p> Automatically converts the given image to its ascii counterpart, can be printed to stdout right away. </p>
   *  @param img the {@code BufferedImage} that should be converted 
   *  @param invert the {@code Boolean}, that decides whether color should be inverted
   *  @param palette the {@code String} of chars that should be used to draw the Ascii image, ordered from darkest to brightest
   */
  public Ascii(BufferedImage img) {
    super(img, (Args.getInvert() ? 255 : 0));
    this.palette = Args.getPalette();
    resizeImg(Args.getWidth(), Args.getHeight());
    convert();
  }
 
 /** Chooses char from palette corresponding to the position in the image. 
  * @param x (int) horizontal starting position of the piece of the image
  * @param y (int) vertical starting position of the piece of the image
  * @return single Character {@code String}, which can be appended to the output
 */
  @Override
  protected String getChar(int x, int y) 
  {
    double gamma = getGammaMean(x,y);
    int index = (int) Math.floor(gamma / 255.0 * (double) Math.max(0, (palette.length()-1)));
    return String.valueOf(palette.charAt(index));
  }

  /** Determines the Mean of all Gamma values in the intervall of size 4 * 2 <i>(ratio of char width to height, approx)</i> at x,y.
 * @param x (int) starting horizontal position
 * @param y (int) starting vertical  position
 * @return the mean of all gamma values rounded down
*/
  private double getGammaMean(int x, int y) 
  {
    double gammaSum = 0;

    for (int i = x; i < Math.min(x + 2, img.getWidth()); i++) 
    {
      for (int j = y; j < Math.min(y + 4, img.getHeight()); j++) 
      {
        gammaSum += getGamma(i,j);
      }
    }
    // if primary col is 0, we get ascending values, otherwise descending
    return Math.abs(primary - Math.floor(gammaSum/(2.0 * 4.0)));
  }
}
