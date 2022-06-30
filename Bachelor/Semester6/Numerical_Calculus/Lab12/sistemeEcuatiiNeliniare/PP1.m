pkg load symbolic;

syms x;
syms y;

f = [x.^2 + y.^2; x.^3 - y];

Newton(f, [x; y], [1; 1])
