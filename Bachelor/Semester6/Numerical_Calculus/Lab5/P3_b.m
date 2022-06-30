function P3_b(n)
  % n - dimension of the matrix
  
  helper = -1 * ones(n);
  term = zeros(n);
  % indexes of the diagonals
  for i = [-3, -1, 1, 3]  
    term = term + diag(diag(helper, i), i);
  endfor
  A = 5 * eye(n) + term;
  
  b = 2 * ones(n, 1);
  b(1) = 3; b(n) = 3;
  
  % Jacobi
  disp('Jacobi');
  x = P1(A, b)
  disp('----------')
  
  % SOR
  disp('SOR');
  x = P2(A, b)
  disp('----------')
  
  % Gauss-Seidel
  disp('Gauss-Seidel');
  x = gaussSeidel(A, b)
  
endfunction
