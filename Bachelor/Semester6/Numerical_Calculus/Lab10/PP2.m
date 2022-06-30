format long;
warning off;

f = @(x) exp(-x.^2);
a = 0;
b = 1;
n = 100;
eps = 0.001;

disp('Regula trapezului');
disp(P1_trapez(f, a, b, n));

disp('Regula lui Simpson');
disp(P1_Simpson(f, a, b, n));

disp('Formula dreptunghiurilor');
disp(P1_dreptunghi(f, a, b, n));

disp('Cuadratura adaptiva cu regula trapezului');
disp(P3(f, a, b, eps, @(f1, a1, b1, n1) P1_trapez(f1, a1, b1, n1)));

disp('Cuadratura adaptiva cu regula lui Simpson');
disp(P3(f, a, b, eps, @(f1, a1, b1, n1) P1_Simpson(f1, a1, b1, n1)));

disp('Cuadratura adaptiva cu formula dreptunghiurilor');
disp(P3(f, a, b, eps, @(f1, a1, b1, n1) P1_dreptunghi(f1, a1, b1, n1)));

disp('Metoda lui Romberg');
disp(P4(f, a, b, eps));

disp('Cuadratura adaptiva II');
[Q, _] = P5(f, a, b, eps);
disp(Q);

disp('Matlab quad');
disp(quad(f, a, b));

disp('Matlab integral')
disp(integral(f, a, b));

warning on;
