pkg load symbolic;

syms x;
syms y;
syms z;

f = [9*x^2+36*y^2+4*z^2-36; x^2-2*y^2-20*z; x^2-y^2+z^2];

initials = [[1; 1; 0], [1; -1; 0], [-1; 1; 0], [-1; -1; 0]];

disp('Newton')
for x0=initials
   Newton(f, [x; y; z], x0)
endfor

disp('Aproximari succesive')
for x0=initials
   aproxSuccesive(f, [x; y; z], x0)
endfor
