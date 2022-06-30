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
		yylval.code = PLUS_CODE;
	else if (strcmp(yytext, "-") == 0)
		yylval.code = MINUS_CODE;
	else if (strcmp(yytext, "*") == 0)
		yylval.code = ASTERIX_CODE;
	else if (strcmp(yytext, "/") == 0)
		yylval.code = DIVIDE_CODE;
	else if (strcmp(yytext, "%") == 0)
		yylval.code = MODULO_CODE;

	return yylval.code;
	}

"<"|">"|">="|"<="|"=="|"!="	{
	printf("RELATIONAL_OPERATOR %s\n", yytext);
	strcpy(yylval.token, yytext);
	if (strcmp(yytext, "<") == 0)
                yylval.code = LESS_THAN_CODE;
        else if (strcmp(yytext, ">") == 0)
                yylval.code = GREATER_THAN_CODE;
        else if (strcmp(yytext, ">=") == 0)
                yylval.code = GE_CODE;
        else if (strcmp(yytext, "<=") == 0)
                yylval.code = LE_CODE;
        else if (strcmp(yytext, "==") == 0)
                yylval.code = EQUAL_CODE;
	else if (strcmp(yytext, "!=") == 0)
		yylval.code = NOT_EQUAL_CODE; 

	return yylval.code;
	}

int|float|struct|if|while|return|#include<iostream>	{
	printf("KEY_WORD %s\n", yytext);
	strcpy(yylval.token, yytext);
	if (strcmp(yytext, "int") == 0)
                yylval.code = INT_CODE;
        else if (strcmp(yytext, "float") == 0)
                yylval.code = FLOAT_CODE;
        else if (strcmp(yytext, "struct") == 0)
                yylval.code = STRUCT_CODE;
        else if (strcmp(yytext, "if") == 0)
                yylval.code = IF_CODE;
        else if (strcmp(yytext, "while") == 0)
                yylval.code = WHILE_CODE;
        else if (strcmp(yytext, "return") == 0)
                yylval.code = RETURN_CODE;
	else if (strcmp(yytext, "#include<iostream>") == 0)
		yylval.code = IOSTREAM_CODE;

	return yylval.code;
	}

"="	{
	printf("ASSIGNMENT OPERATOR %s\n", yytext);
	strcpy(yylval.token, yytext);
	yylval.code = ASSIGNMENT_CODE;
	return yylval.code;
	}

"::"|">>"|"<<"	{
	printf("OTHER_OPERATOR %s\n", yytext);
	strcpy(yylval.token, yytext);
	if (strcmp(yytext, "::") == 0)
                yylval.code = RESOLUTION_CODE;
        else if (strcmp(yytext, ">>") == 0)
                yylval.code = RED_RIGHT_CODE;
        else if (strcmp(yytext, "<<") == 0)
                yylval.code = RED_LEFT_CODE;

	return yylval.code;
	}

"}"|"{"|";"|","|"("|")"	{
	printf("SEPARATOR %s\n", yytext);
	strcpy(yylval.token, yytext);
	if (strcmp(yytext, "}") == 0)
	       yylval.code = LEFT_BRACE_CODE;
        else if (strcmp(yytext, "{") == 0)
                yylval.code = RIGHT_BRACE_CODE;
        else if (strcmp(yytext, ";") == 0)
                yylval.code = SEMI_COLON_CODE;
	else if (strcmp(yytext, ",") == 0)
                yylval.code = COLON_CODE;
        else if (strcmp(yytext, " ") == 0)
                yylval.code = SPACE_CODE;
        else if (strcmp(yytext, "(") == 0)
                yylval.code = LEFT_BRACKET_CODE;
	else if (strcmp(yytext, ")") == 0)
		yylval.code = RIGHT_BRACKET_CODE;

	return yylval.code;
	}

[ \t\n]+	/* remove whitespaces */

{CONST} {
	printf("CONSTANT %s\n", yytext);
	strcpy(yylval.token, yytext);
	yylval.code = CONSTANT_CODE;

	if (strlen(yylval.token) > 256) {
		yyerror("constant too long");
	}	

	return yylval.code;
	}

{ID}	{
	printf("IDENTIFIER %s\n", yytext);

	if (strlen(yytext) > 256) {
		yyerror("identifier too long");
	}

	strcpy(yylval.token, yytext);
	yylval.code = IDENTIFIER_CODE;
	
	return yylval.code;
	}

.	{
	printf("UNIDENTIFIED %s\n", yytext);
	yyerror("unidentified atom");
	return 0;
}

