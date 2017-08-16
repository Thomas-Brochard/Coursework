// GCD.c 
//Thomas Brochard
//lab8
//using C to find the GCD of two user-inputed integers
#include<stdio.h>
#include<math.h>
#include<stdlib.h>
int main(void){

   int a;
   int b;
   int n;
   int temp;
   int tempA;
   int tempB;
   int Q;
   int R;
  char str[100]; // string (i.e. char array) for discarding bad input

   // get three positive doubles from the user
   
       printf("Enter a positive double: ");
      n = scanf(" %d", &a); // try to read a double
     
      while( 1 ){                
         while( n!=1 ){ // if the read failed, because its not a double
            scanf("%s", str); // discard the non-double
            printf("Please enter a positive double: "); // ask again
            n = scanf("%d", &a); // try to read a double
         } 
         // at this point we have a double in the variable x         
         if( a>0 ) break; // get out of the loop if its positive
         else {printf("Please enter a positive double: "); // otherwise ask again
         n = scanf("%d", &a);}
      }   
   
   
     printf("Enter a positive double: ");
      n = scanf(" %d", &b); // try to read a double
     
      while( 1 ){                
         while( n!=1 ){ // if the read failed, because its not a double
            scanf("%s", str); // discard the non-double
            printf("Please enter a positive double: "); // ask again
            n = scanf("%d", &b); // try to read a double
         } 
         // at this point we have a double in the variable x         
         if( a>0 ) break; // get out of the loop if its positive
         else {printf("Please enter a positive double: "); // otherwise ask again
         n = scanf("%d", &b);}
      }   
   
   tempA = a;
   tempB = b;
   
    if(a<b)
      {temp=a;
         a=b;
         b=temp;      
      } 
      
      Q=a/b;
      R=a-b*Q;
     
      while(R!=0)
      {
        Q=b/R;
        a=b;
        b=R;
        R=a-b*Q;
        
         
         
      }
      printf("The GCD of %d and %d is %d",tempA,tempB,b);
   


   return 0;
}
   


