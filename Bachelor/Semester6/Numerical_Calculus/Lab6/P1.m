function out = P1(nodes, y, points)
  % nodes - nodurile
  % y - valorile functiei in noduri
  % points - punctele
  % out - valorile polinomului Lagrange in points
  pkg load symbolic;
  syms x;
  
  pil = lagrangeInterpolation(nodes, y);
  out = double(subs(pil, x, vpa(points)));
  
endfunction

% P1([2, 2.75, 4], [0.5, 0.3636, 0.25], [2, 2.75, 4, 3]) 
% ==> [0.5, 0.3636, 0.25, 0.3431])
