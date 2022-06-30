function P1(f, a, b, n)
  w = @(x) 1;

  phi = {};
  for i=1:n+1
    phi{i} = @(x) x.^(i-1);
  endfor

  A = zeros(n+1);
  b1 = zeros(n+1, 1);

  for j=1:n+1
    for k=1:n+1
      A(j, k) = integral(@(x) w(x).*phi{j}(x).*phi{k}(x), a, b);
    endfor
  endfor

  for j=1:n+1
    b1(j) = integral(@(x) w(x).*f(x).*phi{j}(x), a, b);
  endfor

  coefficients = A \ b1

  pkg load symbolic;
  syms x;
  rez = sym(0);
  for i=1:n+1
    rez += coefficients(i)*x^(i-1);
  endfor
  rez

  Ox=a:0.1:b;
  plot(Ox, f(Ox));
  hold on;
  plot(Ox, double(subs(rez, x, vpa(Ox))));

endfunction

% P1(@(x) exp(x), 1, 4, 3)
