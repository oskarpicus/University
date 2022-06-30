function [z,ni]=secant(f,x0,x1,ea,er,Nmax)
  %SECANT - metoda secantei in R
  %input
  %f - functia
  %x0,x1 - aproximatiile initiale
  %ea,er - eroarea absoluta, respectiv relativa
  %Nmax - numar maxim de iteratii
  %output
  %z - aproximatia solutiei
  %ni - numar de iteratii
  
  if nargin<6, Nmax=50; end
  if nargin<5, er=0; end
  if nargin<4, ea=1e-3; end
  
  xv=x0; fv=f(xv); 
  xc=x1; fc=f(xc);
  for k=1:Nmax
    xn=xc-fc*(xc-xv)/(fc-fv);
    if abs(xn-xc)<ea+er*xn %success
      z=xn;
      ni=k;
      return
    endif
    xv=xc; fv=fc; xc=xn; fc=feval(f,xn);
  endfor

  %esec
  error('maximum iteration number exceeded')
endfunction

% secant(@(x) cos(x)-x, 0, 1)
