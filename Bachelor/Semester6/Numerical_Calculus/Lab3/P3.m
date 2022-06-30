warning('off')

disp("a)");
for n=10:15
  Tk = zeros(1, n);
  for k=1:n
    Tk(k) = -1 + k * (2 / n);
  endfor
  V = vander(Tk);
  condCebisev = norm(V, Inf) * norm(inv(V), Inf);
  disp(["n = ", num2str(n), " cond = ", num2str(condCebisev)]);
endfor

disp("-----");
disp("b)");
for n=10:15
  Tk = zeros(1, n);
  for k=1:n
    Tk(k) = 1 / k;
  endfor
  V = vander(Tk);
  condCebisev = norm(V, Inf) * norm(inv(V), Inf);
  disp(["n = ", num2str(n), " cond = ", num2str(condCebisev)]);
endfor

warning('on')