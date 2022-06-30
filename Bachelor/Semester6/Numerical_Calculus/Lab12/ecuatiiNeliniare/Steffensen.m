function [z, ni] = Steffensen(f, p0, ea, er, Nmax)
  %STEFFENSEN - metoda lui Steffensen
  %Intrare
  %f - functia
  %p0 - valoarea de pornire
  %ea,er - eroarea absoluta, respectiv relativa
  %Nmax - numar maxim de iteratii
  %Iesire
  %z - aproximatia solutiei ecuatiei f(p) = p
  %ni - numar de iteratii
  
  if nargin<6, Nmax=50; endif
  if nargin<5, er=0; endif
  if nargin<4, ea=1e-3; endif
  
  for i=1:Nmax
    p1 = f(p0);
    p2 = f(p1);
    p = p0 - (p1 - p0)^2 / (p2 - 2*p1 + p0);
    
    if abs(p - p0) < ea+er*p
      z = p; ni = i;
      return;
    endif
    
    p0 = p;
  endfor
  
  error('precizia nu poate fi atinsa cu numarul dat de iteratii');
endfunction

% Steffensen(@(x) sqrt(10./(x+4)), 1.5)
