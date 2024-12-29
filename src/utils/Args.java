package braille.utils;

public final class Args {
  private static boolean ascii = false;
  private static boolean braille = false;
  private static boolean invert = false;
  private static boolean color = false;
  private static int width = 0;
  private static int height = 0;
  private static int brightness = 100;
  private static String palette = "  .-~=*%#W";

  // Constructor (private)
  private Args() {}
 
  // Setters 
  public static void setAscii(boolean val) {ascii = val;}
  public static void setBraille(boolean val) {braille = val;}
  public static void setInvert(boolean val) {invert = val;}
  public static void setColor(boolean val) {color = val;}
  public static void setWidth(int val) 
  {
    if (val > 0) 
    {
      width = val;
    } 
    else 
    {
      System.err.println("Invalid width, use positive values only. Ignoring");
    }
  }
  public static void setHeight(int val)
  {
    if (val > 0) 
    {
      height = val;
    } 
    else 
    {
      System.err.println("Invalid height, use positive values only. Ignoring");
    }
  }
  public static void setBrightness(int val) {brightness = val;}
  public static void setPalette(String val) {palette = val;}

  // Getters 
  public static boolean getAscii() {return ascii;}
  public static boolean getBraille() {return braille;}
  public static boolean getInvert() {return invert;}
  public static boolean getColor() {return color;}
  public static int getWidth() {return width;}
  public static int getHeight() {return height;}
  public static int getBrightness() {return brightness;}
  public static String getPalette() {return palette;}
}
