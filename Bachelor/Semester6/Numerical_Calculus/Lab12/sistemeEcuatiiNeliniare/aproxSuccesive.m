function [z, ni] = aproxSuccesive(f, x, x0, eps, Nmax)
  %APROXSUCCESIVE - metoda aproximarilor succesive pentru ecuatii in R^n
  %Intrare
  %f - functia, vector coloana de expresii simbolice
  %x - vector coloana de variabile simbolice
  %x0 - valoarea de pornire, vector coloana
  %eps - eroarea
  %Nmax - numar maxim de iteratii
  %Iesire
  %z - aproximatia solutiei
  %ni - numar de iteratii
  
  if nargin<5, Nmax=10; endif
  if nargin<4, eps=0; endif
  
  pkg load symbolic;
  warning off;
  
  j = jacobian(f, x);
  lambda = -subs(j, x, x0)^(-1);
  
  for i=1:Nmax
    value = subs(f, x, x0);
    z = double(x0 + lambda * value);
    
    if norm(z - x0) < eps
      ni = i;
      return;
    endif
    
    x0 = z;
  endfor
  
  warning on;
endfunction
