function x = P1(A, b)
  % Jacobi
  
  nrIterations = 50;
  epsilon = 0.1;
  
  M = diag(diag(A));
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

% A = [5,-2,3;-3,9,1;2,-1,-7];
% b = [-1;2;3];
