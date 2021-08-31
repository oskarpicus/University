% P1 - Liste ȋn Prolog (I)
% Data predarii temei : 13.10.2020
% 1b) Sa se scrie un predicat care adauga intr-o lista dupa
% fiecare element par valoarea 1
% Modele matematice recursive - vezi Lucrare de laborator P1

% adauga_1_dupa_par( L : list, R : list(result) )
% Se creeaza o noua lista din L in care toate elementele pare
% sunt urmate de valoarea 1
% L : lista de procesat
% R : lista rezultat, dupa inserarea valorilor 1
% model de flux (i,o) determinist sau (i,i) determinist

adauga_1_dupa_par([],[]):-!.
adauga_1_dupa_par([H|T],[H,1|RES]):-
	mod(H,2)=:=0,
	!,
	adauga_1_dupa_par(T,RES).
adauga_1_dupa_par([H|T],[H|RES]):-
	adauga_1_dupa_par(T,RES).

% Exemple de testare
% adauga_1_dupa_par([1,2,3,4],R). ==> R = [1,2,1,3,4,1].
% adauga_1_dupa_par([1,2,3,4,5],R). ==> R = [1,2,1,3,4,1,5].
% adauga_1_dupa_par([1,2,3,4],[1,2,1,3,4,1]). ==> true
% adauga_1_dupa_par([1,2,3,4],[1,2,1,7]). ==> false
% adauga_1_dupa_par([5,7,19],R). ==> R = [5,7,19].
% adauga_1_dupa_par([2,90,128,66],R). ==> R = [2,1,90,1,128,1,66,1].
