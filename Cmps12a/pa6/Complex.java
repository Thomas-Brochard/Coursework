//-----------------------------------------------------------------------------
//   Complex.java
//   Thomas Brochard
//   1479546
//   pa6
//   Demonstrates complex operations between two user inputed complex numbers
//-----------------------------------------------------------------------------
class Complex{

   //--------------------------------------------------------------------------
   // Private Data Fields 
   //--------------------------------------------------------------------------
   private double re; 
   private double im;
   
   //--------------------------------------------------------------------------
   // Public Constant Fields 
   //--------------------------------------------------------------------------
   public static final Complex ONE = Complex.valueOf(1,0);
   public static final Complex ZERO = Complex.valueOf(0,0);
   public static final Complex I = Complex.valueOf(0,1);

   //--------------------------------------------------------------------------
   // Constructors 
   //--------------------------------------------------------------------------
   Complex(double a, double b){
      this.re = a;
      this.im = b;
   }

   Complex(double a){
      this.re = a;
      this.im = 0;
   }

   Complex(String s){
      // Fill in this constructor.
      // It should accept expressions like "-2+3i", "2-3i", "3", "5i", etc..
      // Throw a NumberFormatException if s cannot be parsed as Complex.
      
      double[] part = new double[2];
      s = s.trim();
      String NUM = "(\\d+\\.\\d*|\\.?\\d+)";
      String SGN = "[+-]?";
      String OP =  "\\s*[+-]\\s*";
      String I =   "i";
      String OR =  "|";
      String REAL = SGN+NUM;
      String IMAG = SGN+NUM+"?"+I;
      String COMP = REAL+OR+
                    IMAG+OR+
                    REAL+OP+NUM+"?"+I;
      
      if( !s.matches(COMP) ){
         throw new NumberFormatException(
                   "Cannot parse input string \""+s+"\" as Complex");
      }
      s = s.replaceAll("\\s","");     
      if( s.matches(REAL) ){
         part[0] = Double.parseDouble(s);
         part[1] = 0;
      }else if( s.matches(SGN+I) ){
         part[0] = 0;
         part[1] = Double.parseDouble( s.replace( I, "1.0" ) );
      }else if( s.matches(IMAG) ){
         part[0] = 0;
         part[1] = Double.parseDouble( s.replace( I , "" ) );
      }else if( s.matches(REAL+OP+I) ){
         part[0] = Double.parseDouble( s.replaceAll( "("+REAL+")"+OP+".+" , "$1" ) );
         part[1] = Double.parseDouble( s.replaceAll( ".+("+OP+")"+I , "$1"+"1.0" ) );
      }else{   //  s.matches(REAL+OP+NUM+I) 
         part[0] = Double.parseDouble( s.replaceAll( "("+REAL+").+"  , "$1" ) );
         part[1] = Double.parseDouble( s.replaceAll( ".+("+OP+NUM+")"+I , "$1" ) );
      }
      re= part[0];
      im= part[1];
   }

   //---------------------------------------------------------------------------
   // Public methods 
   //---------------------------------------------------------------------------

   // Complex arithmetic -------------------------------------------------------

   // copy()
   // Return a new Complex equal to this Complex
   Complex copy(){
      
      Complex myComplexCopy = new Complex(Re(), Im());
   return myComplexCopy;
   }
   
   // add()
   // Return a new Complex representing the sum this plus z.
   Complex add(Complex z){  
    double newRe=this.re + z.re; 
    double newIm=this.im + z.im;
    Complex myComplexAdd = new Complex(newRe, newIm);
    return myComplexAdd;
   }
   
   // negate()
   // Return a new Complex representing the negative of this.
   Complex negate(){
   double newRe=this.re*-1;
   double newIm=this.im*-1;
   Complex myComplexNegate = new Complex(newRe, newIm);
   return myComplexNegate; 
    
     }

   // sub()
   // Return a new Complex representing the difference this minus z.
   Complex sub(Complex z){
    double newRe=this.re-z.re;
    double newIm=this.im-z.im;
   Complex MyComplexSub = new Complex(newRe, newIm);
      return MyComplexSub;}

   // mult()
   // Return a new Complex representing the product this times z.
   Complex mult(Complex z){
    double newRe=(this.re*z.re)-(this.im*z.im);
    double newIm=(this.re*z.im)+(this.im*z.re);
   Complex myComplexMult = new Complex(newRe, newIm);
      return myComplexMult;}

   // recip()
   // Return a new Complex representing the reciprocal of this.
   // Throw an ArithmeticException with appropriate message if 
   // this.equals(Complex.ZERO).
   Complex recip(){
   double newRe=(this.re/(Math.pow(this.re,2)+Math.pow(this.im,2)));
   double newIm=(-this.im/(Math.pow(this.re,2)+Math.pow(this.im,2)));
   Complex myComplexRecip = new Complex(newRe, newIm);
      return myComplexRecip;}

   // div()
   // Return a new Complex representing the quotient of this by z.
   // Throw an ArithmeticException with appropriate message if 
   // z.equals(Complex.ZERO).
   Complex div(Complex z){
   double newReNum=(z.re*this.re)+(z.im*this.im);
   double newReDen=((Math.pow(z.re,2)+Math.pow(z.im,2)));
   double newImNum=(z.re*this.im)-(z.im*this.re);
   double newImDen=((Math.pow(z.re,2)+Math.pow(z.im,2)));
   double newRe=(newReNum/newReDen);
   double newIm=(newImNum/newImDen);
   Complex myComplexDiv = new Complex(newRe, newIm);
      return myComplexDiv;}

   // conj()
   // Return a new Complex representing the conjugate of this Complex.
   Complex conj(){
      double newIm= this.im*-1;
   Complex myComplexConj = new Complex(this.re, newIm);
      return myComplexConj;}
   
   // Re()
   // Return the real part of this.
   double Re(){
      return re;
   }

   // Im()
   // Return the imaginary part of this.
   double Im(){
      return im;
   }

   // abs()
   // Return the modulus of this Complex, i.e. the distance between 
   // points (0, 0) and (re, im).
   double abs(){
   double c2  = Math.sqrt((Math.pow(this.re,2)+Math.pow(this.im,2)));
   return c2;
   }

   // arg()
   // Return the argument of this Complex, i.e. the angle this Complex
   // makes with positive real axis.
   double arg(){
      return Math.atan2(im, re);
   }

   // Other functions ---------------------------------------------------------
   
   // toString()
   // Return a String representation of this Complex.
   // The String returned will be readable by the constructor Complex(String s)
   public String toString(){
     String newRe = Double.toString(this.re);
     String newIm = Double.toString(this.im);
     String newComplexToString;
     //String newComplexToString = (newRe + (this.im>=0?"+":"")+ newIm + "i");
     if(this.re==0.0)
     newComplexToString = newIm + "i";
     else if(this.im==0.0)
     newComplexToString = newRe;
     else
     newComplexToString = newRe + (this.im>0?"+":"") + newIm + "i";
     return newComplexToString;
   }

   // equals()
    // Return true iff this and obj have the same real and imaginary parts.
   public boolean equals(Object obj){
  boolean eq = false;
  Complex newComplexEquals;
  if(obj instanceof Complex){
  newComplexEquals = (Complex) obj;
  eq = (this.re==newComplexEquals.re && this.im==newComplexEquals.im);
  }
   return eq;}

   // valueOf()
   // Return a new Complex with real part a and imaginary part b.
   static Complex valueOf(double a, double b){
     String newRe = Double.toString(a);
     String newIe = Double.toString(b);
     String s = (newRe + (b>=0?"+":"")+ newIe + "i");
     Complex newComplexValueOf1 = new Complex(s);
     return newComplexValueOf1;}

   // valueOf()
   // Return a new Complex with real part a and imaginary part 0.
   static Complex valueOf(double a){
    double s = a; 
    Complex newComplexValueOf2 = new Complex(s);
    return newComplexValueOf2;}
 
   // valueOf()
   // Return a new Complex constructed from s.
   static Complex valueOf(String s){
      
   Complex newComplexValueOf3 = new Complex(s);
      return newComplexValueOf3;}

}
