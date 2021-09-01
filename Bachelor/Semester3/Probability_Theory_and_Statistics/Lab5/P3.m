function out=Lab5_3(N)
  U1=rand(1,N);
  U2=rand(1,N);
  X=sqrt(-2*log(U1)).*cos(2*pi*U2);
  Y=sqrt(-2*log(U1)).*sin(2*pi*U2);
  %frecventa relativa
  A(1:N)=0.5^2;
  sum(X.^2+Y.^2<=A)/N
  Rez_Teoretic=1-exp(-1/8)
  out=0;
endfunction
