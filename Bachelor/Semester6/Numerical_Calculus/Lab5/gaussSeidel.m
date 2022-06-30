function x = gaussSeidel(A, b)
  % Gauss - Seidel
  
  nrIterations = 50;
  epsilon = 0.1;
  
  M = diag(diag(A)) - (- tril(A, -1));
  MInv = inv(M);
  N = M - A;
  
  x = ones(size(b));
  
  for i=1:nrIterations
    prev = x;
    x = MInv * N * x + MInv * b;
    if norm(x - prev, Inf) < epsilon * norm(x, Inf)
      return;
    endif
  endfor
endfunction
