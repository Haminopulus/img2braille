package braille.main;

import java.io.FileNotFoundException;
import braille.input.ArgHandler;

//TODO: Color, Outfile, GUI

public class Main {
  public static void main(String... args) {
    ArgHandler aHandler;
    if (args.length > 0) {
      try {
        aHandler = new ArgHandler(args); 
        aHandler.runWithArgs();
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
      } catch (FileNotFoundException e) {
        System.err.println(e.getMessage());
      }
    } else {
      System.err.println("Error: No arguments provided (try -h for help)");
    }
  }
}
