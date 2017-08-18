//-----------------------------------------------------------------------------
// Thomas Brochard
// pa5
// FindPath.c
//----------------------------------------------------------------------------
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include "Graph.h"

#define MAX_LEN 250

int main(int argc, char * argv[]) {

  FILE *in, *out;
  char line[MAX_LEN];
  int size = 0;
  Graph G;
  Graph Gt;
  List S = newList();

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

  // Get first line of input file
  fgets(line, MAX_LEN, in);
  sscanf(line, "%d", &size);

  G = newGraph(size);

  int a;
  int b;

  // Create Adjacency List
  while (fgets(line, MAX_LEN, in) != NULL) {
    a = 0;
    b = 0;
    sscanf(line, "%d %d", &a, &b);
    if (a == 0 && b == 0) {
      break;
    }
    addArc(G, a, b);
  }

  // Initialize first List for initial DFS
  for (int i = 1; i <= (size); i++) {
    append(S, i);
  }

  DFS(G, S);
  Gt = transpose(G);
  DFS(Gt, S);

  // Print Adjacency List
  fprintf(out, "Adjacency list representation of G:\n");
  printGraph(out, G);
  fprintf(out, "\n");

  // Count Strongly Connected Components
  int scc = 0;
  for (int i = 1; i < (size + 1); i++) {
    if (getParent(Gt, i) == 0)
      scc++;
  }

  // Increment through and print formatted txt to to the output file
  fprintf(out, "G contains %d strongly connected components:\n", scc);
  int comp = 1;
  moveBack(S);
  List L = newList();
  while (index(S) != -1) {
    prepend(L, get(S));
    if (getParent(Gt, get(S)) == 0) {
      fprintf(out, "Component %d: ", comp);
      printList(out, L);
      fprintf(out, "\n");
      clear(L);
      comp++;
    }
    movePrev(S);
  }

  //close files / Free ADTs
  freeList(&S);
  freeList(&L);
  freeGraph(&G);
  freeGraph(&Gt);
  fclose(in);
  fclose(out);

  return (0);
}
