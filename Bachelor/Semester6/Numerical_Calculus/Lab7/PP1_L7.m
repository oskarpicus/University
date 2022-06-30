# add Lab6 directory to PATH (right click)

f = @(x) exp(x);
nodes = [0, 1, 2];
values = f(nodes);
derivatives = f(nodes);
point = 0.25;

hermite = P1_L7(nodes, values, derivatives, [point])
lagrange = P1(nodes, values, [point])
software = exp(point)

major = error(f, nodes, point);

disp(['Error is <= ', num2str(major)]);
