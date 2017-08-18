//-----------------------------------------------------------------------------
// Thomas Brochard
// pa2
// List.h
//-----------------------------------------------------------------------------

#ifndef _LIST_H_INCLUDE_
#define _LIST_H_INCLUDE_

// Exported type --------------------------------------------------------------
typedef struct ListObj* List;
typedef struct NodeObj* Node;

// Constructors-Destructors ---------------------------------------------------
List newList(void);

void freeList(List* pQ);

void freeNode(Node* pN);

List newList(void);

// Access functions -----------------------------------------------------------
int getFront(List Q);

int length(List L);

int index(List L);

int isEmpty(List Q);

int front(List Q);

int back(List Q);

int get(List L);

// Manipulation procedures ----------------------------------------------------
void clear(List L);

void moveFront(List L);

void moveBack(List L);

void movePrev(List L);

void moveNext(List L);

void prepend(List L, int data);

void append(List L, int data);

void insertBefore(List L, int data);

void insertAfter(List L, int data);

void deleteFront(List L);

void deleteBack(List L);

void delete(List L);

// Other Functions ------------------------------------------------------------
void printList(FILE* out, List L);

int equals(List A, List B);

List copyList(List L);
#endif
