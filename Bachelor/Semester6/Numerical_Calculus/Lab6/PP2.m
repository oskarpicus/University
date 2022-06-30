pkg load symbolic;

f = @(x) exp(x^2 - 1);
nodes = 1:0.1:1.4;
Ox = 1:0.01:1.4;
m = size(nodes)(2) - 1;

i = 1;
values = zeros(size(nodes));
for node = nodes
   values(i) = f(node);
   i = i + 1;
endfor

point = 1.25;
disp(['Approximate value: ', num2str(P5(nodes, values, [point]))]);

syms x;

% error <= M_(m+1) * |u_m (x)| / (m+1)!

u = prod(point - nodes);
derivative = diff(f, x, m + 1);
M = max(abs(double(subs(derivative, x, vpa(Ox)))));
major = M * abs(u) / factorial(m + 1);

disp(['Error is <= ', num2str(major)]);
