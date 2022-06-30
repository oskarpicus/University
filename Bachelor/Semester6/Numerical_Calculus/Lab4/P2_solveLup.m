function x = P2_solveLup(A, b)
  [L, U, P] = P2_lup(A);
  
  % Ly = Pb
  y = forwardsubst(L, P * b);
  
  % Ux = y
  x = backsubst(U, y);
endfunction

% A = [1,-1,6;2,1,-13;1,1,-1];
% b = [1;2;15];
% x = P2_solveLup(A, b); ==> [4.3793,12.069,1.4483] transpus
