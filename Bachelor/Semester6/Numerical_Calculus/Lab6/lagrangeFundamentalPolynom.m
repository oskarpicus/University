function f = lagrangeFundamentalPolynom(i, nodes)
  % i - gradul
  % nodes - nodurile
  
  warning('off');
  pkg load symbolic;
  
  i = i + 1;
  syms x;
  f = sym(1);
  for j=1:size(nodes)(2)
    if i ~= j
      f = f * ((x - nodes(j)) / (nodes(i) - nodes(j)));
    endif
  endfor
  
  warning('on');
endfunction

% lagrangeFundamentalPolynom(0, [2, 2.75, 4])
% lagrangeFundamentalPolynom(1, [2, 2.75, 4])
% lagrangeFundamentalPolynom(2, [2, 2.75, 4])
