function out = P1_L7(nodes, values, derivatives, points)
  f = hermite(nodes, values, derivatives);
  syms x;
  out = double(subs(f, x, vpa(points)));
endfunction
