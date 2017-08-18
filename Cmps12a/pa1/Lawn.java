// Lawn.java
// Thomas Brochard
// Pa1
// calculating time to mow lawn given the dimensions

import java.util.Scanner;

public class Lawn{
  public static void main(String args[]){
   
    Scanner myScanner = new Scanner(System.in);
    
    System.out.printf("Enter the length and width of the lot, in feet: ");
    double LotLength= myScanner.nextDouble();
    double LotWidth= myScanner.nextDouble();
      
    System.out.printf("Enter the length and width of the house, in feet: ");
    double HouseLength= myScanner.nextDouble();
    double HouseWidth= myScanner.nextDouble();



    double totalArea= (LotLength*LotWidth)-(HouseLength*HouseWidth);
    System.out.printf("The lawn area is %f square feet.\n", totalArea); 
    
    System.out.printf("Enter the mowing rate, in square feet per second: \n");
    double rate = myScanner.nextDouble();
    double time= (totalArea/rate);
      
    int h,m,s;
      
    s = (int) Math.round(time);
    m= s/60;
    s= s%60;
    h= m/60;
    m= m%60;
     
    System.out.printf("The mowing time is %d hour%s %d minute%s %d second%s", h, (h!=1?"s":""), m, (m!=1? "s":""), s, (s!=1?"s":""));       
   }
}




