#pragma once
#include <string.h>
#include <stdlib.h>
#include "model.h"

typedef struct Node {
	Atom element;
	struct Node* next;
} Node;

typedef struct LinkedList {
	struct Node* first;
} LinkedList;

void addList(LinkedList* list, Atom atom) {
	Node* node = (Node*)malloc(1 * sizeof(Node));
        node->element = atom;
 	node->next = NULL;

        if (list->first == NULL) {
                list->first = node;
                return;
        }

        Node* lastElement = list->first;
        while (lastElement->next != NULL) {
                lastElement = lastElement->next;
        }

        lastElement->next = node;
}

/* Returns 1 if found, 0, otherwise */
int findList(LinkedList* list, Atom element) {
	Node* current = list->first;
	while (current != NULL && strcmp((current->element).token, element.token)) {
		current = current->next;
	}

	return current != NULL;
}

void destroyList(LinkedList* list) {
	Node* first = list->first;
	while (first != NULL) {
		Node* p = first;
		first = first->next;
		free(p);		
	}
	free(list);
}

void printList(LinkedList* list) {
	Node* first = list->first;
	while (first != NULL) {
		Atom atom = first->element;
		printf("%s %d %d\n", atom.token, atom.code, atom.tsCode);
		first = first->next;
	}
}
