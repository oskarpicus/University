%{
	#include <stdio.h>
	#include <stdlib.h>

	int yylex(void);
	int yyerror(char* s);	
	extern FILE* yyin;
%}

%union{
	int code;
	char token[256];
}

%start program

%token PLUS_CODE 
%token MINUS_CODE 
%token ASTERIX_CODE 
%token DIVIDE_CODE 
%token MODULO_CODE
%token LESS_THAN_CODE
%token GREATER_THAN_CODE
%token GE_CODE 
%token LE_CODE 
%token EQUAL_CODE 
%token NOT_EQUAL_CODE 
%token INT_CODE  
%token FLOAT_CODE 
%token STRUCT_CODE  
%token ASSIGNMENT_CODE 
%token RESOLUTION_CODE
%token RED_RIGHT_CODE
%token IF_CODE 
%token WHILE_CODE 
%token RETURN_CODE 
%token IOSTREAM_CODE 
%token LEFT_BRACE_CODE 
%token RIGHT_BRACE_CODE 
%token SEMI_COLON_CODE 
%token COLON_CODE  
%token SPACE_CODE  
%token LEFT_BRACKET_CODE 
%token RIGHT_BRACKET_CODE 
%token RED_LEFT_CODE 
%token CONSTANT_CODE 
%token IDENTIFIER_CODE 

%%
 
program:	IOSTREAM_CODE INT_CODE IDENTIFIER_CODE LEFT_BRACKET_CODE RIGHT_BRACKET_CODE RIGHT_BRACE_CODE lista_instructiuni LEFT_BRACE_CODE { printf("Program valid\n");} ; 

operator_aritmetic:	PLUS_CODE 
			| MINUS_CODE
			| ASTERIX_CODE
			| DIVIDE_CODE
			| MODULO_CODE
			;

operator_relational:	LESS_THAN_CODE
			| GREATER_THAN_CODE
			| GE_CODE
			| LE_CODE
			| EQUAL_CODE
			| NOT_EQUAL_CODE
			;

expresie:	CONSTANT_CODE operator_aritmetic IDENTIFIER_CODE
		| CONSTANT_CODE operator_aritmetic CONSTANT_CODE
		| IDENTIFIER_CODE operator_aritmetic CONSTANT_CODE
		| IDENTIFIER_CODE operator_aritmetic IDENTIFIER_CODE
		;

tip_date:	INT_CODE
		| FLOAT_CODE
		| structura
		;

structura:	STRUCT_CODE IDENTIFIER_CODE RIGHT_BRACE_CODE declaratii_variabile LEFT_BRACE_CODE SEMI_COLON_CODE;

declaratii_variabile:	declaratie_variabila
			| declaratie_variabila declaratii_variabile
			;

declaratie_variabila:	tip_date IDENTIFIER_CODE SEMI_COLON_CODE;

instructiune:	declaratii_variabile
		| atribuire
		| intrare
		| iesire
		| if
		| while
		| return
		;

lista_instructiuni:	instructiune
			| instructiune lista_instructiuni
			;

atribuire:	IDENTIFIER_CODE ASSIGNMENT_CODE expresie SEMI_COLON_CODE
		| IDENTIFIER_CODE ASSIGNMENT_CODE CONSTANT_CODE SEMI_COLON_CODE
		| IDENTIFIER_CODE ASSIGNMENT_CODE IDENTIFIER_CODE SEMI_COLON_CODE
		;

iesire:		IDENTIFIER_CODE RESOLUTION_CODE IDENTIFIER_CODE RED_LEFT_CODE IDENTIFIER_CODE SEMI_COLON_CODE
		| IDENTIFIER_CODE RESOLUTION_CODE IDENTIFIER_CODE RED_LEFT_CODE CONSTANT_CODE SEMI_COLON_CODE
		| IDENTIFIER_CODE RESOLUTION_CODE IDENTIFIER_CODE RED_LEFT_CODE expresie SEMI_COLON_CODE
		;

intrare:	IDENTIFIER_CODE RESOLUTION_CODE  IDENTIFIER_CODE RED_RIGHT_CODE IDENTIFIER_CODE SEMI_COLON_CODE
		;

if:		IF_CODE LEFT_BRACKET_CODE cond RIGHT_BRACKET_CODE RIGHT_BRACE_CODE lista_instructiuni LEFT_BRACE_CODE;
cond:		IDENTIFIER_CODE operator_relational IDENTIFIER_CODE
		| IDENTIFIER_CODE operator_relational CONSTANT_CODE
		| CONSTANT_CODE operator_relational IDENTIFIER_CODE
		| CONSTANT_CODE operator_relational CONSTANT_CODE
		;

while:		WHILE_CODE LEFT_BRACKET_CODE cond RIGHT_BRACKET_CODE RIGHT_BRACE_CODE lista_instructiuni LEFT_BRACE_CODE;

return:		RETURN_CODE CONSTANT_CODE SEMI_COLON_CODE 
		| RETURN_CODE IDENTIFIER_CODE SEMI_COLON_CODE
		;

%%

int yyerror(char* s) {
	extern char* yytext;

	printf("ERROR %s at %s\n", s, yytext);
	exit(1);
}

int main(int argc, char** argv) {
	if (argc > 1)
		yyin = fopen(argv[1], "r");
	else
		yyin = stdin;

	while (!feof(yyin)) {
		yyparse();
	}

	return 0;
}
