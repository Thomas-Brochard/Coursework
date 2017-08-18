
//Thomas Brochard
//Programming assignment 1
import java.io.*;
import java.util.Scanner;

public class Lex {
  public static void main(String[] args) {
    // Check for arguments
    if (args.length != 2) {
      System.out.println("Usage: Lex [] []");
      System.exit(1);
    }
    // Read input, get String[] and length
    Scanner sc = null;
    PrintWriter out = null;
    try {
      sc = new Scanner(new File(args[0]));
      out = new PrintWriter(new FileWriter(args[1]));
    } catch (IOException e) {
      throw new RuntimeException("File not found: " + args[0] + ", " + args[1]);
    }
    sc.useDelimiter("\\Z");
    String content = sc.next();
    String[] words = content.split("\n");

    // initlialize indeces
    int[] indices = new int[words.length];
    for (int i = 0; i < indices.length; i++)
      indices[i] = i;

    List myList = new List();
    myList.append(0);
    for (int i = 1; i < words.length; i++) {
      myList.moveFront();
      while (myList.index() != -1 && words[i].compareTo(words[myList.get()]) > 0) {
        myList.moveNext();
      }
      if (myList.index() != -1)
        myList.insertBefore(i);
      else
        myList.append(i);
    }

    myList.moveFront();
    while (myList.index() != -1) {
      out.println(words[myList.get()]);
      myList.moveNext();
    }
    sc.close();
    out.close();
  }
}
