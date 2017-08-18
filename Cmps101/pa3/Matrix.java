// Thomas Brochard
// Programing assignment 3
//
public class Matrix {
  private int size;
  private int NNZ;
  public List[] matrix;

  public Matrix(int n) {
    this.size = n;
    this.NNZ = 0;
    matrix = new List[n];
    for (int i = 0; i < n; i++)
      matrix[i] = new List();
  }

  private class Entry {
    int col;
    double data;

    public Entry(int col, double data) {
      this.col = col;
      this.data = data;
    }

    public int getCol() {
      return this.col;
    }

    public double getData() {
      return this.data;
    }

    // Entry toString()
    public String toString() {
      return "(" + Integer.toString(this.col) + ", " + Double.toString(this.data) + ")";
    }

    // Entry equals
    public boolean equals(Object x) {
      Entry curr = (Entry) x;
      if (this.data != curr.getData())
        return false;
      if (this.col != curr.getCol())
        return false;
      return true;
    }
  }

  // -------------------access functions------------------//
  int getSize() {
    return size;
  }

  int getNNZ() {
    return NNZ;
  }

  // Matrix equals()
  public boolean equals(Object x) {
    Matrix curr = (Matrix) x;
    if (this.size != curr.getSize())
      return false;
    Entry curr1;
    Entry curr2;
    for (int i = 0; i < this.size; i++) {
      if (this.matrix[i].length() != curr.matrix[i].length())
        return false;

      this.matrix[i].moveFront();
      curr.matrix[i].moveFront();
      while (matrix[i].index() != -1) {
        curr1 = (Entry) matrix[i].get();
        curr2 = (Entry) curr.matrix[i].get();
        if (!curr1.equals(curr2))
          return false;
        this.matrix[i].moveNext();
        curr.matrix[i].moveNext();
      }
    }
    return true;
  }

  // ------------------Manipulation Procedures-----------//

  // Resets the matrix instance to the empty state
  void makeZero() {
    for (int i = 0; i < this.size; i++)
      matrix[i].clear();
    this.NNZ = 0;
  }

  // Returns a copy of the Matrix instance
  Matrix copy() {
    Matrix newMatrix = new Matrix(this.size);
    Entry curr;
    for (int i = 0; i < this.size; i++) {
      List myList = matrix[i];
      myList.moveFront();
      while (myList.index() != -1) {
        curr = (Entry) myList.get();
        newMatrix.changeEntry(i + 1, curr.getCol(), curr.getData());
        myList.moveNext();
      }
    }
    return newMatrix;
  }

  // Changes entry in the Matrix instance
  void changeEntry(int i, int j, double x) {
    if (0 > i || i > size || 1 > j || j > size)
      throw new RuntimeException("Matrix error: changeEntry() called on invalid coordinatets: " + i + "," + j);
    List myList = matrix[i - 1];

    // If list is empty, and x != 0, add new entry
    if (myList.length() == 0 && x != 0) {
      myList.append(new Entry(j, x));
      this.NNZ += 1;
    } else if (myList.length() != 0) {
      myList.moveFront();
      Entry curr = (Entry) myList.get();
      while (myList.index() != -1 && j > curr.getCol()) {
        myList.moveNext();
        if (myList.index() != -1)
          curr = (Entry) myList.get();
      }
      // If col is the new greatest col, and x!=0
      if (myList.index() == -1 && x != 0) {
        myList.append(new Entry(j, x));
        this.NNZ += 1;
      }
      // If x==0 and column index is not already zero, set to 0
      else if (x == 0 && curr.getCol() == j) {
        myList.delete();
        this.NNZ -= 1;
      }
      // Replace col if col exists and x!=0
      else if (x != 0 && curr.getCol() == j) {
        myList.insertBefore(new Entry(j, x));
        myList.delete();
      }
      // If x!=0 insert into correct col
      else if (x != 0) {
        myList.insertBefore(new Entry(j, x));
        this.NNZ += 1;
      }
    }
  }

  // Multiplies Matrix instance by a scalar (arg)
  Matrix scalarMult(double x) {
    Matrix newMatrix = new Matrix(this.size);
    Entry curr;
    for (int i = 0; i < this.size; i++) {

      matrix[i].moveFront();
      while (matrix[i].index() != -1) {
        curr = (Entry) matrix[i].get();
        newMatrix.changeEntry(i + 1, curr.getCol(), curr.getData() * x);
        matrix[i].moveNext();
      }
    }
    return newMatrix;
  }

  // Adds the Matrix instance and Matrix argument
  Matrix add(Matrix M) {
    if (size != M.getSize())
      throw new RuntimeException("Matrix error: add() called on matrix of differing size");

    Matrix newMatrix = new Matrix(this.size);
    M = M.copy();

    Entry curr1;
    Entry curr2;
    List M_List1;
    List M_List2;

    for (int i = 0; i < this.size; i++) {
      M_List1 = matrix[i];
      M_List1.moveFront();
      M_List2 = M.matrix[i];
      M_List2.moveFront();
      /* If either list is empty, simply fill the newList with the opposing List */
      if (M_List1.length() != 0 && M_List2.length() == 0) {
        while (M_List1.index() != -1) {
          curr1 = (Entry) M_List1.get();
          newMatrix.changeEntry(i + 1, curr1.getCol(), curr1.getData());
          M_List1.moveNext();
        }
        continue;
      } else if (M_List2.length() != 0 && M_List1.length() == 0) {
        while (M_List2.index() != -1) {
          curr2 = (Entry) M_List2.get();
          newMatrix.changeEntry(i + 1, curr2.getCol(), curr2.getData());
          M_List2.moveNext();
        }
        continue;
      }
      // If both Lists are empty continue
      if (M_List1.length() == 0 && M_List2.length() == 0)
        continue;

      /* Otherwise cursor down the list and add accordingly */
      curr1 = (Entry) M_List1.get();
      curr2 = (Entry) M_List2.get();

      while (M_List1.index() != -1 && M_List2.index() != -1) {

        curr1 = (Entry) M_List1.get();
        curr2 = (Entry) M_List2.get();

        if (curr1.getCol() < curr2.getCol()) {
          // System.out.println("A /"+curr1.getData()+","+curr2.getData()+"/");
          newMatrix.changeEntry(i + 1, curr1.getCol(), curr1.getData());
          M_List1.moveNext();
        } else if (curr1.getCol() > curr2.getCol()) {
          // System.out.println("B /"+curr1.getData()+","+curr2.getData()+"/");
          newMatrix.changeEntry(i + 1, curr2.getCol(), curr2.getData());
          M_List2.moveNext();
        } else if (curr1.getCol() == curr2.getCol()) {
          // System.out.println("C /"+curr1.getData()+","+curr2.getData()+"/");
          newMatrix.changeEntry(i + 1, curr1.getCol(), curr1.getData() + curr2.getData());
          M_List1.moveNext();
          M_List2.moveNext();
        }
      }

      // If one list runs out of data, simply fill with he opposing list, rather than
      // comparing
      while (M_List1.index() != -1) {
        // System.out.println("Complete L1");
        curr1 = (Entry) M_List1.get();
        newMatrix.changeEntry(i + 1, curr1.getCol(), curr1.getData());
        M_List1.moveNext();
      }
      while (M_List2.index() != -1) {
        // System.out.println("Complete 2");
        curr2 = (Entry) M_List2.get();
        newMatrix.changeEntry(i + 1, curr2.getCol(), curr2.getData());
        M_List2.moveNext();
      }
    }
    return newMatrix;
  }

  // Subtract the Matrix instance and Matrix argument
  Matrix sub(Matrix M) {
    if (size != M.getSize())
      throw new RuntimeException("Matrix error: sub() called on matrix of differing size");

    Matrix inverted = M.scalarMult(-1);
    Matrix newMatrix = this.add(inverted);
    return newMatrix;
  }

  // return a new Transposition of the Matrix instance
  Matrix transpose() {
    Matrix newMatrix = new Matrix(this.size);
    List currList;
    Entry currEntry;
    for (int i = 0; i < this.size; i++) {
      currList = matrix[i];
      currList.moveFront();
      while (currList.index() != -1) {
        currEntry = (Entry) currList.get();
        // System.out.println(currEntry.getData()+" ->
        // "+(currEntry.getCol()-1)+","+(i+1));
        newMatrix.changeEntry(currEntry.getCol(), i + 1, currEntry.getData());
        currList.moveNext();
      }
    }
    return newMatrix;
  }

  // Multiply the Matrix instance and Matrix argument
  Matrix mult(Matrix M) {
    if (size != M.getSize())
      throw new RuntimeException("Matrix error: mult() called on matrix of differing size");
    Matrix newMatrix = new Matrix(this.size);
    double total = 0;
    M = M.transpose();
    for (int i = 0; i < this.size; i++) {
      if (matrix[i].length() == 0)
        continue;
      for (int k = 0; k < this.size; k++) {
        total = dot(matrix[i], M.matrix[k]);
        if (total != 0)
          newMatrix.changeEntry(i + 1, k + 1, total);
      }
    }
    return newMatrix;
  }

  // MULTIPLY HELPER:
  // Find the dot product of the two arg rows
  private double dot(List L1, List L2) {
    Entry curr1;
    Entry curr2;
    double total = 0;
    if (L2.length() == 0 || L2.length() == 0)
      return 0;
    L1.moveFront();
    L2.moveFront();
    while (L1.index() != -1 && L2.index() != -1) {

      curr1 = (Entry) L1.get();
      curr2 = (Entry) L2.get();

      if (curr1.getCol() == curr2.getCol()) {
        // System.out.print("A ");// (before index:
        // "+curr1.getCol()+","+curr2.getCol()+")");
        total += curr1.getData() * curr2.getData();
        L1.moveNext();
        L2.moveNext();
      } else if (curr1.getCol() < curr2.getCol()) {
        // System.out.print("B ");// (before index:
        // "+curr1.getData()+","+curr2.getData()+")");
        L1.moveNext();
      } else if (curr1.getCol() > curr2.getCol()) {
        // System.out.print("C ");// (before index:
        // "+curr1.getCol()+","+curr2.getCol()+")");
        L2.moveNext();
      }
    }
    return total;
  }

  // --------------------Other Functions--------------//

  // Matrix toString()
  public String toString() {
    String total = "";
    List myList;
    Entry curr;
    for (int i = 0; i < matrix.length; i++) {
      myList = matrix[i];
      if (myList.length() == 0)
        continue;
      myList.moveFront();
      total += (i + 1) + ": ";
      while (myList.index() != -1) {
        total += (Entry) myList.get();
        if (myList.index() != (myList.length() - 1))
          total += " ";
        myList.moveNext();
      }
      total += "\n";
    }
    return total;
  }
}
