function out = P2_cos(x)
  out = 0;
  term = 1;
  n = 0;
  while out + term ~= out
    term = ((-1)^n) * x^(2*n) / factorial(2*n);
    out = out + term;
    n = n + 1;
  endwhile
  
  % pentru x = 2*k*pi, rezultatul nu va fi 1 (se pierde precizie)
  % motiv: anulare, se scad cantitati care sunt foarte apropiate
  % pentru parametrul x ar trebui aplicata reducerea rangului 
endfunction