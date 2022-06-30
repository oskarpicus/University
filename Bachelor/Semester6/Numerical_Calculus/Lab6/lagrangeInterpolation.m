function f = lagrangeInterpolation(nodes, y)
  pkg load symbolic;
  
  f = sym(0);
  for i=1:size(nodes)(2)
    f = f + y(i) * lagrangeFundamentalPolynom(i - 1, nodes);
  endfor
endfunction

% lagrangeInterpolation([2, 2.75, 4], [0.5, 4/11, 1/4])
