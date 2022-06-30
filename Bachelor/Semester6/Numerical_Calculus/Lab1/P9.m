pkg load symbolic

j = @(x) besselj(0, 2*x);

R_2_2 = P8(j, 2, 2);
R_4_3 = P8(j, 4, 3);
R_2_4 = P8(j, 2, 4);

syms x theta;
Ox = -1:0.1:1;
plot(Ox, double(subs(j, x, Ox)), 'r')
hold on;
plot(Ox, double(subs(R_2_2, x, Ox)), 'g');
plot(Ox, double(subs(R_4_3, x, Ox)), 'b');
plot(Ox, double(subs(R_2_4, x, Ox)), 'y');
