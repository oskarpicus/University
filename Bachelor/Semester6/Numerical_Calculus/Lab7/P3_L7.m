function P3_L7(x1, x2, y1, y2, tangent1, tangent2)
  pkg load symbolic;
  syms x;
  
  nodes = [x1, x2];
  values = [y1, y2];
  derivatives = [tangent1, tangent2];
  
  h = hermite(nodes, values, derivatives);
  
  Ox = -5:0.1:5;
  plot(Ox, double(subs(h, x, vpa(Ox))));
  
endfunction
