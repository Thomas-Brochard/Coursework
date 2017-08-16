// $Id: stack.c,v 1.14 2016-03-01 00:47:14-08 - - $
// Thomas Brochard
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "stack.h"
#include "debug.h"

#define DEFAULT_CAPACITY 16

struct stack {
   size_t capacity;
   size_t size;
   stack_item *data;
};

stack *new_stack (void) {
   stack *this = malloc (sizeof (stack));
   assert (this != NULL);
   this->capacity = DEFAULT_CAPACITY;
   this->size = 0;
   this->data = calloc (this->capacity, sizeof (stack_item));
   assert (this->data != NULL);
   return this;
}

void free_stack (stack *this) {
   assert (empty_stack (this));
   free (this->data);
   free (this);
}

static bool full_stack (stack *this) {
   return this->size == this->capacity;
}

static void realloc_stack (stack *this) {
   size_t old_capacity = this->capacity;
   this->capacity *= 2;
   this->data = realloc (this->data, this->capacity);
   assert (this->data != NULL);
   memset (this->data + old_capacity, 0, old_capacity);
}


void push_stack (stack *this, stack_item item) {
   if (full_stack (this)) realloc_stack (this);
   //DEBUGS ('s', show_stack (this));
   //DEBUGF ('s', "item=%p\n", item);
   this->data[this->size]=item;
   this->size++;
}

stack_item pop_stack (stack *this) {
   if ( empty_stack (this)){
      fflush(NULL);
      fprintf(stderr, "%s: stack empty\n", program_name);
      fflush(NULL);
      return NULL;
   }
   //DEBUGS ('s', show_stack (this));
 
   stack_item ptr = this->data[this->size-1];
   this->size--;
   return ptr;
}

stack_item peek_stack (stack *this, size_t index) {
   //DEBUGS ('s', show_stack (this));
   //assert(index < size_stack(this)); 
   if(empty_stack(this)){
      fprintf(stderr, "%s: stack empty\n", program_name);
      return NULL;
   }
   if(index >= size_stack(this))
      return NULL;
   stack_item ptr = this->data[this->size - (index + 1)];
  
   return ptr;
}

bool empty_stack (stack *this) {
   return size_stack (this) == 0;
}

size_t size_stack (stack *this) {
   return this->size;
}

void show_stack (stack *this) {
   fprintf (stderr, "stack@%p->{%lu,%lu,%p}\n",
            this, this->capacity, this->size, this->data);
}

