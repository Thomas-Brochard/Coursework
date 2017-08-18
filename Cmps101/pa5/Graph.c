//-----------------------------------------------------------------------------
// Thomas Brochard
// pa5
// Graph.c
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include "Graph.h"
#include "List.h"
// structs --------------------------------------------------------------------

// private GraphObj type
struct GraphObj {
  int source;
  int order;
  int size;
  List* adj;
  int* color;
  int* parent;
  int* discover;
  int* finish;
};

// Constructors-Destructors ---------------------------------------------------

// newGraph()
// Returns reference to new empty Queue object.
Graph newGraph(int n) {
  Graph G;
  G = malloc(sizeof(struct GraphObj));
  G->source = NIL;
  G->order = n;
  G->size = 0;
  G->adj = calloc(n + 1, sizeof(List));
  G->color = calloc(n + 1, sizeof(int));
  G->parent = calloc(n + 1, sizeof(int));
  G->discover = calloc(n + 1, sizeof(int));
  G->finish = calloc(n + 1, sizeof(int));
  for (int i = 1; i < (n + 1); i++) {
    G->adj[i] = newList();
    G->color[i] = 1;
    G->parent[i] = NIL;
    G->discover[i] = UNDEF;
    G->finish[i] = UNDEF;
  }
  return G;
}

// freeGraph()
// Frees all heap memory associated with Graph *pG, and sets *pG to NULL.S
void freeGraph(Graph* pG) {
  Graph G = *pG;
  if (pG != NULL && *pG != NULL) {
    for (int i = 0; i < (G->order + 1); i++) {
      freeList(&(G->adj[i]));
    }
    free(G->adj);
    free(G->color);
    free(G->parent);
    free(G->discover);
    free(G->finish);
    free(*pG);
    *pG = NULL;
  }
}

// Access functions -----------------------------------------------------------

int getOrder(Graph G) {
  return G->order;
}

int getSize(Graph G) {
  return G->size;
}

int getParent(Graph G, int u) {
  if (u < 1 || u > getOrder(G)) {
    printf("Graph error: getParent called with an out of bounds index");
    exit(1);
  }
  if (G->parent[u] != NIL)
    return G->parent[u];
  else
    return 0;
}

int getDiscover(Graph G, int u) {
  if (u < 1 || u > getOrder(G)) {
    printf("Graph error: getDist called with an out of bounds index");
    exit(1);
  }
  return G->discover[u];
}
int getFinish(Graph G, int u) {
  if (u < 1 || u > getOrder(G)) {
    printf("Graph error: getDist called with an out of bounds index");
    exit(1);
  }
  return G->finish[u];
}

// Manipulation procedures ----------------------------------------------------

//Cretes an arc between the two provided vertices in the provided graph
void addArc(Graph G, int a, int b) {
  if (a > getOrder(G) + 1 || b > getOrder(G) + 1 || a * b <= 0) {
    printf("Graph error: addArc() called with out of bounds error\n");
    exit(1);
  }

  //printf("\nvertices: %d into List %d\n", b, a);
  List aList = G->adj[a];
  //printf("__AList still works__\n"); 
  if (length(aList) == 0) {
    //printf("%d prep to empty list\n", b);
    prepend(aList, b);
    G->size += 1;
  } else {
    //printf("__else entered__\n");
    moveFront(aList);
    while (index(aList) != -1 && get(aList) <= b) {
      if (get(aList) == b)
        return;
      moveNext(aList);
    }
    //printf("__while completed__\n");

    if (index(aList) == -1) {
      //printf("%d app\n", b);
      append(aList, b);
    } else {
      //printf("%d ibef\n", b);
      insertBefore(aList, b);
    }
    G->size += 1;
  }
}

// Helper function for DFS
void visit(Graph G, List S, int k, int *time) {
  //printf("start of visit\n");
  ++*time;
  G->discover[k] = *time;
  G->color[k] = 0;
  //printf("Starting: %d\n", k);
  if (!isEmpty(S)) {
    moveFront(G->adj[k]);
    //printf("(11)\n");
    while (index(G->adj[k]) != -1) {
      //printf("(12)\n");
      int u = get(G->adj[k]);
      if (G->color[u] == 1) {
        //printf("(13)\n");
        G->parent[u] = k;
        visit(G, S, u, time);
        //printf("(14)\n"); 
      }
      moveNext(G->adj[k]);
    }
  }
  G->color[k] = -1;
  ++*time;
  //printf("Finishin: %d\n", k);
  G->finish[k] = *time;
  prepend(S, k);
  //printf("End of visit\n");
}

//    Runs a Depth first search on the provided Graph, using the 
// Provided List S as starting vertices
void DFS(Graph G, List S) {
  if (length(S) != getOrder(G)) {
    printf("Graph Error: called DFS() without matching sizes\n");
    exit(1);
  }
  //printList(stdout, S);
  //printf("\n");
  //printf("(1)\n"); 
  for (int i = 0; i <= (G->order); i++) {
    G->color[i] = 1; //white
    G->parent[i] = NIL;
    G->discover[i] = UNDEF;
    G->finish[i] = UNDEF;
  }
  //printf("(2)\n");
  int time = 0;
  if (!isEmpty(S)) {
    moveFront(S);
    while (index(S) != -1) {
      if (G->color[get(S)] == 1) {
        visit(G, S, get(S), &time);
      }
      moveNext(S);
    }

    //printf("(3)\n");
    for (int i = length(S) / 2; i > 0; --i)
      deleteBack(S);
    //printf("(4)");
  }
}

/*** Other operations ***/
void printGraph(FILE* out, Graph G) {
  for (int i = 1; i <= (getOrder(G)); i++) {
    fprintf(out, "%d: ", i);
    if (isEmpty(G->adj[i])) {
      fprintf(out, "\n");
      continue;
    }
    printList(out, G->adj[i]);
    fprintf(out, "\n");
  }
}

Graph copyGraph(Graph G) {
  Graph newG = newGraph(G->order);
  for (int i = 1; i < (getOrder(G) + 1); i++) {
    //  newG->adj[i] = copyList(G->adj[i]);
    newG->color[i] = G->color[i];
    newG->parent[i] = G->parent[i];
    newG->discover[i] = G->discover[i];
    newG->finish[i] = G->finish[i];
  }
  return newG;
}

//    Returns a reference to a new Graph which is the transpose of 
// the provided Graph
Graph transpose(Graph G) {
  Graph newG = newGraph(G->order);
  List currList;
  for (int i = 1; i < (getOrder(G) + 1); i++) {
    currList = G->adj[i];
    moveFront(currList);
    while (index(currList) != -1) {
      addArc(newG, get(currList), i);
      moveNext(currList);
    }
  }
  currList = NULL;
  return newG;
}
