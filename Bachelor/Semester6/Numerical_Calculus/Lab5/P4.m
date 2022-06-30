function P4(n)
  A = eye(n);
  b = zeros(n, 1);
  for i=1:n
    A(i, i) = 20 * rand() - 10;
    b(i) = i * A(i, i);
  endfor
  
  A
  b
  
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