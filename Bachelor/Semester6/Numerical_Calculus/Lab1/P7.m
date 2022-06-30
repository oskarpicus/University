function P7(f, n)
  pkg load symbolic
  
  syms x;
  fSimb = sym(f);
  derivatives = sym(zeros(n+1, 1));
  derivatives(1) = fSimb;
  
  for i=1:n
    derivatives(i+1) = diff(derivatives(i));
  endfor
  
  Ox = 1:0.1:5;
  plot(Ox, f(Ox));
  hold on;

  for grad=2:n
    Oy = zeros(size(Ox));
    for i=1:grad
      Oy += double(subs(derivatives(i+1), x, 0)) * Ox.^i / factorial(i);
    endfor
    plot(Ox, Oy);
  endfor
endfunction

# cazuri de utilizare:
# P7(@(x) exp(x), 5)
# P7(@(x) log(1+x), 5)
