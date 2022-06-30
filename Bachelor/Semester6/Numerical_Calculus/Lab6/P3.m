function P3(f, nodes)
  % f - the function
  % nodes - nodes in the function's domain
  
  pkg load symbolic;
  syms x;
  
  values = zeros(size(nodes));
  for i=1:size(nodes)(2)
    values(i) = f(nodes(i));
  endfor
  
  pil = lagrangeInterpolation(nodes, values)
  
  Ox = min(nodes):0.1:max(nodes);
  
  Oy = zeros(size(Ox));
  for i=1:size(Ox)(2)
    Oy(i) = f(Ox(i));
  endfor
  
  plot(Ox, Oy);
  hold on;
  
  Oy = double(subs(pil, x, vpa(Ox)));
  plot(Ox, Oy);
  
endfunction

% P3(@(y) 1 / y, [2, 2.75, 4])
