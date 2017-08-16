// GCD.java
// Thomas Brochard
// pa3
// Calculate the gcd of two inputed 

import java.util.Scanner;
class GCD{
   public static void main(String[] args){
      int A=-1;
      int B=-1;
     
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter a positive integer: ");
   
      while(true){
        while(!sc.hasNextInt()){
           System.out.print("Please enter a positive integer: ");
           sc.next();}
           A = sc.nextInt();
        if(A>0)break;
           System.out.print("Please enter a positive integer: ");
      } 
      
      System.out.print("Enter another a positive integer: ");
      while(true){
        while(!sc.hasNextInt()){
           System.out.print("Please enter a positive integer: ");
           sc.next();}
           B = sc.nextInt();
        if(B>0)break;
           System.out.print("Please enter a positive integer: ");
      } 
                 
     
   
   
      int tempR;
      int temp;
      int tempA=A;
      int tempB=B;
      int Q;
      int R;  
      if(A<B)
      {temp=A;
         A=B;
         B=temp;      
      } 
      
      Q=A/B;
      R=A-B*Q;
     
      while(R!=0)
      {
        Q=B/R;
        A=B;
        B=R;
        R=A-B*Q;
        
         
         
      }
      System.out.println("The GCD of "+tempA+" and "+tempB+" is "+B);
   
   
   
   
   
   }}
