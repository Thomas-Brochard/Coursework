//Roots.java
//Thomas brochard
//pa4
//Finding the zeros of a user-inputted function
import java.util.Scanner;

class Roots{
   public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      double temp=0;
      double resolution=Math.pow(10,-2), tolerance=Math.pow(10,-7), threshhold=Math.pow(10,-3);
      System.out.print("Enter the degree: ");
      int degree = sc.nextInt();  
     
      System.out.printf("Enter %d coefficients: ", degree+1);
      double[] coeff = new double[degree+1];
      
      for(int i=degree;i>=0;i--)
         coeff[i]=sc.nextInt();        
         
      System.out.print("Enter the left and right endpoints: ");
      double leftEndpoint = sc.nextInt();
      double rightEndpoint = sc.nextInt();
      
      if(leftEndpoint > rightEndpoint){
         temp = leftEndpoint;
         leftEndpoint = rightEndpoint;
         rightEndpoint = temp;
      }
   
   
      double[] diff = diff(coeff); 
      
      double b = leftEndpoint + resolution;
      boolean hasRoot=false;
      while(leftEndpoint < rightEndpoint){
         if(poly(coeff,leftEndpoint)*poly(coeff,b)<0){
            System.out.printf("Root found at %.5f \n",findRoot(coeff,leftEndpoint,b,tolerance));
            hasRoot=true;}
         else if(poly(diff,leftEndpoint)*poly(diff,leftEndpoint)<0){
            System.out.printf("Root found at %.5f \n", findRoot(diff,leftEndpoint,b,tolerance));
            hasRoot=true;}
         
         leftEndpoint = b;
         b = b+resolution;
           
      }
      
      if(hasRoot == false)
         System.out.println("No roots were found in the specified range.");
   }
   
   static double poly(double[] coeff, double b)
   {
      double sum=0;
      int degree= coeff.length-1;
      for(int i=0; i<coeff.length;i++){
         sum+=coeff[i]*Math.pow(b,degree);
         degree--;
      }
   
      return sum;
   }
   
   
   
   static double[] diff(double[] coeff)
   {   
      int degree = coeff.length-1;
      double[] D = new double[coeff.length];
      for(int i=0;i<=coeff.length-1;i++){
         D[i]=coeff[i]*degree;
         degree--;
      }
      return D;
   }        
   
   static double findRoot(double[] C, double a, double b, double tolerance){
      
      double root= 0, residual;
      double yRoot;
      double ya;
      double yb;
      if(poly(C,a)/poly(C,b)<0)
         while((b-a)>tolerance){         
            root= (a+b)/2;
         
            yRoot=poly(C,root);
            ya = poly(C,a);
            yb = poly(C,b);
         
            if(ya/yRoot<0)
               b=root;
            else if(yb/yRoot<0)
               a=root;   
         }
      return root;
   }
}
