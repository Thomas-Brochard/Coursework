// $Id: intqueue.java,v 1.23 2016-02-01 12:34:41-08 - - $
// Thomas Brochard
// Tbrochar

import java.util.Iterator;
import java.util.NoSuchElementException;

class intqueue implements Iterable<Integer> {

   private class node {
      int linenr;
      node link;
   
      public node(int linenr, node link){
         this.linenr = linenr;
         this.link = link;
      }
   }

   private int count = 0;
   private node front = null;
   private node rear = null;

   public void insert (int number) {
      ++count;
         if(this.front == null){
         this.front = new node(number, this.front);
         this.rear = this.front;
      }
      else{
         node myNode = new node(number,null);
         this.rear.link = myNode;
         this.rear = myNode;
      }
   }

  public boolean empty() {
      return count == 0;
   }

   public intqueue(int linenr){
   this.insert(linenr);
   }

   public int getcount() {
      return count;
   }

   public void incrementCount(){
   count++;
   }
   
   public Iterator<Integer> iterator() {
      return new iterator();
   }

   private class iterator implements Iterator<Integer> {
      node curr = front;

      public boolean hasNext() {
         return curr != null;
      }

      public Integer next() {
         if (curr == null) throw new NoSuchElementException();
         Integer next = curr.linenr;
         curr = curr.link;
         return next;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

}

