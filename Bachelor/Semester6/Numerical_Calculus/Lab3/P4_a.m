pkg load statistics
pkg load symbolic

syms x;
n = 20;
f = sym(0);
fNorm = sym(0);
fUnif = sym(0);

perturbationsNormal = normrnd(0, 10^(-10), 1, n + 1);
perturbationsUniform = unifrnd(-1, 1, 1, n + 1);

for i=0:n
  aN = 2 ^ (-i);
  aNNorm = aN + perturbationsNormal(i + 1);
  aNUnif = aN + perturbationsUniform(i + 1);
  
  f = f + x ^ (n - i) * sym(vpa(aN));
  fNorm = fNorm + x ^ (n - i) * sym(vpa(aNNorm));
  fUnif = fUnif + x ^ (n - i) * sym(vpa(aNUnif));
endfor

f
fNorm
fUnif

solutions = solve(f == 0, x);
diffF = diff(f);
for solution = double(transpose(solutions))
  condNumber = abs(double(subs(diffF, x, vpa(solution))));
  disp(["solution = ", num2str(solution), " cond. number = ", num2str(condNumber)]);
endfor

Ox = -1:0.1:1;
plot(Ox, double(subs(f, x, vpa(Ox))));
hold on;
plot(Ox, double(subs(fNorm, x, vpa(Ox))));
plot(Ox, double(subs(fUnif, x, vpa(Ox))));
