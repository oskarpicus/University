function E=P5(n, initial)
  E = initial;
  for k = 2:n
    E = 1-k*E;
  endfor
endfunction

% P5(100, 1/exp(1)) = -1.1599e+141 ==> tinde la 0
% P5(100, -9) = 8.7427e+158 ==> tinde la Inf