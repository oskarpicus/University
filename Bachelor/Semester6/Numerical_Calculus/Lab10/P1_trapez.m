function I = P1_trapez(f, a, b, n)
  %formula trapezelor
  %apel I=P1_trapez(f,a,b,n);
  %f functia de calculat integrala
  %a, b limitele intervalului de integrare
  %n indicator pentru precizie

  h=(b-a)/n;
  
  I=(f(a)+f(b)+2*sum(f([1:n-1]*h+a)))*h/2;
endfunction

% P1_trapez(@(x) exp(x), 1, 2, 10)