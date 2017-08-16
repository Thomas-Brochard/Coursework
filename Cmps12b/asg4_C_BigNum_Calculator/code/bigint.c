// $Id: bigint.c,v 1.19 2016-03-01 00:47:14-08 - - $
// Thomas Brochard
#include <assert.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "bigint.h"
#include "debug.h"
bigint *compare(bigint *this, bigint *that);
#define MIN_CAPACITY 16

struct bigint {
   size_t capacity;
   size_t size;
   bool negative;
   char *digits;
};

void trim_zeros (bigint *this) {
   while (this->size > 1) {
      size_t digitpos = this->size - 1;
      if (this->digits[digitpos] != 0) break;
      --this->size;
   }
}

bigint *new_bigint (size_t capacity) {
   bigint *this = malloc (sizeof (bigint));
   assert (this != NULL);
   this->capacity = capacity;
   this->size = 0;
   this->negative = false;
   this->digits = calloc (this->capacity, sizeof (char));
   assert (this->digits != NULL);
   DEBUGS ('b', show_bigint (this));
   return this;
}


bigint *new_string_bigint (const char *string) {
   assert (string != NULL);
   size_t length = strlen (string);
   bigint *this = new_bigint (length > MIN_CAPACITY
                            ? length : MIN_CAPACITY);
   const char *strdigit = &string[length - 1];
   if (*string == '_') {
      this->negative = true;
      ++string;
   }
   char *thisdigit = this->digits;
   while (strdigit >= string) {
      assert (isdigit (*strdigit));
      *thisdigit++ = *strdigit-- - '0';
   }
   this->size = thisdigit - this->digits;
   trim_zeros (this);
   DEBUGS ('b', show_bigint (this));
   return this;
}

bigint *do_add (bigint *this, bigint *that) {
   //DEBUGS ('b', show_bigint (this));
   //DEBUGS ('b', show_bigint (that));
   //STUB (return NULL);
  /* printf("Do_add:\n");
   print_bigint(this);
   printf("+\n");
   print_bigint(that);
   printf("____\n");
 */size_t max_size = 0;
   if(this->size > that->size)
       max_size = this->capacity + 1;
   else
       max_size = that->capacity + 1;
  
   bigint *result = new_bigint(max_size); 
   result->size = result->capacity;
   int digit = 0;
   int carry = 0;
   for(size_t i=0; i < result->capacity; i++){
      digit = carry;   
      if(i < this->capacity) digit += this->digits[i];
      if(i < that->capacity) digit += that->digits[i];
      result->digits[i] = digit % 10;
      carry = digit / 10;
   }
 /*  printf("result before trim: ");
   print_bigint(result);
   printf("result after trim: ");
   print_bigint(result);
 */trim_zeros(result);
   return result;
}

bigint *do_sub (bigint *this, bigint *that) {
   //DEBUGS ('b', show_bigint (this));
   //DEBUGS ('b', show_bigint (that));
   //STUB (return NULL);
  /* printf("Do_sub:\n");
   print_bigint(this);
   printf("-\n");
   print_bigint(that);
   printf("____\n");
  */size_t max_size = this->capacity;
   
 
   bigint *result = new_bigint(max_size);
   int digit = 0;
   int borrow = 0;
   for(size_t i = 0; i < result->capacity; i++){
      digit = this->digits[i] - borrow + 10;
      if(i < that->capacity) digit = digit - that->digits[i];
      result->digits[i] = digit % 10;
      borrow = 1 - digit / 10;
   }
   result->size = result->capacity; 
 /*  printf("result before trim: ");
   print_bigint(result);  
*/   trim_zeros(result);
/*   printf("result after trim: ");
   print_bigint(result);
//   result->size = result->capacity;
 */  return result; 

}

bigint *do_mul(bigint *this,bigint *that){
   size_t max_size = this->capacity + that->capacity;
   
   bigint *result = new_bigint(max_size);
   result->size = result->capacity;
   
   
   for(size_t i = 0; i < this->capacity; i++){
     int carry = 0;
      for(size_t j = 0; j < that->capacity; j++ ){
         int digit = result->digits[i+j]; 
         digit += this->digits[i]*that->digits[j];
         digit += carry;
         result->digits[i+j] = digit % 10;  
         carry = digit/10;
      }
   result->digits[i + that->capacity] += carry;
   }
   trim_zeros(result);
   return result;
}

void free_bigint (bigint *this) {
   free (this->digits);
   free (this);
}

void print_bigint (bigint *this) {
   //DEBUGS ('b', show_bigint (this));
   if(this == NULL)
      return;
   int count = 0;
   if(this->negative)
      printf("-");
   for(char *i = &this->digits[this->size-1]; 
                           i>=this->digits; --i){
      printf("%d", *i);
      count++;
      if(count % 69 == 0) printf("\\\n");
   }
   printf("\n");
}

bigint *add_bigint (bigint *this, bigint *that) {
   //DEBUGS ('b', show_bigint (this));
   //DEBUGS ('b', show_bigint (that));
   //STUB (return NULL);
   bigint *left = compare(this, that);
   bigint *right = NULL;
   if(left == this)
      right=that; 
   else
      right=this;
 
   bigint *result = NULL;
   
   if(this->negative == that->negative){
      result = do_add(left,right) ;
      result->negative = left->negative;
   }
   else{
      result = do_sub(left,right);  
      result->negative = left->negative;
   }
    return result;
}

bigint *sub_bigint (bigint *this, bigint *that) {
   //DEBUGS ('b', show_bigint (this));
   //DEBUGS ('b', show_bigint (that));
   //STUB (return NULL);
   bigint *left = compare(this, that);
   bigint *right = NULL;
   if(left == this)
      right=that;
   else
      right=this;

   bigint *result = NULL;

   if(this->negative != that->negative){
      result = do_add(this, that);
      result->negative = this->negative;   
   }
   else{
      result = do_sub(left, right);
      if(that == left)
         result->negative = !left->negative;
      else
         result->negative = right->negative;
   }
    
   return result;
}


bigint *mul_bigint (bigint *this, bigint *that) {
  //DEBUGS ('b', show_bigint (this));
  //DEBUGS ('b', show_bigint (that));
  //STUB (return NULL);
  bigint *left = compare(this, that);
  bigint *right = NULL;
  if(left == this)    
     right = that;
  else
     right = this;
  
  bigint *result = do_mul(left,right);
  if(left->negative == right->negative)
     result->negative = false;
  else
     result->negative = true;
  
  return result;
}

bigint *compare(bigint *this, bigint *that){

   if(this->size > that->size)
      return this;
   
   else if(this->size < that->size)
      return that;
   
   else{
      for(int i = this->size; i >= 0; i--){
         if(this->digits[i] > that->digits[i])
            return this;
         else if(this->digits[i] < that->digits[i])
            return that;
      }   
  }
  return this;
}

void show_bigint (bigint *this) {
   fprintf (stderr, "bigint@%p->{%lu,%lu,%c,%p->", this,
            this->capacity, this->size, this->negative ? '-' : '+',
            this->digits);
   
   for (char *byte = &this->digits[this->size - 1];
         byte >= this->digits; --byte) {
      fprintf (stderr, "%d", *byte);
      }
   fprintf (stderr, "}\n");
}

