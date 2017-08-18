// Thomas Brochard
// pa4
// GraphTest.c
#include<stdlib.h>
#include<stdio.h>
#include "Graph.h"

int main(int argc, char* argv[]) {
  Graph G = newGraph(8);
  printGraph(stdout, G);

  //This portion reconstructs HWK 6 #3
  addEdge(G, 1, 2);
  addEdge(G, 1, 5);
  addEdge(G, 2, 6);
  addEdge(G, 6, 3);
  addEdge(G, 6, 7);
  addEdge(G, 3, 7);
  addEdge(G, 3, 4);
  addEdge(G, 4, 7);
  addEdge(G, 4, 8);
  addEdge(G, 7, 8);

  printGraph(stdout, G);
  freeGraph(&G);
  return (0);
}
