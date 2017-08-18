//-----------------------------------------------------------------------------
// Thomas Brochard
// pa2
// Lex.c
//----------------------------------------------------------------------------
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include "List.h"

#define MAX_LEN 250

int main(int argc, char * argv[]) {

  FILE *in, *out;
  char line[MAX_LEN];
  char* words[MAX_LEN];

  // check command line for correct number of arguments
  if (argc != 3) {
    printf("Usage: %s <input file> <output file>\n", argv[0]);
    exit(1);
  }

  // open files for reading and writing
  in = fopen(argv[1], "r");
  out = fopen(argv[2], "w");
  if (in == NULL) {
    printf("Unable to open file %s for reading\n", argv[1]);
    exit(1);
  }
  if (out == NULL) {
    printf("Unable to open file %s for writing\n", argv[2]);
    exit(1);
  }

  int count = 0;
  /* read each line of input file, then count and print tokens*/
  while (fgets(line, MAX_LEN, in) != NULL) {
    words[count] = (char*) malloc(255);
    strcpy(words[count], line);
    count = count + 1;
  }
  int len = count;

  List myList = newList();
  append(myList, 0);
  int i;
  for (i = 1; i < len; i++) {
    moveFront(myList);
    while (index(myList) != (-1) && strcmp(words[i], words[get(myList)]) > 0) {
      moveNext(myList);
    }
    if (index(myList) != (-1)) {
      insertBefore(myList, i);
    } else {
      append(myList, i);
    }
  }

  moveFront(myList);
  while (index(myList) != (-1)) {
    fprintf(out, "%s", words[get(myList)]);
    moveNext(myList);
  }

  freeList(&myList);

  i = 0;
  for (i = 0; i < len; i++) {
    free(words[i]);
  }

  //close files
  fclose(in);
  fclose(out);
  return (0);
}
