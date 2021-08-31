#pragma once
typedef int TElem;
/*
Functie pentru determinarea distantei dintre 2 elemente
pre: el1, el2: TElem
post: el1 "-" el2: int
*/
TElem diferenta(TElem el1, TElem el2);


/*
Functie pentru determinarea elementului urmator dupa un numar prezicat de pozitii altui element
pre: el: TElem, i: int
post: al i-ulea element dupa el
*/
TElem next(TElem el, int i);

