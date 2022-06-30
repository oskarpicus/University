function out = P2_sinReduction(x)
  k = floor(x / (2 * pi));
  x = x - 2 * k * pi;
  out = P2_sin(x);  % ==> out = 0, pentru x = 2*k*pi
endfunction