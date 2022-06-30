function out = P2_sin(x)
  out = 0;
  term = 1;
  n = 0;
  while out + term ~= out
    term = ((-1)^n) * x^(2*n+1) / factorial(2*n + 1);
    out = out + term;
    n = n + 1;
  endwhile
  
  % pentru x = 2*k*pi, rezultatul nu va fi 0
  % motiv: anulare, se scad cantitati care sunt foarte apropiate
  % pentru parametrul x ar trebui aplicata reducerea rangului 
endfunction