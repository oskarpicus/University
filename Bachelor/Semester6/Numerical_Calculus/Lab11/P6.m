n = 10;

disp('integral e^(-x^2) * sin(x)');
f = @(x) sin(x);
[g_nodes, g_coeff] = Gauss_Hermite(n);
disp(g_coeff * f(g_nodes));

disp('integral e^(-x^2) * cos(x)');
f = @(x) cos(x);
[g_nodes, g_coeff] = Gauss_Hermite(n);
disp(g_coeff * f(g_nodes));
