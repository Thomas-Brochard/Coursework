// Thomas Brochard
// Programming Assignment 3

class MatrixTest {
  public static void main(String[] args) {

    Matrix A = new Matrix(3);
    Matrix B = new Matrix(3);

    A.changeEntry(1, 1, 1.0);
    A.changeEntry(1, 2, 2.0);
    A.changeEntry(1, 3, 3.0);
    A.changeEntry(2, 1, 4.0);
    A.changeEntry(2, 2, 5.0);
    A.changeEntry(2, 3, 6.0);
    A.changeEntry(3, 1, 7.0);
    A.changeEntry(3, 2, 8.0);
    A.changeEntry(3, 3, 9.0);

    B.changeEntry(1, 1, 1.0);
    B.changeEntry(1, 3, 1.0);
    B.changeEntry(2, 2, 1.0);
    B.changeEntry(3, 1, 1.0);
    B.changeEntry(3, 2, 1.0);
    B.changeEntry(3, 3, 1.0);

    System.out.println("A has " + A.getNNZ() + " non-zero entries:");
    System.out.println(A);

    System.out.println("B has " + B.getNNZ() + " non-zero entries");
    System.out.println(B);

    System.out.println("(1.5)*A =");
    System.out.println(A.scalarMult(1.5));

    System.out.println("A+B =");
    System.out.println(A.add(B));

    System.out.println("A+A =");
    System.out.println(A.add(A));

    System.out.println("B-A =");
    System.out.println(B.sub(A));

    System.out.println("A-A =");
    System.out.println(A.sub(A));

    System.out.println("Transpose(A) =");
    System.out.println(A.transpose());

    System.out.println("A*B =");
    System.out.println(A.mult(B));

    System.out.println("B*B =");
    System.out.println(B.mult(B));

  }
}
