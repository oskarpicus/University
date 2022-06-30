# formula de cuadratura Gauss-Laguerre cu alpha = 0

err = 1e-9;
n = 1;
f = @(x) cos(x);

[g_nodes, g_coeffs] = Gauss_Laguerre(n, 0);
I_current = g_coeffs * f(g_nodes);
I_prev = I_current;
n += 1;

while 1
  [g_nodes, g_coeffs] = Gauss_Laguerre(n, 0);
  I_current = g_coeffs * f(g_nodes);
  
  if abs(I_prev - I_current) < err
    disp(n);
    disp(I_current);
    return;
  endif
  
  I_prev = I_current;
  n += 1;
endwhile
