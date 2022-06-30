pkg load symbolic

# Taylor Series for e^(-t^2), then integrate
syms t x
ty = taylor(exp(-t^2), t, 0, 'order', 5);

erf = sym(2 / sqrt(pi), 'f') * int(ty, t, 0, x);
computed = double(subs(erf, x, 1))

# Directly, Taylor Series for erf function
g = vpa(2 / sqrt(pi)) * int(exp(-t^2), t, 0, x);
erf2 = taylor(g, x, 0, 'order', 5);
computed = double(subs(erf2, x, 1))
