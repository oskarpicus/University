% P2 - Liste in Prolog (II)
% Data predarii temei: 27.10.2020
% 2a) Sa se sorteze o lista cu pastrarea dublurilor.
% 2b) Se da o lista eterogena, formata din numere intregi si
% liste de numere. Sa se sorteze fiecare sublista cu pastrarea
% dublurilor
% Modele matematice recursive - vezi Lucrare de laborator P2


% minim_aux( L :list , Curent : element, Rez : element(result) )
% Predicat auxiliat pentru a determina minimul dintr-o lista
% L : lista din care se cauta minimul
% Curent : cel mai mic element gasit pana la momentul actual
% Rez : elementul in care se va pastra rezultatul final
% model de flux (i,i,o) si (i,i,i) determinist

minim_aux([],Curent,Curent):-!.
minim_aux([H|T],Curent,Rez):-
	H < Curent,
	!,
	minim_aux(T,H,Rez).
minim_aux([_|T],Curent,Rez):-
	minim_aux(T,Curent,Rez).


% minim( L :list, Rez : element(result) )
% Predicat pentru determinarea minimului dintr-o lista
% L : lista in care se determina minimul
% Rez : elementul minim din lista L
% model de flux (i,o) si (i,i) determinist

minim([H|T],Rez):-
	minim_aux(T,H,Rez).

% Exemple de testare:
% minim([1,2,3],R). ==> R = 1.
% minim([1,2,3],1). ==> true.
% minim([],R). ==> false.
% minim([5,4,3,2,1],R). ==> R = 1.
% minim([10,4,9,-10,32],R). ==> R = -10.

% -------------------------------------------------------------

% sterge_aux( L : list, E : element, Ok : Integer, LRez : list(result) )
% Predicat auxiliar pentru stergerea primei aparitii a
% unei valori dintr-o lista
% L : lista din care se sterge
% E : elementulde sters
% Ok : indicator (ia valori 0 sau 1), marcheaza daca valoarea
% de sters E a fost intalnita
% LRez : lista rezultata in urma stergerii din L a lui E
% model de flux (i,i,i,o) sau (i,i,i,i) determinist

sterge_aux([],_,_,[]):-!.
sterge_aux([H|T],E,Ok,[H|Rez]):-
	H =\= E,
	!,
	sterge_aux(T,E,Ok,Rez).
sterge_aux([H|T],E,Ok,Rez):-
	H =:= E,
	Ok =:= 0,
	!,
	Ok1 is 1,
	sterge_aux(T,E,Ok1,Rez).

sterge_aux([H|T],E,Ok,[H|Rez]):-
	sterge_aux(T,E,Ok,Rez).

% sterge( L : list, E : element, LRez : list ( result) )
% Predicat pentru stergerea primei aparitii a unui element
% intr-o lista
% L : lista din care se sterge
% E : elementul a carei prime aparitii se sterge
% LRez : rezultatul stergerii lui E din L
% model de flux (i,i,o) sau (i,i,i) determinist

sterge(L,E,LRez):-
	Ok is 0,
	sterge_aux(L,E,Ok,LRez).

% Exemple de testare:
% sterge([1,2,3],1,R). ==> R = [2,3].
% sterge([1,2,3],1,[2,3]). ==> true.
% sterge([1,2,3],4,R). ==> R = [1,2,3].
% sterge([],5,R). ==> R	= [].
% sterge([2,4,1,4,9,4,2],4,R). ==> R = [2,1,4,9,4,2].
% sterge([1,1,1],1,R). ==> R = [1,1].

% ------------------------------------------------------------

% sortareLista ( L : list, LRez : list(result) )
% Predicat pentru sortarea unei liste omogene prin
% Selection Sort (adaptat), cu pastrarea dublurilor
% L : lista de sortat
% LRez : lista obtinuta prin sortarea lui L
% model de flux (i,o) sau (i,i) determinist

sortareLista([],[]):-!.
sortareLista(L,Rez):-
	minim(L,Minim),
	sterge(L,Minim,Rez_sters),
	sortareLista(Rez_sters,Rez1),
	Rez=[Minim|Rez1].

% Exemple de testare:
% sortareLista([1,2,3],R). ==> R = [1,2,3].
% sortareLista([3,2,1],R). ==> R = [1,2,3].
% sortareLista([3,2,1],[1,2,3]). ==> true.
% sortareLista([],R). ==> R = [].
% sortareLista([1,5,8,9,6,7,-3,4,2,0],R). ==>
% R = [-3,0,1,2,4,5,6,7,8,9].
% sortareLista([4,2,6,2,3,4],R). ==> R = [2,2,3,4,4,6].

% ------------------------------------------------------------

% sortare( L : lista eterogenta, LRez : list(result) )
% Predicat pentru sortarea unei liste eterogene formata din
% numere intregi si liste de numere, cu pastrarea dublurilor
% L : lista eterogena de sortat
% LRez : lista obtinuta prin sortarea lui L
% model de flux (i,o) sau (i,i) determinist

sortare([],[]):-!.
sortare([H|T],Rez):-
	is_list(H),
	!,
	sortareLista(H,Rez1),
	sortare(T,Rez2),
	Rez=[Rez1|Rez2].
sortare([H|T],[H|Rez]):-
	sortare(T,Rez).

% Exemple de testare:
% sortare([1,2,[4,1,4],3,6,[7,10,1,3,9],5,[1,1,1],7],R). ==>
% R = [1,2,[1,4,4],3,6,[1,3,7,9,10],5,[1,1,1],7].
% sortare([1,[9,3,-4],4,[2]],R). ==> R = [1,[-4,3,9],4,[2]].
% sortare([],R). ==> R = [].
% sortare([1,2,[4,1,4],3,6,[7,10,1,3,9],5,[1,1,1],7],
% [1,2,[1,4,4],3,6,[1,3,7,9,10],5,[1,1,1],7]). ==> true.


