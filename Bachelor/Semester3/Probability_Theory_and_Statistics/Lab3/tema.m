% Folosind binornd in 5000 de simulari, estimati prob
% ca exact 2 zaruri din 5 aruncate sa arate nr div cu 3
% comparati val obt. cu prob. teoretica coresp.,
% folosind functia binopdf

function out = Tema_Lab3(M) % M = nr de simulari
  n=5; % se arunca 5 zaruri (<=> de 5 ori acelasi zar)
  p=2/6; % probabilitatea ca la aruncarea 
  % unui zar, sa obtinem un nr. div. cu 3 (pe 3 sau 6)
  x=binornd(n,p,1,M); %aruncam cele 5 zaruri de M ori
  cazFav=sum(x==2);
  out=cazFav/M; % probabilitatea
  
  clf; grid on; hold on;
  N=histc(x,0:n);
  bar(0:n,N/M,'hist','FaceColor','b');
  bar(0:n,binopdf(0:n,n,p),'hist','FaceColor','y');
  legend('estimated probabilities','theoretical probabilities');
endfunction
