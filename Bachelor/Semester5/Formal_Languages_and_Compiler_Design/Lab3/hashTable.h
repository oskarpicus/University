#pragma once
#include <stdlib.h>
#include <string.h>
#include "linkedList.h"
#define INITIAL_CAPACITY 13

typedef struct HashTable {
	int capacity;
	LinkedList** array;
} HashTable;

HashTable* createTable() {
	HashTable* h = (HashTable*)malloc(1 * sizeof(HashTable));
	h->capacity = INITIAL_CAPACITY;
	h->array = (LinkedList**)malloc(sizeof(LinkedList*) * INITIAL_CAPACITY);
	for (int i = 0; i < h->capacity; i++) {
		h->array[i] = NULL;
	}
	return h;
}

int d(HashTable* table, Atom element) {
        int hash = 7;
        int len = strlen(element.token);
        for(int i = 0; i < len; i++) {
                hash = hash * 31 + (element.token)[i];
        }
	int remainder = hash % table->capacity;
        return remainder < 0 ? remainder + table->capacity : remainder;
}

int addTable(HashTable* table, Atom element) {
	int bucket = d(table, element);
	LinkedList* current = table->array[bucket];
	element.tsCode = bucket;
	if (current == NULL) {
		current = (LinkedList*)malloc(sizeof(LinkedList) * 1);
		table->array[bucket] = current;
	}		

	addList(current, element);
	return bucket;
}

int findTable(HashTable* table, Atom element) {
	int bucket = d(table, element);
	LinkedList* current = table->array[bucket];
	return current != NULL && findList(current, element) ? bucket : -1;	
}

void destroyTable(HashTable* table) {
	for (int i = 0; i < table->capacity; i++) {
		if (table->array[i] != NULL)
			destroyList(table->array[i]);
	}
	free(table);
}

void printTable(HashTable* table) {
	for (int i = 0; i < table->capacity; i++) {
		if (table->array[i] != NULL) {
			printList(table->array[i]);
		}
	}
}
