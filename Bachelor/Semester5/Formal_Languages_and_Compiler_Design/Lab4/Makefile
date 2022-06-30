
CC 	= gcc
CFLAGS	= -g -Wall

output:		bison.o lex.o
		$(CC) $(CFLAGS) bison.o lex.o -o output

bison.o:	bison.tab.c
		$(CC) $(CFLAGS) -c bison.tab.c -o bison.o

lex.o:		lex.yy.c
		$(CC) $(CFLAGS) -c lex.yy.c -o lex.o

lex.yy.c:	lex.l
		flex lex.l

bison.tab.c:	bison.y
		bison -d -v bison.y

clean:
	rm -f *.o *~ lex.yy.c bison.tab.c bison.tab.h output
