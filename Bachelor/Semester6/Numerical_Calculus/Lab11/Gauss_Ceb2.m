function [g_coeff, g_nodes] = Gauss_Ceb2(n)
  %GAUSS_CEB2 - noduri si coeficienti Gauss-Cebisev #2
  
  alpha = zeros(n, 1);
  beta = [1/2*pi, 1/4.*ones(1, n-1)];
  [g_coeff, g_nodes] = Gaussquad(alpha, beta);
endfunction
