// $Id: airport.java,v 1.31 2016-02-16 23:30:31-08 - - $

//Thomas Brochard
//Tbrochar
// Starter code for the airport utility.
//

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class airport {
   private static final String STDIN_FILENAME = "-";
   
   public static treemap load_database (String database_name) {
      treemap tree = new treemap ();
      try {
         Scanner database = new Scanner (new File (database_name));
         for (int linenr = 1; database.hasNextLine(); ++linenr) {
            String line = database.nextLine();
            if (line.matches ("^\\s*(#.*)?$")) continue;
            String[] keyvalue = line.split (":");
            if (keyvalue.length != 2) {
               misc.warn (database_name, linenr, "invalid line");
               continue;
            }
            tree.put (keyvalue[0], keyvalue[1]);
         }
         database.close();
      }catch (IOException error) {
         misc.warn (error.getMessage());
      }
//      tree.print_tree(tree.root);
      return tree;
   } 

   public static void main (String[] args) {
      boolean opt_debug = false;

      if(args.length > 2 || args.length == 0){
         System.err.printf("Usage: airport [-d] database%n");
         System.exit(1);
      }
   
      else if(args.length == 2){
         if(args[0].compareTo("-d") != 0 ||  args[1].charAt(0) == '-'){
            System.out.printf("Usage: airport [-d] database%n");
            System.exit(1);   
         }
         if(args[0].compareTo("-d") == 0)
            opt_debug = true;
      }

      
      treemap tree;
      if(opt_debug == true)
         tree = load_database (args[1]);
      else
         tree = load_database (args[0]);
      
      if(opt_debug == true){
         tree.debug_tree();
         exit(0);
      }
      
      Scanner stdin = new Scanner (in);
      while (stdin.hasNextLine()) {
         String airport = stdin.nextLine().toUpperCase().trim();
         if(airport.substring(0,2).equals("//"))
            continue;
          
         String airport_name = tree.get (airport); 
         if (airport_name == null) {
            err.printf ("%s: no such airport%n", airport);
         }else {
            out.printf ("%s = %s%n", airport, airport_name);
         }
      }
      //tree.debug_tree ();
      exit (misc.exit_status);
      
   }

}
