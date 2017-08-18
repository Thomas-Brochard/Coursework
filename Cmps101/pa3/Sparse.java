
// Thomas Brochard
// Programming assignment 3

import java.io.*;
import java.util.Scanner;

class Sparse {
  public static void main(String[] args) {

    // Check for args
    if (args.length != 2) {
      System.out.println("Usage: Sparse [] []");
      System.exit(1);
    }

    // Open arg files
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
    String[] section = content.split("\n\n");
    if (section.length != 3)
      throw new RuntimeException("Invalid File Format");

    // Use split to obtain values for two matricce inputs
    String[] opt = section[0].split(" ");
    int n = Integer.parseInt(opt[0]);
    int a = Integer.parseInt(opt[1]);
    int b = Integer.parseInt(opt[2]);

    Matrix A = new Matrix(n);
    Matrix B = new Matrix(n);

    String[] section_1 = section[1].split("\n");
    String[] aEntries = new String[3];
    for (int i = 0; i < section_1.length; i++) {
      aEntries = section_1[i].split(" ");
      A.changeEntry(Integer.parseInt(aEntries[0]), Integer.parseInt(aEntries[1]), Double.parseDouble(aEntries[2]));
    }

    String[] section_2 = section[2].split("\n");
    String[] bEntries = new String[3];
    for (int i = 0; i < section_2.length; i++) {
      bEntries = section_2[i].split(" ");
      B.changeEntry(Integer.parseInt(bEntries[0]), Integer.parseInt(bEntries[1]), Double.parseDouble(bEntries[2]));
    }

    // Implements tests
    out.println("A has " + A.getNNZ() + " non-zero entries:");
    out.println(A);

    out.println("B has " + B.getNNZ() + " non-zero entries:");
    out.println(B);

    out.println("(1.5)*A =");
    out.println(A.scalarMult(1.5));

    out.println("A+B =");
    out.println(A.add(B));

    out.println("A+A =");
    out.println(A.add(A));

    out.println("B-A =");
    out.println(B.sub(A));

    out.println("A-A =");
    out.println(A.sub(A));

    out.println("Transpose(A) =");
    out.println(A.transpose());

    out.println("A*B =");
    out.println(A.mult(B));

    out.println("B*B =");
    out.println(B.mult(B));

    sc.close();
    out.close();
  }
}
