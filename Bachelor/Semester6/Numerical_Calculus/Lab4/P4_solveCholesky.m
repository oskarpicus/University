function x = P4_solveCholesky(A, b)
  R = transpose(P4_cholesky(A));
  
  y = forwardsubst(R, b);
  
  x = backsubst(R', y);
endfunction

% A = [1,2,1;2,5,3;1,3,3];
% b = [4;10;7]
% x = P4_solveCholesky(A, b) ==> x = [1, 1, 1] transpus
