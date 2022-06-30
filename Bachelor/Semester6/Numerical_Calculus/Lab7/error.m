function major = error(f, nodes, point)
  % error <= M_(m+1) * |u_m (x)| / (m+1)!
  syms x;
  m = size(nodes)(2) - 1;
  Ox = min(nodes):0.1:max(nodes);
  u = prod((point - nodes).^2);
  derivative = diff(f, x, m + 1);
  M = max(abs(double(subs(derivative, x, vpa(Ox)))));
  major = M * abs(u) / factorial(m + 1);
endfunction
