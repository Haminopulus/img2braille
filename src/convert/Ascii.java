package braille.convert;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Ascii {
  private BufferedImage img;
  private StringBuilder output = new StringBuilder();
  private String palette; 
  private int w, h, dw, dh;
  private int primary;

  public Ascii(BufferedImage img, Boolean INVERT, String palette, int w, int h) {
    this.img = img;
    this.w = w;
    this.h = h;
    this.palette = palette;
    primary = (INVERT ? 255 : 0);
    dw = (int)Math.floor((double)img.getWidth() / (double)w);
    dh = (int)Math.floor((double)img.getHeight() / (double)h);
    toASCII();
  }

  private void toASCII() {
    for (int i = 0; i < img.getHeight(); i+=dh)
    {
      for (int j = 0; j < img.getWidth(); j+=dw) 
      {
        output.append(matchGamma(getGammaMean(j,i)));
      }
      output.append("\n");
    }
  }

  private int getGammaMean(int x, int y) 
  {
    double gammaSum = 0;
    for (int i = x; i<Math.min(x + dw, img.getWidth()); i++) 
    {
      for (int j = y; j<Math.min(y + dh, img.getHeight()); j++) 
      {
        Color rgb = new Color(img.getRGB(i,j));
        gammaSum += (double)(rgb.getRed() + rgb.getBlue() + rgb.getGreen())/3.0;
      }
    }
    int meanGamma = (int) Math.floor(gammaSum/(double)(dw * dh));
    return Math.abs(primary - meanGamma);
  }

  private char matchGamma(int gamma) {
    return palette.charAt((int)Math.floor((double)gamma/255.0 * (double) (palette.length()-1)));
  }

  @Override
  public String toString() {
    return output.toString();
  }
}
