function P3_a(n)
  % n - dimension of the matrix
  
  helper = -1 * ones(n);
  A = 5 * eye(n) + diag(diag(helper, -1), -1)  + diag(diag(helper, 1), 1);
  
  b = 3 * ones(n, 1);
  b(1) = 4; b(n) = 4;
  
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
