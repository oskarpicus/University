%{
	#include <stdio.h>
	#include <stdlib.h>
	#include <string.h>
	#include <ctype.h>

	int yylex(void);
	int yyerror(char* s);	
	extern FILE* yyin;

	char dataSegment[4096];
	char codeSegment[4096];
	int temporaryNames = 1;

	void writeToFile();
	void newTempName(char* s);
%}

%union{
	char token[256];
}

%start program

%token PLUS_CODE 
%token MINUS_CODE 
%token ASTERIX_CODE 
%token DIVIDE_CODE 
%token MODULO_CODE
%token INT_CODE  
%token ASSIGNMENT_CODE 
%token RESOLUTION_CODE
%token RED_RIGHT_CODE
%token IOSTREAM_CODE 
%token LEFT_BRACE_CODE 
%token RIGHT_BRACE_CODE 
%token SEMI_COLON_CODE 
%token SPACE_CODE  
%token LEFT_BRACKET_CODE 
%token RIGHT_BRACKET_CODE 
%token RED_LEFT_CODE 

%token <token> CONSTANT_CODE
%token <token> IDENTIFIER_CODE

%type <token> expresie
%type <token> variabila

%%
 
program:	IOSTREAM_CODE INT_CODE IDENTIFIER_CODE LEFT_BRACKET_CODE RIGHT_BRACKET_CODE RIGHT_BRACE_CODE lista_instructiuni LEFT_BRACE_CODE { printf("Program valid\n");} ; 

variabila: IDENTIFIER_CODE { strcpy($$, $1); } 
	| CONSTANT_CODE { strcpy($$, $1); }
	;

expresie:	variabila PLUS_CODE variabila
	{
		char* numeVariabila = (char*) malloc(sizeof(char) * 250);
		newTempName(numeVariabila);
		strcpy($$, numeVariabila);

		char* code = (char*) malloc(sizeof(char) * 250);
		sprintf(code, isdigit($1[0]) ? "mov eax, %s\n" : "mov eax, [%s]\n", $1);
		strcat(codeSegment, code);
		sprintf(code, isdigit($3[0]) ? "add eax, dword %s\n" : "add eax, [%s]\n", $3);
		strcat(codeSegment, code);
		sprintf(code, "mov [%s], eax\n\n", numeVariabila);
		strcat(codeSegment, code);

		free(numeVariabila);		
		free(code);
	}
		| variabila MINUS_CODE variabila
	{
		char* numeVariabila = (char*) malloc(sizeof(char) * 250);
		newTempName(numeVariabila);
		strcpy($$, numeVariabila);

		char* code = (char*) malloc(sizeof(char) * 250);
		sprintf(code, isdigit($1[0]) ? "mov eax, %s\n" : "mov eax, [%s]\n", $1);
		strcat(codeSegment, code);
		sprintf(code, isdigit($3[0]) ? "sub eax, dword %s\n" : "sub eax, [%s]\n", $3);
		strcat(codeSegment, code);
		sprintf(code, "mov [%s], eax\n\n", numeVariabila);
		strcat(codeSegment, code);

		free(numeVariabila);
		free(code);
	}
		| variabila ASTERIX_CODE variabila
	{
		char* numeVariabila = (char*) malloc(sizeof(char) * 250);
                newTempName(numeVariabila);
                strcpy($$, numeVariabila);

                char* code = (char*) malloc(sizeof(char) * 250);
                sprintf(code, isdigit($1[0]) ? "mov eax, %s\n" : "mov eax, [%s]\n", $1);
                strcat(codeSegment, code);
		sprintf(code, isdigit($3[0]) ? "mov ebx, %s\nmul ebx\n" : "mul dword[%s]\n", $3);
                strcat(codeSegment, code);
                sprintf(code, "mov [%s], eax\n\n", numeVariabila); 
                strcat(codeSegment, code);

                free(numeVariabila);
                free(code);

	}
		| variabila DIVIDE_CODE variabila
	{
		char* numeVariabila = (char*) malloc(sizeof(char) * 250);
                newTempName(numeVariabila);
                strcpy($$, numeVariabila);

                char* code = (char*) malloc(sizeof(char) * 250);
                sprintf(code, isdigit($1[0]) ? "mov eax, %s\n" : "mov eax, [%s]\n", $1);
                strcat(codeSegment, code);
		strcpy(code, "mov edx, 0\n");
		strcat(codeSegment, code);
                sprintf(code, isdigit($3[0]) ? "mov ebx, %s\ndiv ebx\n" : "div dword[%s]\n", $3);
                strcat(codeSegment, code);
                sprintf(code, "mov [%s], eax\n\n", numeVariabila);
                strcat(codeSegment, code);

                free(numeVariabila);
                free(code);

	}
		;

declaratii_variabile:	declaratie_variabila
			| declaratie_variabila declaratii_variabile
			;

declaratie_variabila:	INT_CODE IDENTIFIER_CODE SEMI_COLON_CODE 
	{
		char* numeVariabila = (char*) malloc(sizeof(char) * 250);
		sprintf(numeVariabila, "%s dd 0\n", $2);
		strcat(dataSegment, numeVariabila);
		free(numeVariabila);
	}
	;

instructiune:	declaratii_variabile
		| atribuire
		| intrare
		| iesire
		;

lista_instructiuni:	instructiune
			| instructiune lista_instructiuni
			;

atribuire:	IDENTIFIER_CODE ASSIGNMENT_CODE expresie SEMI_COLON_CODE
	{
		char* code = (char*) malloc(sizeof(char) * 250);
		sprintf(code, "mov eax, [%s]\n", $3);
		strcat(codeSegment, code);
		sprintf(code, "mov [%s], eax\n\n", $1);
		strcat(codeSegment, code);
		free(code);	
	}
		| IDENTIFIER_CODE ASSIGNMENT_CODE CONSTANT_CODE SEMI_COLON_CODE
	{	
		char* code = (char*) malloc(sizeof(char) * 250);
		sprintf(code, "mov eax, %s\n", $3);
		strcat(codeSegment, code);
		sprintf(code, "mov [%s], eax\n\n", $1);
		strcat(codeSegment, code);
		free(code);
	}
		| IDENTIFIER_CODE ASSIGNMENT_CODE IDENTIFIER_CODE SEMI_COLON_CODE
	{
		char* code = (char*) malloc(sizeof(char) * 250);
                sprintf(code, "mov eax, [%s]\n", $3);
                strcat(codeSegment, code);
                sprintf(code, "mov [%s], eax\n\n", $1);
                strcat(codeSegment, code);
                free(code);
	}
		;

iesire:		IDENTIFIER_CODE RESOLUTION_CODE IDENTIFIER_CODE RED_LEFT_CODE IDENTIFIER_CODE SEMI_COLON_CODE
	{
		char* text = (char*) malloc(sizeof(char) * 250);
		sprintf(text, "push dword [%s]\npush dword format\ncall [printf]\nadd esp, 4*2\n\n", $5);
		strcat(codeSegment, text);
		free(text);
	}
	;

intrare:	IDENTIFIER_CODE RESOLUTION_CODE IDENTIFIER_CODE RED_RIGHT_CODE IDENTIFIER_CODE SEMI_COLON_CODE 
	{
		char* text = (char*) malloc(sizeof(char) * 250);
		sprintf(text, "push dword %s\npush dword format\ncall [scanf]\nadd esp, 4*2\n\n", $5);
		strcat(codeSegment, text);
		free(text);
	}
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

	writeToFile();
	return 0;
}

void writeToFile() {
	FILE* file = fopen("out.asm", "w");
	if (file == NULL) {
		perror("Failed to open file\n");
		exit(1);
	}

	char* header = (char*) malloc(sizeof(char) * 4000);
	strcpy(header, "bits 32\n\n");
	strcat(header, "global start\n\n");
	strcat(header, "extern exit, printf, scanf\n\n");
	strcat(header, "import exit msvcrt.dll\nimport printf msvcrt.dll\nimport scanf msvcrt.dll\n\n");
	fwrite(header, strlen(header), sizeof(char), file);	

	char* segmentData = (char*) malloc(sizeof(char) * 4000);
	strcpy(segmentData, "segment data use32 class=data\nformat db '%d', 0\n");
	strcat(segmentData, dataSegment);
	strcat(segmentData, "\n");
	fwrite(segmentData, strlen(segmentData), sizeof(char), file);

	char* segmentCode = (char*) malloc(sizeof(char) * 4000);
	strcpy(segmentCode, "segment code use32 class=code\nstart:\n");
	strcat(segmentCode, codeSegment);
	strcat(segmentCode, "push dword 0\ncall [exit]\n");
	fwrite(segmentCode, strlen(segmentCode), sizeof(char), file);

	fclose(file);
	free(header);
	free(segmentData);
	free(segmentCode);
}

// newTempName will add a new variable in the datasegment. Will populate the s parameter with the given name for the variable.
void newTempName(char* s) {
	sprintf(s, "temp%d dd 1\n", temporaryNames);
	strcat(dataSegment, s);
	sprintf(s, "temp%d", temporaryNames);
	temporaryNames++;	
}
