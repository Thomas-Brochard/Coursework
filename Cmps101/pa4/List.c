//-----------------------------------------------------------------------------
// Thomas Brochard
// pa4
// List.c
// Implementation file for List ADT
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include "List.h"

// structs --------------------------------------------------------------------

// private NodeObj type
typedef struct NodeObj {
  int data;
  struct NodeObj* next;
  struct NodeObj* prev;
} NodeObj;

// private Node type
typedef NodeObj* Node;

// private ListObj type
typedef struct ListObj {
  Node front;
  Node back;
  Node cursor;
  int index;
  int length;
} ListObj;

// Constructors-Destructors ---------------------------------------------------

// newNode()
// Returns reference to new Node object. Initializes next and data fields.
// Private.
Node newNode(int data) {
  Node N = malloc(sizeof(NodeObj));
  N->data = data;
  N->next = NULL;
  N->prev = NULL;
  return (N);
}

// freeNode()
// Frees heap memory pointed to by *pN, sets *pN to NULL.
// Private.
void freeNode(Node* pN) {
  if (pN != NULL && *pN != NULL) {
    free(*pN);
    *pN = NULL;
  }
}

// newList()
// Returns reference to new empty List object.
List newList(void) {
  List Q;
  Q = malloc(sizeof(ListObj));
  Q->front = Q->back = NULL;
  Q->length = 0;
  Q->index = -1;
  Q->cursor = NULL;
  return (Q);
}

// freeList()
// Frees all heap memory associated with List *pQ, and sets *pQ to NULL.S
void freeList(List* pQ) {
  if (pQ != NULL && *pQ != NULL) {
    while (!isEmpty(*pQ)) {
      deleteBack(*pQ);
    }
    free(*pQ);
    *pQ = NULL;
  }
}

// length()
// Pre: length != null
int length(List L) {
  if (L == NULL) {
    printf("List error: calling length() on NULL List reference\n");
    exit(1);
  }
  return (L->length);
}
// Access functions -----------------------------------------------------------

// index()
// Pre: L!=null
int index(List L) {
  if (isEmpty(L)) {
    printf("null list err\n");
    exit(1);
  }
  if (L->cursor == NULL) {
    return -1;
  }
  return (L->index);
}

// front()
// Returns the value at the front of Q.
// Pre: !isEmpty(Q)
int front(List Q) {
  if (Q == NULL) {
    printf("List Error: calling getFront() on NULL List reference\n");
    exit(1);
  }
  if (isEmpty(Q)) {
    printf("List Error: calling getFront() on an empty List\n");
    exit(1);
  }
  return (Q->front->data);
}

// back()
// Returns the value at the back of Q.
// Pre: !isEmpty(Q)
int back(List Q) {
  if (Q == NULL) {
    printf("List Error: calling getBack() on NULL List reference\n");
    exit(1);
  }
  if (isEmpty(Q)) {
    printf("List Error: calling getBack() on an empty List\n");
    exit(1);
  }
  return (Q->back->data);
}

// get();
// Pre: length()>0, index()>=0
int get(List L) {
  if (isEmpty(L)) {
    printf("empty list err\n");
  }
  if (L->index < 0) {
    printf("null cursor err\n");
  }
  return (L->cursor->data);
}

// isEmpty()
// Returns true (1) if Q is empty, otherwise returns false (0)
int isEmpty(List Q) {
  if (Q == NULL) {
    printf("List Error: calling isEmpty() on NULL List reference\n");
    exit(1);
  }
  return (Q->length == 0);
}

int equals(List A, List B) {
  if (length(A) != length(B)) {
    return 0;
  }
  moveFront(A);
  moveFront(B);
  while (A->index != -1) {
    if (get(A) != get(B))
      return 0;
    moveNext(A);
    moveNext(B);
  }
  return 1;
}

// Manipulation procedures ----------------------------------------------------

// clear()
// clears list to initial state
void clear(List L) {
  Node tmp = L->front;
  while (tmp != NULL) {
    Node curr = tmp;
    tmp = tmp->next;
    free(curr);
  }
  L->front = L->back = NULL;
  L->length = 0;
  L->index = -1;
  L->cursor = NULL;
}

// moveFront()
void moveFront(List L) {
  if (!isEmpty(L)) {
    L->cursor = L->front;
    L->index = 0;
  }
}

// moveBack()
void moveBack(List L) {
  if (!isEmpty(L)) {
    L->cursor = L->back;
    L->index = (L->length - 1);
  }
}

// movePrev()
void movePrev(List L) {
  if (L->cursor != NULL && L->index != 0) {
    L->cursor = L->cursor->prev;
    L->index = (L->index - 1);
  } else if (L->index == 0) {
    L->cursor = NULL;
    L->index = -1;
  }
}

// moveNext()
void moveNext(List L) {
  if (L->cursor != NULL && L->index != (L->length - 1)) {
    L->cursor = L->cursor->next;
    L->index = (L->index + 1);
  } else if (L->index == (L->length - 1)) {
    L->cursor = NULL;
    L->index = -1;
  }
}

// prepend()
// Places new data element at the end of Q
void prepend(List L, int data) {
  Node N = newNode(data);
  if (L == NULL) {
    printf("List Error: calling prepend() on NULL List reference\n");
    exit(1);
  }
  if (isEmpty(L)) {
    L->front = L->back = N;
  } else {
    N->next = L->front;
    L->front->prev = N;
    L->front = N;
  }
  if (L->index != -1) {
    L->index = L->index + 1;
  }
  L->length = (L->length + 1);
}

// append()
void append(List L, int data) {
  Node N = newNode(data);
  if (L == NULL) {
    printf("List Error: calling append() on NULL List reference\n");
    exit(1);
  }
  if (isEmpty(L)) {
    L->front = L->back = N;
  } else {
    N->prev = L->back;
    L->back->next = N;
    L->back = N;
  }
  N = NULL;
  L->length = (L->length + 1);
}

// insertBefore()
void insertBefore(List L, int data) {
  if (L->length == 0) {
    printf("List error: insertBefore() called on empty list");
    exit(1);
  }
  if (L->index == -1) {
    printf("List error: insertBefore() called on null cursor");
    exit(1);
  }
  Node N = newNode(data);
  if (L->index == 0) {
    L->front = N;
  } else {
    Node prev = L->cursor->prev;
    prev->next = N;
    N->prev = prev;
    prev = NULL;
  }
  L->cursor->prev = N;
  N->next = L->cursor;
  L->length = (L->length + 1);
  L->index = (L->index + 1);
}

// insertAfter()
void insertAfter(List L, int data) {
  if (L->length == 0) {
    printf("List error: insertBefore() called on empty list");
    exit(1);
  }
  if (L->index == -1) {
    printf("List error: insertBefore() called on null cursor");
    exit(1);
  }
  Node N = newNode(data);
  if (L->index == (L->length - 1)) {
    L->back = N;
  } else {
    Node next = L->cursor->next;
    next->prev = N;
    N->next = next;
    next = NULL;
  }
  L->cursor->next = N;
  N->prev = L->cursor;
  L->length = (L->length + 1);
}

// deleteFront()
void deleteFront(List L) {
  Node N = NULL;
  if (L == NULL) {
    printf("List Error: calling deleteFront() on NULL List reference\n");
    exit(1);
  }
  if (isEmpty(L)) {
    printf("List Error: calling deleteFront() on an empty List\n");
    exit(1);
  }
  N = L->front;
  if (L->length > 1) {
    L->front = L->front->next;
    L->index = (L->index - 1);
  } else {
    L->front = L->back = NULL;
    L->index = -1;
  }
  if (L->index == 0) {
    L->cursor = NULL;
  }
  L->length = (L->length - 1);
  freeNode(&N);
  N = NULL;
}

// deleteBack()
void deleteBack(List L) {
  Node N = NULL;
  if (L == NULL) {
    printf("List Error: calling deleteFront() on NULL List reference\n");
    exit(1);
  }
  if (isEmpty(L)) {
    printf("List Error: calling deleteFront() on an empty List\n");
    exit(1);
  }
  N = L->back;
  if (L->length > 1) {
    L->back = N->prev;
    L->back->next = NULL;
  } else {
    L->front = L->back = NULL;
    L->index = -1;
  }
  if (L->index == (L->length - 1)) {
    L->cursor = NULL;
    L->index = -1;
  }
  L->length = (L->length - 1);
  freeNode(&N);
  N = NULL;
}

// delete()
void delete(List L) {
  Node N = L->cursor;
  if (L->length == 0) {
    printf("List error: delete() called on empty List");
    exit(1);
  }
  if (L->index == -1) {
    printf("List error: delete() called with undefined cursor");
    exit(1);
  }
  if (L->cursor->prev != NULL) {
    L->cursor->prev->next = L->cursor->next;
  } else {
    L->front = L->cursor->next;
  }
  if (L->cursor->next != NULL) {
    L->cursor->next->prev = L->cursor->prev;
  } else {
    L->back = L->cursor->prev;
  }
  L->length = (L->length - 1);
  L->index = -1;
  L->cursor = NULL;
  freeNode(&N);
  N = NULL;
}

// Other Functions ------------------------------------------------------------

// printList()
// Prints data elements in Q on a single line to stdout.
void printList(FILE* out, List L) {
  Node N = NULL;
  if (L == NULL) {
    printf("List Error: calling printList() on NULL List reference\n");
    exit(1);
  }
  int count = 0;
  for (N = L->front; N != NULL; N = N->next) {
    count += 1;
    fprintf(out, "%d", N->data);
    //printf("%d %d\n", index(L), length(L)-1);
    if (length(L) != count)
      fprintf(out, " ");
  }
  N = NULL;
}

List copyList(List L) {
  List myList = newList();
  moveFront(L);
  while (index(L) != -1) {
    append(myList, get(L));
    moveNext(L);
  }
  return myList;
}
