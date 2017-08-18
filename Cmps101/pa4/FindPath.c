//-----------------------------------------------------------------------------
// Thomas Brochard
// pa4
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

  fgets(line, MAX_LEN, in);
  sscanf(line, "%d", &size);

  G = newGraph(size + 1);

  int a;
  int b;

  while (fgets(line, MAX_LEN, in) != NULL) {
    a = 0;
    b = 0;
    sscanf(line, "%d %d", &a, &b);
    if (a == 0 && b == 0) {
      break;
    }
    addEdge(G, a, b);
  }

  printGraph(out, G);
  int newLine = 0;
  while (fgets(line, MAX_LEN, in) != NULL) {

    sscanf(line, "%d %d", &a, &b);
    if (a == 0 && b == 0)
      break;

    if (newLine++ != 0)
      fprintf(out, "\n");
    BFS(G, a);
    int dist = getDist(G, b);
    fprintf(out, "The distance from %d to %d is ", a, b);
    if (dist == INF)
      fprintf(out, "infinity\n");
    else
      fprintf(out, "%d\n", dist);

    List Path = newList();
    getPath(Path, G, b);

    if (front(Path) == NIL)
      fprintf(out, "No %d-%d path exists\n", a, b);
    else {
      fprintf(out, "A shortest %d-%d path is: ", a, b);
      printList(out, Path);
      fprintf(out, "\n");
    }

    freeList(&Path);
    Path = NULL;
    newLine = newLine + 1;
  }

  //close files
  freeGraph(&G);
  fclose(in);
  fclose(out);
  return (0);
}
