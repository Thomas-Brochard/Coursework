/*Sphere.c  
 *Thomas Brochard
 *lab7
 *utilizing C and user input to provide the surface area and volume of a sphere from a given radius   */

#include<stdio.h>
#include<math.h>
int main(void){
   
   const double pi = 3.141592654;
   double x, sA, vol;
   
   printf("Enter the radius of the sphere: ");
   scanf("%lf", &x);
   
   vol = (4.0/3.0)*pi*pow(x,3.0);

   sA = 4.0*pi*pow(x,2.0);

   printf("The volume is %lf and the surface area is %lf", vol, sA);
   
   return 0;
}


