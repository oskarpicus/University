% P1 - Liste ȋn Prolog (I)
% Data predarii temei : 13.10.2020
% 1a)  Sa se scrie un predicat care intoarce diferenta
% a doua multimi.
% Modele matematice recursive - vezi Lucrare de laborator P1

% apare( L : list, E : element)
% Verifica daca un element apare intr-o lista
% L : lista in care cautam un anumit element
% E : elementul de cautat
% model de flux (i,i) determinist (o,i) determinist

apare([E|_],E):-!.
apare([_|T],E):-
	apare(T,E).

% Exemple de testare:
% apare([1,2,3],3). ==> true.
% apare([1,2,3],4). ==> false.


% diferenta( L : list, K : list, R : list(result))
% Calculeaza diferenta a celor 2 multimi reprezentate ca
% liste L si K
% L : lista (descazut)
% K : lista (scazator)
% R : lista rezultata, R = L \ K
% model de flux (i,i,o) determinist sau (i,i,i) determinist

diferenta([],_,[]):-!.
diferenta([H|T],K,[H|RES]):-
	not(apare(K,H)),
	!,
	diferenta(T,K,RES).
diferenta([_|T],K,RES):-
	diferenta(T,K,RES).

% Exemple de testare
% diferenta([1,2,3],[4,5,6],R). ==> R = [1,2,3].
% diferenta([1,2,3],[4,5,6],[1,2,3]). ==> true.
% diferenta([1,2,3],[1,2,3],R). ==> R = [].
% diferenta([1,2,3],[1,2,3],[]). ==> true.
% diferenta([1,2,3],[1,2,3],[1]). ==> false.
% diferenta([4,9,11],[5,2,4,10,11,12],R). ==> R = [9].
