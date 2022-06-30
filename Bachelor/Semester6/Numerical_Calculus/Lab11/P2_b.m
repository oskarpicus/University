pkg load symbolic;
warning off;
format long;

syms x;
f = cos(x.^2);

eps = 10^(-7);
a=-1; b=1;

secondDeriv = diff(f, x, 2);
M2f = max(abs(double(subs(secondDeriv, x, a:0.1:b))))

% rest <= (b-a)^3 / (24*n^2) * M2f

% solve([(b-a)^3 / (24*x*2) * M2f < eps, x > 0], [x])
n = 3580;  # solutia inecuatiei

[g_nodes, g_coeff] = Gauss_Legendre(n);
g_coeff * double(subs(f, x, g_nodes))

warning on;