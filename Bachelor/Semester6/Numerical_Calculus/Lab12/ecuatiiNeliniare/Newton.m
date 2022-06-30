function [z,ni]=Newton(f,fd,x0,ea,er,Nmax)
  %NEWTON - metoda lui Newton pentru ecuatii in R
  %Intrare
  %f - functia
  %fd - derivata
  %x0 - valoarea de pornire
  %ea,er - eroarea absoluta, respectiv relativa
  %Nmax - numar maxim de iteratii
  %Iesire
  %z - aproximatia solutiei
  %ni - numar de iteratii
  
  if nargin<6, Nmax=50; endif
  if nargin<5, er=0; endif
  if nargin<4, ea=1e-3; endif
  
  xv=x0;
  for k=1:Nmax
    xc=xv-f(xv)/fd(xv);
    % succes
    if abs(xc-xv)<ea+er*xc
      z=xc; ni=k;
      return
    endif
    xv=xc;
  endfor

  %esec
  error('s-a depasit numarul maxim de iteratii')
endfunction

% Newton(@(x) (cos(x) - x), @(x) (-sin(x) -1), 0)
