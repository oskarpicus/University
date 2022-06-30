function P2(maxGrade, nodes)
  pkg load symbolic;
  syms x;
  
  Ox = -1:0.1:1;
  for i=0:maxGrade
    p = lagrangeFundamentalPolynom(i, nodes)
    Oy = double(subs(p, x, vpa(Ox)));
    plot(Ox, Oy);
    hold on;
  endfor
endfunction

% P2(2, [2, 2.75, 4])
