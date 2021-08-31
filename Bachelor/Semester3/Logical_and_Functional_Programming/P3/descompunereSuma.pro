
%candidat(N : integer, E : integer)
%Predicat pentru determinarea fiecare numar din multimea
% {1,...,n} (de la mai mare la mai mic)
%N : numarul pentru care determinam aceste numere
%E : variabile in care stocam aceste numere
%(i,o) (i,i) nedeterminist
candidat(N,N).
candidat(N,E):-
	N>1,
	N1 is N-1,
	candidat(N1,E).

%Exemple de testare:
%candidat(3,E). ==> E=3; E=2; E=1.
%candidat(1,E). ==> E=1;
%candidat(3,2). ==> true.


%descSuma(N : integer ,L : list)
%Predicat pentru determinarea descompunerilor unui numar
%in suma de numere consecutive
%N : numarul pentru care determinam descompunerile
%L : lista in care memoram o astfel de descompunere
%(i,o),(i,i) nedeterminist
descSuma(N,L):-
	candidat(N,E),
	E < N,
	descSumaAux(N,L,[E],E).

%Exemple de testare
%descSuma(3,L). ==> L=[1,2]; false.
%descSuma(15,L).==> L=[7,8] ; L=[4,5,6] ; L=[1,2,3,4,5]; false
%descSuma(15,[4,5,6]). ==> true.
%descSuma(15,[7,8,9]). ==> false.


%descSumaAux(N : integer, L : list, Col : list, Sum : integer)
%Predicat auxiliar pentru determinarea descompunerilor unui
%numar in suma de numere consecutive
%N : numarul pentru care determinam descompunerile
%L : lista in care memoram la final o astfel de descompunere
%Col : lista in care construim descompunerile
%Sum : suma elementelor din Col
%(i,o,i,i),(i,i,i,i) nedeterminist

descSumaAux(N,Col,Col,N):-!.
%taietura pentru ca nu putem obtine o alta solutie din aceasta

descSumaAux(N,L,[H|T],Sum):-
	candidat(N,E),
	ElemConsecutiv is H-1,
	E =:= ElemConsecutiv,
	SumNou is Sum+E,
	SumNou =< N,
	descSumaAux(N,L,[E|[H|T]],SumNou).


% toate( N : integer, L : list )
% Predicat pentru colectarea tuturor solutiilor descompunerii
% unui numar in suma de numere consecutive
% N : numarul de descompus
% L : lista in care se colecteaza toate solutiile
% (i,o), (i,i) determinist
toate(N,L):-
	findall(Lista,descSuma(N,Lista),L).

% Exemple de testare:
% toate(3,L). ==> L=[[1,2]].
% toate(3,[[1,2]]). ==> true.
% toate(9,L). ==> L = [[4, 5], [2, 3, 4]].
% toate(9,[[4,5],[2,3,4]]). ==> true.
