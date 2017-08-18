//-----------------------------------------------------------------------------
// Thomas Brochard
// Graph.c
// pa4
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
  int* distance;
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
  G->distance = calloc(n + 1, sizeof(int));
  for (int i = 1; i < n + 1; i++) {
    G->adj[i] = newList();
    G->color[i] = 1;
    G->parent[i] = NIL;
    G->distance[i] = INF;
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
    free(G->distance);
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

int getSource(Graph G) {
  return G->source;
}

int getParent(Graph G, int u) {
  if (u < 1 || u > getOrder(G)) {
    printf("Graph error: getParent called with an out of bounds index");
    exit(1);
  }
  return G->parent[u];
}

int getDist(Graph G, int u) {
  if (u < 1 || u > getOrder(G)) {
    printf("Graph error: getDist called with an out of bounds index");
    exit(1);
  }
  return G->distance[u];
}

void getPath(List L, Graph G, int u) {
  if (G->source == NIL) {
    printf("Graph Error: calling getPath without Breadth First Search\n");
    exit(1);
  }
  if (u < 1 || u > (G->order)) {
    printf("Graph Error: calling getPath on an out of bound vertex\n");
    exit(1);
  }

  if (u == G->source)
    append(L, G->source);
  else if (G->parent[u] == NIL)
    prepend(L, NIL); //printf("no path\n");
  else {
    getPath(L, G, getParent(G, u));
    append(L, u);
  }
}
// Manipulation procedures ----------------------------------------------------
void makeNull(Graph G) {
  for (int i = 1; i < (G->order + 1); ++i) {
    clear(G->adj[i]);
  }
  G->size = 0;
}

void addEdge(Graph G, int u, int v) {
  if (u > getOrder(G) + 1 || v > getOrder(G) + 1 || u * v <= 0) {
    printf("Graph error: addEdge() called with out of bounds error\n");
    exit(1);
  }
  if (u == v) {
    printf("Graph error: addEdge() called between similar vertices\n");
    exit(1);
  }
  addArc(G, v, u);
  addArc(G, u, v);
}

void addArc(Graph G, int a, int b) {
  if (a > getOrder(G) + 1 || b > getOrder(G) + 1 || a * b <= 0) {
    printf("Graph error: addArc() called with out of bounds error\n");
    exit(1);
  }
  if (a == b) {
    printf("Graph error: addArc() called between similar vertices\n");
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

void BFS(Graph G, int s) {
  int x; // current dequeued value
  int y;
  List Q;
  List currList;
  G->source = s;
  for (int i = 0; i < (G->order); i++) {
    G->color[i] = 1; //white
    G->parent[i] = NIL;
    G->distance[i] = INF;
  }
  G->color[s] = 0; //grey
  G->distance[s] = 0;
  G->parent[s] = NIL; //???????
  Q = newList();
  append(Q, s); // = enqueue

  while (!isEmpty(Q)) {

    x = front(Q);

    deleteFront(Q);
    currList = G->adj[x];

    if (!isEmpty(currList)) {
      moveFront(currList);
      while (index(currList) != -1) {
        y = get(currList);
        if (G->color[y] == 1) {
          G->color[y] = 0;
          G->distance[y] = G->distance[x] + 1;
          G->parent[y] = x;
          append(Q, y);
        }
        moveNext(currList);
      }
      G->color[x] = -1;
    }
  }
  currList = NULL;
  freeList(&Q);
}

/*** Other operations ***/
void printGraph(FILE* out, Graph G) {
  for (int i = 1; i < (getOrder(G)); i++) {
    fprintf(out, "%d: ", i);
    if (isEmpty(G->adj[i])) {
      fprintf(out, "\n");
      continue;
    }
    printList(out, G->adj[i]);
    fprintf(out, "\n");
  }
  fprintf(out, "\n");
}

