function out = P3_cosPade(arg)
  k = floor(arg / (2 * pi));
  arg = arg - 2 * k * pi;
  
  padeCos = P3_pade(@(x) cos(x), 3, 3);
  syms x
  out = double(subs(padeCos, x, vpa(arg)));
endfunction