pkg load symbolic

syms x
ty = taylor(log(1 + x), x, 0, 'order', 8);
estimation = double(subs(ty, x, 1))

# serie alternanta ==> |restul| < |prim termen neglijat|
R = @(n) 1 / (n + 1);  # x este 1 pentru a fi ln2
nrTermeni = 1;
while (abs(R(nrTermeni)) > 10^(-5))
  nrTermeni+=1;
endwhile
nrTermeni

disp('--------')

# pentru ln((1+x)/(1-x)) = ln(2) ==> x = 1/3
ty = taylor(log((1+x) / (1-x)), x, 0, 'order', 10);
estimation = double(subs(ty, x, vpa(1/3)));
R = @(k) 2 / (3^(2*k+1) * (2*k+1));
nrTermeni = 1;
while (abs(R(nrTermeni)) > 10^(-5))
  nrTermeni+=1;
endwhile
nrTermeni
