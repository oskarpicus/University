function [A, b] = P3(n)
  x = rand();
  A = x * eye(n);
  b = x * ones(n, 1);
endfunction