// $Id: hashset.c,v 1.9 2014-05-15 20:01:08-07 - - $

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "debug.h"
#include "hashset.h"
#include "strhash.h"

#define HASH_NEW_SIZE 15

typedef struct hashnode hashnode;
struct hashnode {
   char *word;
   hashnode *link;
};

struct hashset {
   size_t size;
   size_t load;
   hashnode **chains;
};

hashset *new_hashset (void) {
   hashset *this = malloc (sizeof (struct hashset));
   assert (this != NULL);
   this->size = HASH_NEW_SIZE;
   this->load = 0;
   size_t sizeof_chains = this->size * sizeof (hashnode *);
   this->chains = malloc (sizeof_chains);
   assert (this->chains != NULL);
   memset (this->chains, 0, sizeof_chains);
   DEBUGF ('h', "%p -> struct hashset {size = %zd, chains=%p}\n",
                this, this->size, this->chains);
   return this;
}

void free_hashset (hashset *this) {
   DEBUGF ('h', "free (%p)\n", this);
   
   for(size_t i = 0; i < this->size; i++){
      hashnode* tmp = this->chains[i];
      while(tmp != NULL) {
         hashnode *old = tmp;
         free(tmp->word);
         tmp = tmp->link;
         free(old);
      }
   }
   free(this->chains);
   free(this);
}

void doublearray(hashset *this){
   size_t old_size = this->size;
   this->size = (this->size *2)+1;
  
   size_t sizeof_chains = this->size * sizeof(hashnode *);  
   hashnode **chains = malloc(sizeof_chains);
   memset(chains, 0, sizeof_chains);
   for(size_t i = 0; i<old_size; i++){
      hashnode *node = this->chains[i];
      while(node != NULL) {
         int hash_index = strhash(node->word) % this->size;
         hashnode *tmp = malloc(sizeof(struct hashnode));
         tmp->word = strdup(node->word);
         tmp->link = (chains[hash_index]== NULL?
                     NULL : chains[hash_index]);
         chains[hash_index] = tmp;
         free(node->word);
         hashnode* old = node;
         node = node->link;
         free(old);
      }
   }
   free(this->chains);
   this->chains = chains;
}

bool has_hashset (hashset *this, const char *item) {
   //STUBPRINTF ("hashset=%p, item=%s\n", this, item);
   int hash_index = strhash(item) % this->size;
   for(hashnode* node = this->chains[hash_index];
       node != NULL; node = node->link){
      if(strcmp(node->word, item) == 0)
         return true;
   }
   return false;
}

void put_hashset (hashset *this, const char *item) {
   //STUBPRINTF ("hashset=%p, item=%s\n", this, item);
   if(this->load*2 > this->size)
      {doublearray(this);}
   if(has_hashset(this, item) == true) 
      return;
   else{
      int hash_index = strhash(item) % this->size;
      hashnode* head = this->chains[hash_index];
      hashnode* tmp = malloc(sizeof(struct hashnode));     
      tmp->word = strdup(item);
      tmp->link = (head == NULL? NULL : this->chains[hash_index] );
      this->chains[hash_index] = tmp;
      this->load++;
   }
}

void print_hash(hashset *this){
   size_t array[this->size];
   memset(array, 0, this->size * sizeof(size_t));
   printf("%5zd words in the hash set\n", this->load);
   printf("%5zd size of the hash array\n", this->size);
   for(size_t i = 0; i < this->size; i++){
      hashnode* node = this->chains[i];
      size_t len = 0;
      while(node != NULL){
         len++;
         node = node->link;
      }
      if(len > 0)
         array[len]++;
   }
   for(size_t index = 0; index < this->size; index++)
      if(array[index] != 0)
         printf("%5zd chains of size %5zd\n", array[index], index);
   
}

void dump_hash(hashset *this){
   for(size_t index = 0; index < this->size; index++){
      hashnode* node = this->chains[index];
      int cnt = 0;
      while(node != NULL){
         if(cnt == 0)
            printf("array[%10zd] = %20lu \"%s\"\n",
                  index, strhash(node->word), node->word);
         else
            printf("                   = %20lu \"%s\"\n",
                  strhash(node->word), node->word);
         node = node->link;
         cnt++;
      }
   }
}










