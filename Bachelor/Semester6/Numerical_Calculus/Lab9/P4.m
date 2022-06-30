x = 1900:10:2010;
y = [75.995, 91.972, 105.71, 123.2, 131.67, 150.7, 179.32, 203.21, 226.51, 249.63, 281.42, 308.79];
plot(x, y, 'd');
hold on;

Ox = min(x):1:max(x);

% model polinomial de grad 3
n = 3;
A = zeros(n+1);
b = zeros(n+1, 1);

for i=1:n+1
  for j=1:n+1
    A(i, j) = sum(x.^(i+j-2));
  endfor
  
  b(i) = sum(y.*x.^(i-1));
endfor

coeff = A\b

% model exponential

% y = K*e^(alpha*x) ==> lny = lnK + alpha*x

m = length(x);
%alpha = (sum(x.^2)*sum(y)-sum(x.*y)*sum(x))/(m*sum(x.^2)-sum(x)^2)
%K = exp((m*sum(x.*y)-sum(x)*sum(y))/(m*sum(x.^2)-sum(x)^2))

alpha = (sum(y)*sum(x.*y.*log(y))-sum(x.*y)*sum(y.*log(y)))/(sum(y)*sum(x.^2.*y)-sum(x.*y)^2)
K = exp((sum(x.^2.*y)*sum(y.*log(y))-sum(x.*y)*sum(x.*y.*log(y)))/(sum(y)*sum(x.^2.*y)-sum(x.*y)^2))

pkg load symbolic;
syms x;
polin = sym(0);
for i=1:n+1
    polin += vpa(coeff(i))*x^(i-1);
endfor

expon = @(argument) K .* exp(alpha .* argument);
plot(Ox, expon(Ox));

plot(Ox, double(subs(polin, x, vpa(Ox))));
