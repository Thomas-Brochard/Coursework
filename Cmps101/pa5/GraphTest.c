// Thomas Brochard
// pa4
// GraphTest.c

#include<stdio.h>
#include<stdlib.h>
#include "Graph.h"

int main(int argc, char* argv[]) {
  int n = 8;
  List Path = newList();
  Graph Z = newGraph(n);
  Graph Y;
  //This portion reconstructs HWK 6 #3
  printf("\n------------------------------------------\n");
  printf("HWK 6 #3 -- Testing all Graph ADT features\n");
  printf("------------------------------------------\n");

  // Build Graph
  addArc(Z, 1, 2);
  addArc(Z, 1, 5);
  addArc(Z, 2, 6);
  addArc(Z, 3, 4);
  addArc(Z, 3, 6);
  addArc(Z, 3, 7);
  addArc(Z, 4, 7);
  addArc(Z, 4, 8);
  addArc(Z, 6, 7);
  addArc(Z, 7, 8);

  printf("\norder: %d    //8\nsize: %d   //10\n", getOrder(Z), getSize(Z));
  for (int i = 1; i < 9; i++)
    append(Path, i);

  printf("\nAdjacency List representation of Graph: ");
  //Run DFS
  DFS(Z, Path);
  printf("\n");
  printGraph(stdout, Z);
  printf("\n");

  //Print out Graph attributes
  printf("Topological Sort: ");
  printList(stdout, Path);
  printf("      // 3 4 1 5 2 6 7 8 \nParents: ");
  for (int i = 1; i < (n + 1); i++)
    printf("%d ", getParent(Z, i));
  printf("             // 0 1 0 3 1 2 6 7 \nDiscover Times: ");
  for (int i = 1; i < (n + 1); i++)
    printf("%d ", getDiscover(Z, i));
  printf("  // 1 2 13 14 10 3 4 5 \nFinish Times: ");
  for (int i = 1; i < (n + 1); i++)
    printf("%d ", getFinish(Z, i));
  printf("  // 12 9 16 15 11 8 7 6 \n\n");

  Y = transpose(Z);
  printf("Copy and Transpose of above adjacency represesntation: \n");
  printGraph(stdout, Y);

///////////////////////////////////////////////////////////////////////////

  printf("\n\n-----------------------------\n");
  printf("--GraphClient--\n");
  printf("-----------------------------\n");
  int i;
  List S = newList();
  Graph G = newGraph(n);
  Graph T = NULL, C = NULL;
//
  for (i = 1; i <= n; i++)
    append(S, i);

  addArc(G, 1, 2);
  addArc(G, 1, 5);
  addArc(G, 2, 5);
  addArc(G, 2, 6);
  addArc(G, 3, 2);
  addArc(G, 3, 4);
  addArc(G, 3, 6);
  addArc(G, 3, 7);
  addArc(G, 3, 8);
  addArc(G, 6, 5);
  addArc(G, 6, 7);
  addArc(G, 8, 4);
  addArc(G, 8, 7);
  printGraph(stdout, G);

  DFS(G, S);
  fprintf(stdout, "\n");
  fprintf(stdout, "x:  d  f  p\n");
  for (i = 1; i <= n; i++) {
    fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(G, i), getFinish(G, i),
        getParent(G, i));
  }

  T = transpose(G);
  fprintf(stdout, "\n");
  printGraph(stdout, T);
  fprintf(stdout, "\n");

  DFS(T, S);
  fprintf(stdout, "x:  d  f  p\n");
  for (i = 1; i <= n; i++) {
    fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(T, i), getFinish(T, i),
        getParent(T, i));
  }
  fprintf(stdout, "\n");
  printList(stdout, S);
  fprintf(stdout, "\n");

  freeList(&Path);
  freeList(&S);
  freeGraph(&G);
  freeGraph(&T);
  freeGraph(&C);
  freeGraph(&Y);
  freeGraph(&Z);
  return (0);

}
