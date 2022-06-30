pkg load symbolic

order = 1;
R = @(k) (1 / (2*k+1));
while (abs(R(order)) > 10^(-5))
  order+=1;
endwhile
order

syms x
ty = taylor(atan(x), x, 0, 'order', order)
estimation = double(subs(ty, x, 1))