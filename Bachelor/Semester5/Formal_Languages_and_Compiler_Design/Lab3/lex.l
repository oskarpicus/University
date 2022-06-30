%{
#include <stdio.h>
#include "hashTable.h"
#include "codes.h"
#include <string.h>

LinkedList* fip;
HashTable* ts;

%}

%option noyywrap
CONST	([1-9][0-9]*)|(\".+\")|0|([+-]?([0-9]*\.)?[0-9]+)
ID	[a-zA-Z_][a-zA-Z_0-9]{0,249}

%%
"+"|"-"|"*"|"/"|"%"	{
	printf("ARITHMETIC_OPERATOR %s\n", yytext);
	Atom atom;
	strcpy(atom.token, yytext);
	atom.tsCode = DEFAULT_FIP_CODE;

	if (strcmp(atom.token, "+") == 0) 
		atom.code = PLUS_CODE;
	else if (strcmp(atom.token, "-") == 0)
		atom.code = MINUS_CODE;
	else if (strcmp(atom.token, "*") == 0)
		atom.code = ASTERIX_CODE;
	else if (strcmp(atom.token, "/") == 0)
		atom.code = DIVIDE_CODE;
	else if (strcmp(atom.token, "%") == 0)
		atom.code = MODULO_CODE;

	addList(fip, atom);
	}

"<"|">"|">="|"<="|"=="|"!="	{
	printf("RELATIONAL_OPERATOR %s\n", yytext);
	Atom atom;
	strcpy(atom.token, yytext);
	atom.tsCode = DEFAULT_FIP_CODE;

	if (strcmp(atom.token, "<") == 0)
                atom.code = LESS_THAN_CODE;
        else if (strcmp(atom.token, ">") == 0)
                atom.code = GREATER_THAN_CODE;
        else if (strcmp(atom.token, ">=") == 0)
                atom.code = GE_CODE;
        else if (strcmp(atom.token, "<=") == 0)
                atom.code = LE_CODE;
        else if (strcmp(atom.token, "==") == 0)
                atom.code = EQUAL_CODE;
	else if (strcmp(atom.token, "!=") == 0)
		atom.code = NOT_EQUAL_CODE; 

	addList(fip, atom);
	}

int|float|struct|if|while|return|#include<iostream>	{
	printf("KEY_WORD %s\n", yytext);
	Atom atom;
	strcpy(atom.token, yytext);
	atom.tsCode = DEFAULT_FIP_CODE;

	if (strcmp(atom.token, "int") == 0)
                atom.code = INT_CODE;
        else if (strcmp(atom.token, "float") == 0)
                atom.code = FLOAT_CODE;
        else if (strcmp(atom.token, "struct") == 0)
                atom.code = STRUCT_CODE;
        else if (strcmp(atom.token, "if") == 0)
                atom.code = IF_CODE;
        else if (strcmp(atom.token, "while") == 0)
                atom.code = WHILE_CODE;
        else if (strcmp(atom.token, "return") == 0)
                atom.code = RETURN_CODE;
	else if (strcmp(atom.token, "#include<iostream>") == 0)
		atom.code = IOSTREAM_CODE;

	addList(fip, atom);
	}

"="	{
	printf("ASSIGNMENT_OPERATOR %s\n", yytext);
	Atom atom;
	strcpy(atom.token, yytext);
	atom.tsCode = DEFAULT_FIP_CODE;
	atom.code = ASSIGNMENT_CODE;
	addList(fip, atom);
	}

"::"|">>"|"<<"	{
	printf("OTHER_OPERATOR %s\n", yytext);
	Atom atom;
	strcpy(atom.token, yytext);
	atom.tsCode = DEFAULT_FIP_CODE;

	if (strcmp(atom.token, "::") == 0)
                atom.code = RESOLUTION_CODE;
        else if (strcmp(atom.token, ">>") == 0)
                atom.code = RED_RIGHT_CODE;
        else if (strcmp(atom.token, "<<") == 0)
                atom.code = RED_LEFT_CODE;

	addList(fip, atom);
	}

"}"|"{"|";"|","|" "|"("|")"	{
	printf("SEPARATOR %s\n", yytext);
	Atom atom;
	strcpy(atom.token, yytext);
	atom.tsCode = DEFAULT_FIP_CODE;

	if (strcmp(atom.token, "}") == 0)
                atom.code = LEFT_BRACE_CODE;
        else if (strcmp(atom.token, "{") == 0)
                atom.code = RIGHT_BRACE_CODE;
        else if (strcmp(atom.token, ";") == 0)
                atom.code = SEMI_COLON_CODE;
	else if (strcmp(atom.token, ",") == 0)
                atom.code = COLON_CODE;
        else if (strcmp(atom.token, " ") == 0)
                atom.code = SPACE_CODE;
        else if (strcmp(atom.token, "(") == 0)
                atom.code = LEFT_BRACKET_CODE;
	else if (strcmp(atom.token, ")") == 0)
		atom.code = RIGHT_BRACKET_CODE;

	addList(fip, atom);	
	}

[ \t\n]+	/* remove whitespaces */

{CONST} {
	printf("CONSTANTA %s\n", yytext);
	Atom atom;
	strcpy(atom.token, yytext);
	atom.code = CONSTANT_CODE;
	int index = findTable(ts, atom);
	printf("index %d\n", index);
	if (index == -1) {
		index = addTable(ts, atom);
	}
	printf("index %d\n", index);
	atom.tsCode = index;
	addList(fip, atom);
	}

{ID}	{
	printf("IDENTIFIER %s\n", yytext);
	Atom atom;
	strcpy(atom.token, yytext);
	atom.code = IDENTIFIER_CODE;
	int index = findTable(ts, atom);
	if (index == -1) {
		index = addTable(ts, atom);
	}
	atom.tsCode = index;
	addList(fip, atom);
	}

.	{
	printf("UNIDENTIFIED %s\n", yytext);
	return 0;
}
%%

int main(int argc, char** argv){
	fip = (LinkedList*)malloc(1 * sizeof(LinkedList));
	ts = createTable();

	FILE* f = fopen(argv[1], "r");
	yyin = f;	

	yylex();

	printf("\n\nFIP\nToken code tsCode\n");
	printList(fip);

	printf("\n\nTS\n");
	printTable(ts);

	destroyList(fip);
	destroyTable(ts);
	return 0;
}
