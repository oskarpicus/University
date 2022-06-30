function out = P3_sinPade(arg)
  k = floor(arg / (2 * pi));
  arg = arg - 2 * k * pi;
  
  padeSin = P3_pade(@(x) sin(x), 3, 3);
  syms x
  out = double(subs(padeSin, x, vpa(arg)));
endfunction