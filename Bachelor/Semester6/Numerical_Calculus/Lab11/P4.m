% formula de cuadratura Gauss-Cebisev # 2 cu 10 noduri

[g_nodes, g_coeff] = Gauss_Ceb2(10);

f = @(x) exp(-x.^2);
w = @(x) sqrt(1-x.^2);
g = @(x) w(x).*f(x);

disp('estimare:')
est = g_coeff*f(g_nodes)

disp('matlab:')
r = integral(g, -1, 1)

disp('eroarea:')
abs(est - r)