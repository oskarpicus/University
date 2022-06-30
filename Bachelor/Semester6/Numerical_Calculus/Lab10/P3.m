function I = P3(f, a, b, eps, g)
  %cuadratura adaptiva I
  %apel I=adaptquad(f,a,b,eps,g)
  %f - functia
  %a,b - limitele
  %eps -eroarea
  %g - cuadratura repetata utilizata
  %g in {P1_trapez, P1_Simpson sau P1_dreptunghi}
  
  m=4;
  I1=g(f,a,b,m);
  I2=g(f,a,b,2*m);
  if abs(I1-I2) < eps %succes
    I=I2;
    return
  else %sudivizare recursiva
    I=P3(f,a,(a+b)/2,eps,g)+P3(f,(a+b)/2,b,eps,g);
  endif
endfunction

% P3(@(x) exp(x), 1, 2, 0.001, @(f, a, b, n) P1_trapez(f, a, b, n))
% P3(@(x) exp(x), 1, 2, 0.001, @(f, a, b, n) P1_Simpson(f, a, b, n))
% P3(@(x) exp(x), 1, 2, 0.001, @(f, a, b, n) P1_dreptunghi(f, a, b, n))
