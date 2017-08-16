// $Id: listmap.java,v 1.39 2016-02-01 12:34:34-08 - - $
// Thomas Brochard
// Tbrochar
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import static java.lang.System.*;

class listmap implements Iterable<Entry<String,intqueue>> {

   private class node implements Entry<String,intqueue> {
      String key;
      intqueue queue;
      node link;

     public node(String key, intqueue queue, node link){
        this.key = key;
        this.queue = queue;
        this.link = link;
     }

      public String getKey() {
         return key;
      }
      public intqueue getValue() {
         return queue;
      }
      public intqueue setValue (intqueue queue) {
         throw new UnsupportedOperationException();
      }
   }
   private node head = null;

   public listmap() {
      // Not needed, since head defaults to null anyway.
   }

   public void insert (String key, int linenr) {
      String word = key;
      node prev = null;
      node curr = head;
      int cmp = 1;
      
      while(curr != null){
         cmp = curr.getKey().compareTo(word);
         if(cmp >= 0) break;
         prev = curr;
         curr = curr.link;
      }
      
      if(cmp != 0){
         if(prev == null) 
            this.head = new node(word, new intqueue(linenr), curr);
         else 
            prev.link = new node(word, new intqueue(linenr), curr);
      }
      else{
         curr.getValue().insert(linenr); 
     
      }
   }        

   public Iterator<Entry<String,intqueue>> iterator() {
      return new iterator();
   }


   private class iterator
           implements Iterator<Entry<String,intqueue>> {
      node curr = head;

      public boolean hasNext() {
         return curr != null;
      }

      public Entry<String,intqueue> next() {
         if (curr == null) throw new NoSuchElementException();
         node next = curr;
         curr = curr.link;
         return next;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

   }

}
