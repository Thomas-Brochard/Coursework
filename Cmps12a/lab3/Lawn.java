// Lawn.java
// Thomas Brochard
// Pa1
// calculating time to mow lawn given the dimensions


import java.util.Scanner;

public class Lawn{
   public static void main(String args[]){
   
      Scanner myScanner = new Scanner(System.in);
   
   
      double Llength= myScanner.nextDouble();
      double Lwidth= myScanner.nextDouble();
      
      double Hlength= myScanner.nextDouble();
      double Hwidth= myScanner.nextDouble();



      double totalArea= (Llength*Lwidth)-(Hlength*Hwidth);
      System.out.println("The lawn area is "+totalArea+" square feet."); 
    
      double rate = myScanner.nextDouble();
      double time= (totalArea/rate);
      
      int h,m,s;
      
      s = (int) Math.round(time);
      m= s/60;
      s= s%60;
      h= m/60;
      m= m%60;
     
      System.out.println("The mowing time is "+h+" hour"+(h!=1?"s ":" ")+m+" minute"+(m!=1?"s ":" ")+s+" second"+(s!=1?"s ":" "));       
   }}


