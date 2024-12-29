package braille.convert;

import java.awt.Color;
import java.awt.image.BufferedImage;

import braille.convert.Converter;
import braille.utils.Args;

/** Class that takes in a BufferedImage and Converts it to Unicode Braille Patterns. 
 * <p> Is accessed via usage of {@code toString() } method. </p>
 * <p> The size of the Image is preserved 1:1 unless its height and width arent divisible by 4 and 2 respectively. </p>
 * @see <a href="https://en.wikipedia.org/wiki/Braille_Patterns">https://en.wikipedia.org/wiki/Braille_Patterns</a>
 **/

public class Braille extends Converter {
  private int brightness;
  /** Base 16 starting value of the Braille; Patterns in the Unicode character set. **/
  private static final int hexBase = Integer.parseInt("2800", 16);

  /** Given an image, inversion bool and a cutoff for the minimum drawing brightness, creates an instance of Braille class.  
   *  <p> Automatically converts the given image to its braille counterpart, can be printed to stdout right away. </p>
   *  @param img the {@code BufferedImage} that should be converted 
   *  @param invert the {@code Boolean}, that decides whether color should be inverted
   *  @param brightness the {@code int} value represetning the desired brightness cutoff 
   **/
  public Braille(BufferedImage img) {
    super(img, (Args.getInvert() ? 0 : 1));
    this.brightness = Args.getBrightness();
    resizeImg(Args.getWidth(), Args.getHeight());
    convert();
  }
  
  /** checks the rectangular 2*4 pixel Array at position x,y. 
   * Converts it to the corresponding 8-dot braille character 
   * @param x (int) horizontal starting coordinate
   * @param y (int) vertical starting coordinate
   * @return the {@code String} value corresponding to the fitting unicode braille character
   **/
  protected String getChar(int x, int y) 
  {
    int[] dots = new int[8];
    int[] xpos = new int[]{x, x, x, x+1, x+1, x+1, x, x+1};
    int[] ypos = new int[]{y, y+1, y+2, y, y+1, y+2, y+3, y+3};
    for (int i = 0; i < 8; i++) {
      dots[i] = (xpos[i] > img.getWidth()-1 || ypos[i] > img.getHeight()-1) ? 0 : getGamma(xpos[i], ypos[i]);
    }

    StringBuilder bin = new StringBuilder();
    for (int i = dots.length - 1; i >= 0; i--)
    {
      bin.append((dots[i] > brightness) ? primary : 1 - primary);
    }
    return Character.toString(Integer.parseInt(bin.toString(), 2) + hexBase);
  }
}
