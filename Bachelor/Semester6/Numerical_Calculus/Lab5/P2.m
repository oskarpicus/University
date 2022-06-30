function x = P2(A, b)
  % SOR
  
  % compute omega - by computing first the Jacobi matrix
  M = diag(diag(A));
  N = M - A;
  T = inv(M) * N;
  rho = max(abs(eig(T)));
  omega = 2 / (1 + sqrt(1 - rho^2));
  
  M = (1 / omega) * diag(diag(A)) - (- tril(A, -1));
  MInv = inv(M);
  N = M - A;
  
  nrIterations = 50;
  epsilon = 0.01;
  x = ones(size(b));
  
  for i=1:nrIterations
    prev = x;
    x = MInv * N * x + MInv * b;
    if norm(x - prev, Inf) < epsilon * norm(x, Inf)
      return;
    endif
  endfor
  
endfunction
