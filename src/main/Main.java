package braille.main;

import braille.convert.ASCII;
import braille.convert.Braille;
import braille.input.FileHandler;
import braille.utils.Constants;

public class Main {
  public static void main(String[] args) {
    FileHandler fHandler = new FileHandler(Constants.INFILE);
    
    if (Constants.ASCII) {
      ASCII myAscii = new ASCII(fHandler.getBufImg());
      System.out.println(myAscii);
    } 
    
    if (Constants.BRAILLE) {
      Braille myBraille = new Braille(fHandler.getBufImg());
      System.out.println(myBraille);
    }
  }
}
