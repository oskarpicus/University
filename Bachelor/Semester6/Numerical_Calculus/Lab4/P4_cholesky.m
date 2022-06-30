function R = P4_cholesky(A)
  [m, n] = size(A);
  for k=1:m
    for j=k+1:m
      A(j, j:m) = A(j, j:m) - A(k, j:m) * A(k, j) / A(k, k);
    endfor
    A(k, k:m) = A(k, k:m) / sqrt(A(k, k));
  endfor
  R = triu(A);
endfunction