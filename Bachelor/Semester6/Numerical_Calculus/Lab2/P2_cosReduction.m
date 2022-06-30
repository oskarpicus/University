function out = P2_cosReduction(x)
  k = floor(x / (2 * pi));
  x = x - 2 * k * pi;
  out = P2_cos(x); % ==> out = 1, pentru x = 2*k*pi
endfunction