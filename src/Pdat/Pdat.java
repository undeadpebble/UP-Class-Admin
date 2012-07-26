package Pdat;

/* Generic driver frame file for Coco/R for Java
   PDT  October 2005 - for CSC 301 pracs
   Modify this to suit your own purposes - hints are given below! */

//  ----------------------- you may need to change the "import" clauses:

import java.io.*;

public class Pdat {

  public static void main (String[] args) {
    boolean mergeErrors = false;
    String inputName = null;

    // ------------------------ you may need to process command line parameters:

    for (int i = 0; i < args.length; i++) {
      if (args[i].toLowerCase().equals("-l")) mergeErrors = true;
      else inputName = args[i];
    }
    if (inputName == null) {
      System.err.println("No input file specified");
      System.exit(1);
    }

    int pos = inputName.lastIndexOf('/');
    if (pos < 0) pos = inputName.lastIndexOf('\\');
    String dir = inputName.substring(0, pos+1);

/*++++ If the parser needs an output file, include a section like the following
       and add a line

            public static OutFile output;

       to your ATG file.

    String outputName = null;
    pos = inputName.lastIndexOf('.');
    if (pos < 0) outputName = inputName + ".out";
    else outputName = inputName.substring(0, pos) + ".out";
    Parser.output = new OutFile(outputName);
    if (Parser.output.openError()) {
      System.err.println("cannot open " + outputName);
      System.exit(1);
    }

++++++ */

    Scanner.Init(inputName);
    Errors.Init(inputName, dir, mergeErrors);
    //  ----------------------- add other initialization if required:
    Parser.Parse();
    Errors.Summarize();
    //  ----------------------- add other finalization if required:

/*++++ If the parser needs an output file, uncomment this section
    Parser.output.close();
++++++ */

  }

} // end driver
