function P2_L7(f, nodes)
  pkg load symbolic;
  
  syms x;
  fD = diff(sym(f));
  
  values = f(nodes);
  derivatives = double(subs(fD, x, vpa(nodes)));
  
  h = hermite(nodes, values, derivatives);
  
  Ox = min(nodes):0.1:max(nodes);
  plot(Ox, f(Ox));
  hold on;
  plot(Ox, double(subs(h, x, vpa(Ox))));
  
endfunction
