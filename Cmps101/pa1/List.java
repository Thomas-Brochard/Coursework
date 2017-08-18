//Thomas Brochard
//Programming Assignment 1

public class List {
  private class node {
    int data;
    node next;
    node previous;

    public node(int data) {
      this.data = data;
    }
  }

  private int size = 0;
  private int index = -1;
  private node cursor = null;
  private node front = null;
  private node back = null;

  public int length() {
    return size;
  }

  public int index() {
    return index;
  }

  public int front() {
    if (size <= 0)
      throw new RuntimeException("List error: front() called on empty List");
    return front.data;
  }

  public int back() {
    if (size <= 0)
      throw new RuntimeException("List error: back() called on empty List");
    return back.data;
  }

  public int get() {
    if (size <= 0)
      throw new RuntimeException("List error: get() called on empty List");
    else if (index < 0)
      throw new RuntimeException("List error: get() called with undefined cursor");
    node curr = front;
    for (int i = 0; i != index; i++) {
      curr = curr.next;
    }
    return curr.data;
  }

  public boolean equals(List L) {
    node curr1 = front;
    node curr2 = L.front;
    if (size != L.length())
      return false;
    while (curr1 != null) {
      if (curr1.data != curr2.data)
        return false;
      curr1 = curr1.next;
      curr2 = curr2.next;
    }
    return true;
  }

  public void clear() {
    index = -1;
    size = 0;
    cursor = null;
    front = null;
    back = null;
  }

  public void moveFront() {
    if (size > 0) {
      index = 0;
      cursor = front;
    }
  }

  public void moveBack() {
    if (size > 0) {
      index = size - 1;
      cursor = back;
    }
  }

  public void movePrev() {
    if (index != -1 && index != 0) {
      index -= 1;
      cursor = cursor.previous;
    } else {
      cursor = null;
      index = -1;
    }
  }

  public void moveNext() {
    if (index != -1 && index != size - 1) {
      index += 1;
      cursor = cursor.next;
    } else {
      cursor = null;
      index = -1;
    }
  }

  public void prepend(int data) {
    node newNode = new node(data);
    if (size == 0) {
      front = newNode;
      back = newNode;
    } else {
      front.previous = newNode;
      newNode.next = front;
      front = newNode;
      if (index != -1)
        index++;
    }
    size += 1;
  }

  public void append(int data) {
    node newNode = new node(data);
    if (size == 0) {
      front = newNode;
      back = newNode;
    } else {
      newNode.previous = back;
      back.next = newNode;
      back = newNode;
    }
    size += 1;
  }

  public void insertBefore(int data) {
    if (size <= 0)
      throw new RuntimeException("List error: insertBefore() called on empty List");
    else if (index < 0)
      throw new RuntimeException("List error: insertBefore() called with undefined cursor");
    node newNode = new node(data);
    node prevNode = cursor.previous;
    if (prevNode != null) {
      prevNode.next = newNode;
      newNode.previous = prevNode;
    } else {
      front = newNode;
    }
    newNode.next = cursor;
    cursor.previous = newNode;
    size += 1;
    index++;
  }

  public void insertAfter(int data) {
    if (size <= 0)
      throw new RuntimeException("List error: insertAfter() called on empty List");
    else if (index < 0)
      throw new RuntimeException("List error: insertAfter() called with undefined cursor");
    node newNode = new node(data);
    node nextNode = cursor.next;
    if (nextNode != null) {
      nextNode.previous = newNode;
      newNode.next = nextNode;
    } else {
      back = newNode;
    }
    newNode.previous = cursor;
    cursor.next = newNode;
    size += 1;
  }

  public void deleteFront() {
    if (size <= 0)
      throw new RuntimeException("List error: deleteFront() called on empty List");
    if (index == 0) {
      cursor = null;
      index = -1;
    }
    front = front.next;
    front.previous = null;
    if (index == 0) {
      index = -1;
    }
    size -= 1;
  }

  public void deleteBack() {
    if (size <= 0)
      throw new RuntimeException("List error: deleteBack() called on empty List");
    if (index == size - 1) {
      cursor = null;
      index = -1;
    }
    back = back.previous;
    back.next = null;
    if (index == size - 1) {
      index = -1;
    }
    size -= 1;
  }

  public void delete() {
    if (size <= 0)
      throw new RuntimeException("List error: delete() called on empty List");
    else if (index < 0)
      throw new RuntimeException("List error: delete() called with undefined cursor");
    if (cursor.previous != null) {
      cursor.previous.next = cursor.next;
    } else {
      front = cursor.next;
    }
    if (cursor.next != null) {
      cursor.next.previous = cursor.previous;
    } else {
      back = cursor.previous;
    }
    size -= 1;
    index = -1;
    cursor = null;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    node curr = front;
    while (curr != null) {
      sb.append(curr.data + " ");
      curr = curr.next;
    }
    return sb.toString();
  }

  public List copy() {
    List newList = new List();
    node curr = front;
    while (curr != null) {
      newList.append(curr.data);
      curr = curr.next;
    }
    return newList;
  }

  public List concat(List L) {
    List newList = L.copy();
    node curr = back;
    while (curr != null) {
      newList.prepend(curr.data);
      curr = curr.previous;
    }
    return newList;
  }
}
