function I = P1_dreptunghi(f, a, b, n)
  %formula dreptunghiurilor
  %apel I=P1_trapez(f,a,b,n);
  %f functia de calculat integrala
  %a, b limitele intervalului de integrare
  %n indicator pentru precizie
  
  h = (b-a)/n;
  xK = [0:n]*h+a;
  
  arguments = zeros(1, n);
  for k=1:n
    arguments(k) = (xK(k) + xK(k+1)) / 2;
  endfor
  
  I = h * sum(f(arguments));
endfunction

% P1_dreptunghi(@(x) exp(x), 1, 2, 100)