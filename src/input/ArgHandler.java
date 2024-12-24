package braille.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import braille.convert.Ascii; 
import braille.convert.Braille;
import braille.input.FileHandler;

public class ArgHandler {
  private static final String help = 
    "braille [ARGS] <input files>\n"
   +"\nARGS:"
   +"\t'-h'/'--help'                                  print this help message\n"
   +"\t'-A'/'--ascii'                                 convert input image file to ASCII\n"
   +"\t'-B'/'--braille'                               convert input image file to BRAILLE dot patterns\n\n"
   +"\t'-i'/'--invert'                                invert the input image before conversion\n"
   +"\t'-p [STRING]'/'--palette [STRING]'             the string after this argument is assigned as the char palette (only for ASCII)\n"
   +"\t'-b [INT]'/'--brightness [INT]'                uses the given integer value as brightness cutoff (only for BRAILLE) \n\n"
   +"\t'-W [INT]'/'--width [INT]'                     Changes width to [INT] chars\n"
   +"\t'-H [INT]'/'--height [INT]'                    Changes height to [INT] chars\n\n"
   +"\t'-o [PATH]'/'--outfile [PATH]'                 prints the converted ASCII/Braille to the given file\n";

  private Boolean ascii = false, braille = false, invert = false, ignore = false, helped = false;
  private String palette = "  .-~=*%#W";
  private int brightness = 100, width = 0, height = 0;
  private ArrayList<File> inFiles = new ArrayList<>();
  private FileHandler fHandler = new FileHandler();

  public ArgHandler(String[] args) throws IllegalArgumentException, FileNotFoundException
  { 
    for (int i = 0; i < args.length; i++) 
    {
      if (ignore) {ignore = false; continue;}
      switch (args[i]) {
        case "--help":
        case "-h":
          System.out.println(help);
          helped = true;
          break;

        case "--ascii":
        case "-A":
          ascii = true;
          break;

        case "-B":
        case "--braille":
          braille = true;
          break;

        case "--invert":
        case "-i":
          invert = true;
          break;

        case "--palette":
        case "-p":
          try {
            palette = args[i+1];
            ignore = true;
          } catch (IndexOutOfBoundsException e) {
            System.err.println("No value provided for argument '-p', palette (String) is required. Ignoring.");
          }
          break;
        
        case "--brightness":
        case "-b":
          try {
            brightness = Integer.parseInt(args[i+1]);
            ignore = true;
          } catch (IndexOutOfBoundsException e) {
            System.err.println("No value provided for argument '-b', brightness (int) is required. Ignoring.");
          } catch (NumberFormatException e) {
            System.err.println("The given value is not a valid integer. Ignoring.");
          }
          break;

        case "--width":
        case "-W":
          try {
            width = Integer.parseInt(args[i+1]);
            ignore = true;
          } catch (IndexOutOfBoundsException e) {
            System.err.println("No value provided for argument '-W', width (int) is required. Ignoring.");
          } catch (NumberFormatException e) {
            System.err.println("The given value is not a valid integer. Ignoring.");
          }
          break;

        case "--height":
        case "-H":
          try {
            height = Integer.parseInt(args[i+1]);
            ignore = true;
          } catch (IndexOutOfBoundsException e) {
            System.err.println("No value provided for argument '-H', height (int) is required. Ignoring.");
          } catch (NumberFormatException e) {
            System.err.println("The given value is not a valid integer. Ignoring.");
          }
          break;

        default:
          File inFile = new File(args[i]);
          if (inFile.exists() && !inFile.isDirectory()) {
            inFiles.add(inFile);
          } else {
            System.out.println("Unknown Argument: '" + args[i] + "' try '-h' for help. Ignoring.");
          }
          break;
      }
    }
    if (!ascii && !braille && !helped) {
      throw new IllegalArgumentException("No active args provided. Exiting.");
    }
    if (inFiles.isEmpty() && !helped) {
      throw new FileNotFoundException("No valid input file was provided.");
    }
  }

  public void runWithArgs() {
    for (File file : inFiles) {
      try {
        fHandler.setImage(file);
        // if width has not been provided as arg, just use the image width
        width = ((width == 0) ? fHandler.getBufImg().getWidth() : width);
        height = ((height == 0) ? fHandler.getBufImg().getHeight() : height);

        System.out.print(
            (braille ? new Braille(fHandler.resizeBufImg(width, height), invert, brightness) + "\n" : "")
            + (ascii ? new Ascii(fHandler.getBufImg(), invert, palette, width, height) + "\n" : ""));
      } catch (Exception e) {
        System.err.println("Error while reading input file:");
        System.err.println(e.getMessage());
      }
    }
  }
}
