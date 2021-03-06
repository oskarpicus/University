%{
#include <stdio.h>
#include <string.h>
#include "bison.tab.h"

int yyerror(char* s);
%}

%option noyywrap
CONST	([1-9][0-9]*)|(\".+\")|0|([+-]?([0-9]*\.)?[0-9]+)
ID	[a-zA-Z_][a-zA-Z_0-9]*

%%
"+"|"-"|"*"|"/"|"%"	{
	printf("ARITHMETIC_OPERATOR %s\n", yytext);
	strcpy(yylval.token, yytext);
	if (strcmp(yytext, "+") == 0) 
		return PLUS_CODE;
	else if (strcmp(yytext, "-") == 0)
		return MINUS_CODE;
	else if (strcmp(yytext, "*") == 0)
		return ASTERIX_CODE;
	else if (strcmp(yytext, "/") == 0)
		return DIVIDE_CODE;
	else if (strcmp(yytext, "%") == 0)
		return MODULO_CODE;
	}

int|#include<iostream>	{
	printf("KEY_WORD %s\n", yytext);
	strcpy(yylval.token, yytext);
	if (strcmp(yytext, "int") == 0)
                return INT_CODE;
	else if (strcmp(yytext, "#include<iostream>") == 0)
		return IOSTREAM_CODE;
	}

"="	{
	printf("ASSIGNMENT OPERATOR %s\n", yytext);
	strcpy(yylval.token, yytext);
	return ASSIGNMENT_CODE;
	}

"::"|">>"|"<<"	{
	printf("OTHER_OPERATOR %s\n", yytext);
	strcpy(yylval.token, yytext);
	if (strcmp(yytext, "::") == 0)
                return RESOLUTION_CODE;
        else if (strcmp(yytext, ">>") == 0)
                return RED_RIGHT_CODE;
        else if (strcmp(yytext, "<<") == 0)
                return RED_LEFT_CODE;
	}

"}"|"{"|";"|"("|")"	{
	printf("SEPARATOR %s\n", yytext);
	strcpy(yylval.token, yytext);
	if (strcmp(yytext, "}") == 0)
	       return LEFT_BRACE_CODE;
        else if (strcmp(yytext, "{") == 0)
                return RIGHT_BRACE_CODE;
        else if (strcmp(yytext, ";") == 0)
                return SEMI_COLON_CODE;
        else if (strcmp(yytext, " ") == 0)
                return SPACE_CODE;
        else if (strcmp(yytext, "(") == 0)
                return LEFT_BRACKET_CODE;
	else if (strcmp(yytext, ")") == 0)
		return RIGHT_BRACKET_CODE;
	}

[ \t\n]+	/* remove whitespaces */

{CONST} {
	printf("CONSTANT %s\n", yytext);
	strcpy(yylval.token, yytext);

	if (strlen(yylval.token) > 256) {
		yyerror("constant too long");
	}	

	return CONSTANT_CODE;
	}

{ID}	{
	printf("IDENTIFIER %s\n", yytext);

	if (strlen(yytext) > 256) {
		yyerror("identifier too long");
	}

	strcpy(yylval.token, yytext);
	return IDENTIFIER_CODE;
	}

.	{
	printf("UNIDENTIFIED %s\n", yytext);
	yyerror("unidentified atom");
	return 0;
}

