function [x, ni] = aproxSuccesive(f, x0, ea, er, Nmax)
    %APROXSUCCESIVE - metoda aproximatiilor succesive
    %Intrare
    %f - functia
    %p0 - valoarea de pornire
    %ea,er - eroarea absoluta, respectiv relativa
    %Nmax - numar maxim de iteratii
    %Iesire
    %x - aproximatia solutiei
    %ni - numar de iteratii
    
    if nargin<6, Nmax=50; endif
    if nargin<5, er=0; endif
    if nargin<4, ea=1e-3; endif
  
    x = x0;
    for i=1:Nmax
      x = feval(f, x0);
      
      if abs(x - x0) < ea+er*x
        ni = i;
        return;
      endif
      
      x0 = x;
    endfor
endfunction
