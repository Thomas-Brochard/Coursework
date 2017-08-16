// $Id: treemap.java,v 1.62 2016-02-16 23:41:17-08 - - $

// Thomas Brochard
// Tbrochar
//
// Development version of treemap.
// To be deleted and replaced by an actual implementation that
// does *NOT* use java.util.TreeMap.

import static java.lang.System.*;

class treemap {

   class tree {
      String key;
      String value;
      tree left;
      tree right;
   //   static boolean dup=false;

   public tree(String key, String value){
   this.key = key;
   this.value = value.trim();
   this.left = null;
   this.right = null;
   }
  } 
   tree root = null;

   public String get (String key) {
     // System.out.printf("getting item at %s%n", key);
      tree val = traverse(root, key);
    

     if(val == null) return null;
     // System.out.printf("returning value: %s%n", val);
      return val.value;  
   }
   
//returns reference to node with given key
   public tree traverse(tree node, String key){
      if(node == null) return node;

      if(key.compareTo(node.key) < 0){ 
        // System.out.println("left");
         return traverse(node.left, key);
      }

      else if(key.compareTo(node.key) > 0){
        // System.out.println("right");
         return traverse(node.right, key);
      }
      return node;
    }


   public void print_tree(tree node){
      if(node == null) return;
      System.out.printf("%s ", node.key);
      print_tree(node.left);
      print_tree(node.right);
   }

   public String put(String key, String value){
      key = key.toUpperCase().trim();
      if(root == null){
         tree tmp = new tree(key, value);
         root = tmp;
        // System.out.printf("Node added as a root: 
        //                    %s : %s%n", key, value); 
        return value;
      }
      return doPut(root, key, value);
   }
     

   public String doPut(tree node, String key, String value) {
      if(node == null) return null;
      
      int cmp = key.compareTo(node.key);
      
      if(cmp < 0){
         if(node.left == null){
            node.left = new tree(key, value);
            //System.out.printf("Node added left of %s: %s :
            //%s%n", node.key, node.left.key, node.left.value);
            return node.value;
        }else
            return doPut(node.left, key, value);
      
      }else if(cmp>0){
          if(node.right == null) {
             node.right = new tree(key, value);
             //System.out.printf("Node added right of %s: %s : 
             //%s%n", node.key, node.right.key, node.right.value);
             return node.value;
         }else
            return doPut(node.right, key, value);
       } 
       
       else{
          String tmp = node.value;
          node.value = value;
          return tmp;
   }
}
  

   public void debug_tree () {
      debug_recur (root, 0);
   }

   private void debug_recur(tree node, int depth) {
      if(node == null) return;
      debug_recur(node.left, depth+1);   
  
      System.out.printf("%3d \"%s\" \"%s\" %s %s%n",
                depth, node.key, node.value, node.left, node.right);

      debug_recur(node.right, depth+1);   
   }

}
