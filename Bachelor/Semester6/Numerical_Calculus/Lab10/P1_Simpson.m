function I = P1_Simpson(f, a, b, n)
  %regula lui Simpson
  %apel I=P1_trapez(f,a,b,n);
  %f functia de calculat integrala
  %a, b limitele intervalului de integrare
  %n indicator pentru precizie
  
  h=(b-a)/n;
  x2=[1:n-1]*h+a;
  x4=[0:n-1]*h+a+h/2;
  I=h/6*(f(a)+f(b)+2*sum(f(x2))+4*sum(f(x4)));
endfunction

% P1_Simpson(@(x) exp(x), 1, 2, 100)