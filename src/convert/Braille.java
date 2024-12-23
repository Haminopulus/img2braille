package braille.convert;

import java.awt.Color;
import java.awt.image.BufferedImage;

/** Class that takes in a BufferedImage and Converts it to Unicode Braille Patterns. 
 * <p> Is accessed via usage of {@code toString() } method. </p>
 * <p> The size of the Image is preserved 1:1 unless its height and width arent divisible by 4 and 2 respectively. </p>
 * @see <a href="https://en.wikipedia.org/wiki/Braille_Patterns">https://en.wikipedia.org/wiki/Braille_Patterns</a>
 */

public class Braille {
  private BufferedImage img;
  private StringBuilder output = new StringBuilder();
  /** Base 16 starting value of the Braille Patterns in the Unicode character set */
  private static final int hexBase = Integer.parseInt("2800", 16);
  /** primary color (either {@code white} or {@code black}, depending on whether the Image has been inverted) */
  private static int primary;
  private final int brightness;
  private int w, h, dw, dh;

  public Braille(BufferedImage img, Boolean INVERT, int brightness, int w, int h) {
    this.img = img;
    this.h = h;
    this.w = w;
    this.dh = (int) Math.floor(((double)(img.getHeight()-img.getHeight()%4)/4.0)/h);
    this.dw = (int) Math.floor(((double)(img.getWidth()-img.getWidth()%4)/4.0)/w);
    primary = (INVERT ? 0 : 1);
    this.brightness = brightness;
    toBraille();
  }

  private void toBraille() {
    for (int i = 0; i < img.getHeight()-img.getHeight()%4; i+=4*dw)
    {
      for (int j = 0; j < img.getWidth()-img.getWidth()%2; j+=2) 
      {
        output.append(getBraillePattern(j,i));
      }
      output.append("\n");
    }
  }

  /* Braille Dot numbers:
   *   _1_ 4
   *    2  5
   *    3  6
   *    7  8
   *
   * Pos 1-8 as binary number ("1" = on, "0" = off), add with 2800_16
   * _1_ : starting point
   */ 

  private String getBraillePattern(int x, int y) 
  {
    int[] dots = new int[8];
    // I NEED to automate this somehow
    // The first 6 are easy but i would hate to do the last two serparate
    dots[0] = getGamma(x, y);
    dots[1] = getGamma(x, y+1);
    dots[2] = getGamma(x, y+2);
    dots[3] = getGamma(x+1, y);
    dots[4] = getGamma(x+1, y+1);
    dots[5] = getGamma(x+1, y+2);
    dots[6] = getGamma(x, y + 3);
    dots[7] = getGamma(x + 1, y + 3);

    StringBuilder bin = new StringBuilder();
    for (int i = dots.length-1; i >= 0; i--)
    {
      bin.append((dots[i] > brightness) ? primary : 1 - primary);
    }
    return Character.toString(Integer.parseInt(bin.toString(), 2) + hexBase);
  }

  private int getGamma(int x, int y) 
  {
    Color rgb = new Color(img.getRGB(x,y));
    return (int)Math.ceil((double)(rgb.getRed() + rgb.getBlue() + rgb.getGreen())/3.0);
  }

  @Override
  public String toString() {
    return output.toString();
  }
}
