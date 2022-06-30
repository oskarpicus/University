R_1_1 = P8(@(x) exp(x), 1, 1);
R_2_2 = P8(@(x) exp(x), 2, 2);

g = @(x) log(1 + x);
R_2_2_log = P8(g, 2, 2);
R_3_1_log = P8(g, 3, 1);

syms x

figure 1;
Ox = -1:0.1:1;
plot(Ox, exp(Ox), 'r');
hold on;
plot(Ox, double(subs(R_1_1, x, Ox)), 'g');
plot(Ox, double(subs(R_2_2, x, Ox)), 'b');

figure 2;
plot(Ox, g(Ox), 'r');
hold on;
plot(Ox, double(subs(R_2_2_log, x, Ox)), 'g');
plot(Ox, double(subs(R_3_1_log, x, Ox)), 'b');
