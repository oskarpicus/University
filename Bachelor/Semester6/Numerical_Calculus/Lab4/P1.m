function x = P1(A)
  n = size(A)(1);
  x = zeros(n, 1);
  % eliminare
  for i=1:n-1
    p = -1; maximum = -Inf;
    for j=i:n
      if A(j, i) ~= 0 && A(j, i) > maximum
        p = j;
        maximum = A(j, i);
      endif
    endfor
    
    if p == -1
      error('Nu exista solutie unica');
    endif
    
    if p ~= i
      A([i, p], :) = A([p, i], :);
    endif
    
    for j=i+1:n
      m = A(j, i) / A(i, i);
      A(j, i+1:n+1) = A(j, i+1:n+1) - m * A(i, i+1:n+1);
    endfor
  endfor
  
  if A(n, n) == 0
    error('Nu exista solutie unica');
  endif
  
  % substitutie inversa
  x(n) = A(n, n+1) / A(n, n);
  for i=n-1:-1:1
    x(i) = (A(i, n+1) - A(i, i+1:n)*x(i+1:n)) / A(i, i);
  endfor
endfunction

% P1([1,-1,6,1;2,1,-13,2;1,1,-1,15]) ==> [4.3793,12.069,1.4483] transpus
