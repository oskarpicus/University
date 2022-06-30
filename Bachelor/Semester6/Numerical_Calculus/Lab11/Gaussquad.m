function [g_nodes,g_coeff]=Gaussquad(alpha,beta)
  %GAUSSQUAD - generare cuadratura Gauss
  %calculeaza noduri si coeficienti pentru
  %cuadraturi Gauss cu alpha si beta cunoscuti
  %metoda - matrice Jacobi
  
  n=length(alpha); rb=sqrt(beta(2:n));
  J=diag(alpha)+diag(rb,-1)+diag(rb,1);
  [v,d]=eig(J);
  g_nodes=diag(d);
  g_coeff=beta(1)*v(1,:).^2;
endfunction