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
     
      System.out.print("Enter "+(degree+1)+" coefficients: ");
      double[] C= new double[degree+1];
      
      for(int i=degree;i>=0;i--)
         C[i]=sc.nextInt();        
         
      System.out.print("Enter the left and right endpoints: ");
      double L = sc.nextInt();
      double R = sc.nextInt();
      
      if(L > R){
         temp=L;
         L=R;
         R=temp;}
   
   
      double[] D= diff(C); 
      
      
  /* //show polynomial
      System.out.print("Polynomial: ");
      for(int i=0; i<=C.length-1;i++)
         System.out.print(C[i]+" ");
   //show derivative
      System.out.print("Derivative: ");
      for(int i=0; i<=D.length-1;i++)
         System.out.print(D[i]+" ");
      System.out.println();   
   */
      double a=L;
      double b=L+resolution;
      boolean hasRoot=false;
      while(a<R){
         if(poly(C,a)*poly(C,b)<0){
            System.out.printf("Root found at %.5f \n",findRoot(C,a,b,tolerance));
            hasRoot=true;}
         else if(poly(D,a)*poly(D,a)<0){
            System.out.printf("Root found at %.5f \n", findRoot(D,a,b,tolerance));
            hasRoot=true;}
         
         a=b;
         b=b+resolution;
           
      }
      
      if(hasRoot==false)
         System.out.println("No roots were found in the specified range.");
   }
   
   static double poly(double[] C, double x)
   {
      double sum=0;
      int degree= C.length-1;
      for(int i=0; i<C.length;i++){
         sum+=C[i]*Math.pow(x,degree);
         degree--;
      }
   
      return sum;
   }
   
   
   
   static double[] diff(double[] C)
   {
   
   
      int degree = C.length-1;
      double[] D=new double[C.length];
      for(int i=0;i<=C.length-1;i++){
         D[i]=C[i]*degree;
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
