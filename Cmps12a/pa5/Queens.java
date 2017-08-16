// Queens.java
// 1479546
// pa5
// Solves the n-Queen problem w/ options of printing all solutions or the number of solutions
import java.util.Scanner;

public class Queens{
   public static void main(String[] args){
      
      boolean verbose = false;
      int n=0; 
      int count=0;

      if(args.length == 0){ 
          printUsage();
          System.exit(1);
      }

      if(args.length == 1 && !isInteger(args[0])){
      printUsage();
      System.exit(1); 
      }
     
      if(args.length == 1 && isInteger(args[0])) 
	 if(Integer.parseInt(args[0])<0){
            printUsage();
	    System.exit(1);
        }
      if(args.length == 2 && isInteger(args[1]))
         if(Integer.parseInt(args[1])<0){
            printUsage();
            System.exit(1);
         }
      if((!args[0].equals("-v") && !isInteger(args[0])) || (args.length == 2 && !isInteger(args[1])) ){ 
        printUsage();
        System.exit(1); 
      }
  

      if(args[0].equals("-v"))
         verbose=true;      
      if(verbose==false)
        n=Integer.parseInt(args[0]);
      else if (verbose==true)
        n=Integer.parseInt(args[1]);
      
      int[] a = new int[n+1];  
       
      for(int i=0; i<=n; i++)
         a[i]=i;
        
     if(verbose==true) 
         count = printSolutions(a,n);  
     else
         count = count(a,n);       
 System.out.println(n+"-Queens has "+count+" solutions");
  
 }
   
  
   static void printUsage(){
      System.out.println("Usage: Queens [-v] number");
      System.out.println("Option:  -v   verbose output, print all solutions");  
   }
   static int[] nextPermutation(int[] a){
      int pivot=0;
      int successor;
      int temp=0;
      int n=a.length;
      boolean hasPivot=false;
      for(int i=n-2; i>0;i--){ 
         //find pivot spot
         if(a[i]<a[i+1]){
            pivot=i;   
            hasPivot=true;
            break;
         }          
      }      
         //swap pivot and succesor
          if(hasPivot==true){
            for(int k=n-1; k>0;k--)    
               if(a[k]>a[pivot]){
                  successor=k;
                  swap(a,pivot,k);                 
               //reverse array after pivot point        
                  reverse(a,pivot);                 
                  return a;                   
               }
            }         
      
      reverse(a,0);
      return a;      
   }
   
   
   
   static boolean isSolution(int[] a){
      int n= a.length-1;
      int x;
      int y;
      boolean isSolution = true;
   
   
      for(int i=1;i<=n;i++)
         for(int k=i+1;k<=n;k++){
            x= k-i;
            y= a[k]-a[i];
            y= Math.abs(y);
            if(y==x){
               isSolution=false;
               break;
            }}
      return isSolution;
   }      
         
   
   static int count(int[] a,int n){
      int count=0;
      int f=0;
      while(f<=fact(n)){
         nextPermutation(a);     
         if(isSolution(a)){
            count++;}
         f++;}
      return count;  
   }
   
   static int printSolutions(int[] a,int n){
      int count=0;
      int f=0;
      while(f<=fact(n)){
         nextPermutation(a);     
         if(isSolution(a)){
            System.out.print("(");
            for(int k=1; k<=n; k++){
               if(k<n)
               System.out.print(a[k]+", ");
               else
               System.out.print(a[k]);}
            count++;
            System.out.print(")");
            System.out.println();}
         f++;}
      return count;
   }
   
   
   static int fact(int n){
      int total=n;
      for(int i=n-1; i>0; i--)
         total*=i;
      return total;   
   }
   
     

   static void swap(int[] a, int i, int j){
      int temp = a[i];
      a[i] = a[j];
      a[j] = temp;
   }
   
   static void reverse(int[] a, int pivot){
      int i=pivot+1, j=a.length-1;
      while(i<j){
         swap(a, i, j);
         i++;
         j--;
      }
   }

   public static boolean isInteger(String s){
      try {
         Integer.parseInt(s);
      }catch(NumberFormatException e){
        return false;
      }catch(NullPointerException e){
        return false;
      }catch(NegativeArraySizeException e){
        return false;
      }
        return true;
 
}}
