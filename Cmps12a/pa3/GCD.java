// GCD.java
// pa3
// Calculate the gcd of two inputed 

import java.util.Scanner;

class GCD{
   public static void main(String[] args){
      int first = -1;
      int second = -1;
     
      Scanner sc = new Scanner(System.in);
   
      do{
           System.out.print("Please enter a positive integer: ");
           first = sc.nextInt();
      }while(first < 1);
      
      do{
           System.out.print("Please enter another a positive integer: ");
           second = sc.nextInt();
      }  while(second < 1);
                  
      int temp;
      int originalFirst = first;
      int originalSecond = second;
      int ratio;
      int R;  
      if(first < second)
      {
	 temp = first;
         first = second;
         second = temp;      
      } 
      
      ratio = first/second;
      R = first-second*ratio;
     
      while(R!=0)
      {
        ratio = second/R;
        first = second;
        second = R;
        R = first-second*ratio;
        
         
         
      }
      System.out.printf("The GCD of %d  and %d is %d", originalFirst, originalSecond, second); 
   
   }
}
