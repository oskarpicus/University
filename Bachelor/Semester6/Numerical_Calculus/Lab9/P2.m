x = [-1.024940;-0.949898;-0.866114;-0.773392;-0.671372;-0.559524;-0.437067;-0.302909;-0.159493;-0.007464];
y = [-0.389269;-0.322894;-0.265256;-0.216557;-0.177152;-0.147582;-0.128618;-0.121353;-0.127348;-0.148895];

% Hal?r, Radim, and Jan Flusser. 
% "Numerically stable direct least squares fitting of ellipses." 
% Proc. 6th International Conference in Central Europe on Computer Graphics and Visualization. WSCG. Vol. 98. Citeseer, 1998.

% elipsa

D1 = [x .^ 2, x .* y, y .^ 2]; % quadratic part of the design matrix
D2 = [x, y, ones(size(x))]; % linear part of the design matrix
S1 = D1' * D1; % quadratic part of the scatter matrix
S2 = D1' * D2; % combined part of the scatter matrix
S3 = D2' * D2; % linear part of the scatter matrix
T = - inv(S3) * S2'; % for getting a2 from a1
M = S1 + S2 * T; % reduced scatter matrix
M = [M(3, :) ./ 2; - M(2, :); M(1, :) ./ 2]; % premultiply by inv(C1)
[evec, eval] = eig(M); % solve eigensystem
cond = 4 * evec(1, :) .* evec(3, :) - evec(2, :) .^ 2; % evaluate a’Ca
a1 = evec(:, find(cond > 0)); % eigenvector for min. pos. eigenvalue
a = [a1; T * a1] % ellipse coefficients

% parabola: y = a+bx+cx^2
n = 2;
A = zeros(n+1);
b = zeros(n+1, 1);

for i=1:n+1
  for j=1:n+1
    A(i, j) = sum(x.^(i+j-2));
  endfor
  
  b(i) = sum(y.*x.^(i-1));
endfor

coeff = A\b
