function out = P4(points, f, m, nodes)
  y = zeros(size(nodes));
  i = 1;
  for node=nodes
    y(i) = f(node);
    i = i + 1;
  endfor
  
  out = P1(nodes, y, points);
endfunction

% P4([2, 3, 4, 5], @(y) 1 / y, 3, [2, 2.75, 4])
