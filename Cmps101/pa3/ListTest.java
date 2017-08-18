// Thomas Brochard
// Programming Assignment 3

class ListTest {

  public static void main(String[] args) {
    // This test uses Integer Objects in place of the Entry Objects used in
    // Matrix.java

    // Initialize new lists
    List A = new List();
    List B = new List();
    List C = new List();
    // Check for append() and prepend()
    for (int i = 1; i <= 20; i++) {
      A.append(i);
      B.prepend(i);
    }
    // Test toString()
    System.out.println(A);
    System.out.println(B);

    // Test moveFront(); index(); moveNext(); get(); For walking down each list
    for (A.moveFront(); A.index() >= 0; A.moveNext()) {
      System.out.print(A.get() + " ");
    }
    System.out.println();

    // Test moveBack(); movePrev();
    for (B.moveBack(); B.index() >= 0; B.movePrev()) {
      System.out.print(B.get() + " ");
    }
    System.out.println();

    // Test List.equals();
    System.out.println(A.equals(B));

    A.moveFront();
    for (int i = 0; i < 5; i++)
      A.moveNext(); // at index 5
    A.insertBefore(-1); // at index 6
    for (int i = 0; i < 9; i++)
      A.moveNext(); // at index 15
    A.insertAfter(-2);
    for (int i = 0; i < 5; i++)
      A.movePrev(); // at index 10
    A.delete();
    System.out.println(A);
    A.deleteBack();
    System.out.println(A);
    A.deleteFront();
    System.out.println(A);
    System.out.println(A.length());
    A.clear();

    for (int i = 0; i < 3; i++)
      A.append(i);
    System.out.println(A);
    A.deleteFront();
    System.out.println(A);
    A.deleteBack();
    System.out.println(A);
    A.clear();

    System.out.println(A.length());

    // Test List.equals() when they are indeed equal;
    System.out.println(C.equals(A));

  }
}

// Output of this program:
// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
// 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
// false
// 1 2 3 4 5 -1 6 7 8 9 11 12 13 14 15 -2 16 17 18 19 20
// 1 2 3 4 5 -1 6 7 8 9 11 12 13 14 15 -2 16 17 18 19
// 2 3 4 5 -1 6 7 8 9 11 12 13 14 15 -2 16 17 18 19
// 19
// 0 1 2
// 1 2
// 1
// 0
// true
