A = [10, 7, 8, 7; 7, 5, 6, 5; 8, 6, 10, 9; 7, 5, 9, 10];
A1 = [10, 7, 8.1, 7.2; 7.08, 5.04, 6, 5; 8, 5.98, 9.89, 9; 6.99, 4.99, 9, 9.98];

b = [32; 23; 33; 31];
b1 = [32.1; 22.9; 33.1; 30.9];

x = [A \ b];
x1 = [A \ b1];
x2 = [A1 \ b];

relativeErrorInput1 = norm(b - b1)/norm(b);
relativeErrorInput2 = norm(A - A1)./norm(A);

relativeError1 = norm(x1 - x)./norm(x);
relativeError2 = norm(x2 - x)./norm(x);

disp("a)");
disp(["x transpose = ", num2str(transpose(x1))]);
disp(["Relative error (input) = ", num2str(relativeErrorInput1)]);
disp(["Relative error (output) = ", num2str(relativeError1)]);
disp(["r.e.i. / r.e.o. = ", num2str(relativeErrorInput1 / relativeError1)]);

disp("----");

disp("b)");
disp(["x transpose = ", num2str(transpose(x2))]);
disp(["Relative error (input) = ", num2str(relativeErrorInput2)]);
disp(["Relative error (output) = ", num2str(relativeError2)]);
disp(["r.e.i. / r.e.o. = ", num2str(relativeErrorInput2 / relativeError2)]);
