// $Id: jfmt.java,v 1.2 2016-03-27 01:00:54-07 - - $
// Thomas Brochard
// tbrochar
//
// Starter code for the jfmt utility.  This program is similar
// to the example code jcat.java, which iterates over all of its
// input files, except that this program shows how to use
// String.split to extract non-whitespace sequences of characters
// from each line.
//

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.*;

   // Main function scans arguments and opens/closes files.
public class jfmt{
   public static void main (String[] args) {

       String JAR_NAME = get_jarname();
       final int EXIT_SUCCESS = 0;
       final int EXIT_FAILURE = 1;
       int exit_status = EXIT_SUCCESS;
       int width = 65;
       boolean hasOperand = false;
      // check for bad operand ex. -80s 
       
       if(args.length>0 && args[0].matches("-\\d+")){
          width = Integer.parseInt(args[0]) * -1;
          hasOperand= true;
       }
       else if(args.length>0 && args[0].matches("-.+")){
           System.err.println("Usage: jfmt.java [-width] [filename...]");
           exit_status = EXIT_FAILURE;
           System.exit(exit_status);
       }
     
      if (args.length == 0 || (args.length==1 && hasOperand == true)){
         // There are no filenames given on the command line.
         format (new Scanner (in), width);
      }
      
      else{
         // Iterate over each filename given on the command line.
         for(int argix = 0; argix < args.length; ++argix) {
            String filename = args[argix];
          
            // If arg is an operand ignore it
           if (argix==0 && hasOperand == true) 
              continue; 
        
            else {
               // Open the file and read it, or error out.
               try {
                  Scanner infile = new Scanner (new File (filename));
                  format (infile, width);
                  infile.close();
               }catch (IOException error) {
                  exit_status = EXIT_FAILURE;
                  err.printf ("%s: %s%n", JAR_NAME,
                              error.getMessage());
               }
            }
         }
     }        
  exit(exit_status); 
}     


   public static String get_jarname() {
      String jarpath = getProperty ("java.class.path"); 
      int lastslash = jarpath.lastIndexOf('/');
      if(lastslash < 0) return jarpath;
      return jarpath.substring (lastslash + 1);
}     


   public static void format (Scanner infile, int width) {
// Create list and cycle from line to line      
      System.out.println();
      List<String> words = new LinkedList<String>();
      boolean firstLoop = true; 
      boolean firstLineOfFile = true;
      while(infile.hasNextLine()){
         String line = infile.nextLine();

// skips extra lines and print old words 
         if(line.length()==0 && firstLoop==false){
               print_paragraph(words,width);
               System.out.printf("\n\n");
             
            while(line.length() == 0){

                 if(infile.hasNextLine())
                    line = infile.nextLine();

                 else if(line.length() == 0){
                    break;
                 }

                 else{
                    System.out.println();
                    break;
                 }
            }
         }        
         for(String word: line.split("\\s+")) {
            if(word.length() != 0)
               words.add(word);
         }
        firstLoop = false;  
     }
   }
   
   public static void print_paragraph(List<String> words, int width) {
      
      int count=0;
      boolean firstLineOfPara = true; 
      for(String word: words){
         if(count+word.length()+1<=width && count!=0) {
            System.out.printf(" %s",word);
            count = count + word.length()+ 1;
         }
         
         else if((count+word.length()<=width && count==0) || 
                                           firstLineOfPara==true) {
            System.out.printf("%s",word);
            count = count + word.length();
            firstLineOfPara = false; 
        }

         else{
            System.out.printf("\n%s",word);
            count=word.length();}
         }        
      
     words.clear();
   }  

}











 
