package braille.main;

import java.io.File;
import java.util.ArrayList;

import braille.convert.ASCII;
import braille.convert.Braille;
import braille.input.FileHandler;

public class Main {
  public static void main(String... args) {
    Boolean ASCII = false, BRAILLE = false, INVERT = false, IGNORE = false;
    String PALETTE = "  .-~=*%#W";
    int BRIGHTNESS = 100, WIDTH, HEIGHT; // TODO: set W/H according to the bufferedImage if no other input is provided
    ArrayList<File> INFILES = new ArrayList<>();
    FileHandler fHandler = new FileHandler();
    
    if (args.length != 0) {
      for (int i = 0; i < args.length; i++) {
        if (IGNORE) {IGNORE = false; continue;}
        switch (args[i]) {
          case "--help":
          case "-h":
            System.out.println(
                "imgconv [ARGS] <input files>\n"
                +"\nARGS:"
                +"\t'-h'/'--help'                                  print this help message\n"
                +"\t'-A'/'--ascii'                                 convert input image file to ascii\n"
                +"\t'-B'/'--braille'                               convert input image file to braille dot patterns\n\n"
                +"\t'-i'/'--invert'                                invert the input image before conversion\n"
                +"\t'-p \"[STRING]\"'/'--palette \"[STRING]\"'         the string after this argument is assigned as the char palette (only for ASCII)\n"
                +"\t'-b [INT]'/'--brightness [INT]'                uses the given integer value as brightness cutoff (only for BRAILLE) \n\n"
                +"\t'-w \"[INT]\"'/'--width \"[INT]\"'                 Changes width to [INT] chars\n"
                +"\t'-h \"[INT]\"'/'--height \"[INT]\"'                Changes height to [INT] chars\n\n"
                +"\t'-o \"[PATH]\"'/'--outfile \"[PATH]\"'             prints the converted ASCII/Braille to the given file\n"
                );
            break;

          case "--ascii":
          case "-A":
            ASCII = true;
            break;

          case "-B":
          case "--braille":
            BRAILLE = true;
            break;

          case "--invert":
          case "-i":
            INVERT = true;
            break;

          case "--palette":
          case "-p":
            try {
              PALETTE = args[i+1];
              IGNORE = true;
            } catch (IndexOutOfBoundsException e) {
              System.err.println("No value provided for argument '-p', palette (String) is required. Ignoring.");
            }
            break;
          
          case "--brightness":
          case "-b":
            try {
              BRIGHTNESS = Integer.parseInt(args[i+1]);
              IGNORE = true;
            } catch (IndexOutOfBoundsException e) {
              System.err.println("No value provided for argument '-b', brightness (int) is required. Ignoring.");
            } catch (NumberFormatException e) {
              System.err.println("The given value is not a valid integer. Ignoring.");
            }
            break;

          default:
            File infile = new File(args[i]);
            if (infile.exists() && !infile.isDirectory()) {
              INFILES.add(infile);
            } else {
              System.out.println("Unknown Argument: '" + args[i] + "' try '-h' for help. Ignoring Argument");
            }
            break;
        }
      }
    } else {
      System.err.println("Error: No arguments provided (try -h for help)");
    }
    for (File file : INFILES) {
      try {
        fHandler.setImage(file);
        System.out.print(
            (BRAILLE ? new Braille(fHandler.getBufImg(), INVERT, BRIGHTNESS) + "\n" : "")
            + (ASCII ? new ASCII(fHandler.getBufImg(), INVERT, PALETTE) + "\n" : ""));
      } catch (Exception e) {
        System.err.println("Error while reading input file:");
        System.err.println(e.getMessage());
      }
    }
  }
}
