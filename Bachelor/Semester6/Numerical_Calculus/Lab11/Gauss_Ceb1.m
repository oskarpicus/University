function [g_nodes,g_coeff]=Gauss_Ceb1(n)
  %GAUSS_CEB1 - noduri si coeficienti Gauss-Cebisev #1
  
  alpha = zeros(n, 1);
  beta = [pi, 1/2, (1/4)*ones(1, n-2)];
  [g_nodes, g_coeff] = Gaussquad(alpha, beta);
endfunction