function P3(x, f, tip, der)
  %P3 - deseneaza spline-ul cubic ce trece prin anumite puncte
  %apel P3(x,f,tip,t,der)
  %x - abscisele
  %f - ordonatele
  %tip - 0  complet
  %      1  cu derivate secunde
  %      2  natural
  %      3  not a knot (deBoor)
  %der - informatii despre derivate 
  %      [f'(a),f'(b)] pentru tipul 0
  %      [f''(a), f''(b)] pentru tipul 1
  
  [a, b, c, d] = P1(x, f, tip, der);
  
  Ox = min(x):0.1:max(x);
  Oy = P2(x, a, b, c, d, Ox);
  
  plot(Ox, Oy);
  
endfunction

% Ox=0:0.1:2*pi;
% P3(Ox, sin(Ox), 2, []);
% P3(Ox, sin(Ox), 0, cos(Ox));
