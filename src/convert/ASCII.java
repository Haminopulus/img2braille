package braille.convert;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ASCII {
  BufferedImage img;
  StringBuilder output = new StringBuilder();
  String palette = "  ...,,:*%#"; 
  int txtW = 2, txtH = 5;

  public ASCII(BufferedImage img) {
    this.img = img;
    toASCII();
  }

  private void toASCII() {
    for (int i = 0; i < img.getHeight(); i+=2)
    {
      for (int j = 0; j < img.getWidth(); j+=1) 
      {
        output.append(matchGamma(getGammaMean(j,i)));
      }
      output.append("\n");
    }
  }

  private int getGammaMean(int x, int y) 
  {
    double gammaSum = 0;
    for (int i = x; i<Math.min(x + txtW, img.getWidth()); i++) 
    {
      for (int j = y; j<Math.min(y + txtH, img.getHeight()); j++) 
      {
        Color rgb = new Color(img.getRGB(i,j));
        gammaSum += (double)(rgb.getRed() + rgb.getBlue() + rgb.getGreen())/3.0;
      }
    }
    return (int) Math.floor(gammaSum/(double)(txtW*txtH));
  }

  private char matchGamma(int gamma) {
    return palette.charAt((int)Math.floor((double)gamma/255.0 * (double) (palette.length()-1)));
  }

  @Override
  public String toString() {
    return output.toString();
  }
}
