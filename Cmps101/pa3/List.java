//Thomas Brochard
//Programming Assignment 3

public class List {
  private class Node {
    Object entry;
    Node next;
    Node previous;

    public Node(Object entry) {
      this.entry = entry;
    }
  }

  private int size = 0;
  private int index = -1;
  private Node cursor = null;
  private Node front = null;
  private Node back = null;

  public int length() {
    return size;
  }

  public int index() {
    return index;
  }

  public Object front() {
    if (size <= 0)
      throw new RuntimeException("List error: front() called on empty List");
    return front.entry;
  }

  public Object back() {
    if (size <= 0)
      throw new RuntimeException("List error: back() called on empty List");
    return back.entry;
  }

  public Object get() {
    if (size <= 0)
      throw new RuntimeException("List error: get() called on empty List");
    else if (index < 0)
      throw new RuntimeException("List error: get() called with undefined cursor");
    Node curr = front;
    for (int i = 0; i != index; i++) {
      curr = curr.next;
    }
    return curr.entry;
  }

  public boolean equals(List L) {
    Node curr1 = front;
    Node curr2 = L.front;
    if (size != L.length())
      return false;
    while (curr1 != null) {
      if (curr1.entry != curr2.entry)
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

  public void prepend(Object entry) {
    Node newNode = new Node(entry);
    if (size == 0) {
      front = newNode;
      back = newNode;
    } else {
      front.previous = newNode;
      newNode.next = front;
      front = newNode;
      if (index != -1) {
        index++;
      }
    }
    size += 1;
  }

  public void append(Object entry) {
    Node newNode = new Node(entry);
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

  public void insertBefore(Object entry) {
    if (size <= 0)
      throw new RuntimeException("List error: insertBefore() called on empty List");
    else if (index < 0)
      throw new RuntimeException("List error: insertBefore() called with undefined cursor");
    Node newNode = new Node(entry);
    Node prevNode = cursor.previous;
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

  public void insertAfter(Object entry) {
    if (size <= 0)
      throw new RuntimeException("List error: insertAfter() called on empty List");
    else if (index < 0)
      throw new RuntimeException("List error: insertAfter() called with undefined cursor");
    Node newNode = new Node(entry);
    Node nextNode = cursor.next;
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
    Node curr = front;
    while (curr != null) {
      sb.append(curr.entry + " ");
      curr = curr.next;
    }
    return sb.toString();
  }
}
