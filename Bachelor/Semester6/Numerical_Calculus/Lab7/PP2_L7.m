f = @(x) sin(x);

nodes = [0.30, 0.32, 0.35];
values = [0.29552, 0.31457, 0.34290];
derivatives = [0.95534, 0.94924, 0.93937];
point = 0.34;

hermite = P1_L7(nodes, values, derivatives, [point])
software = sin(point)

major = error(f, nodes, point);
disp(['Error is <= ', num2str(major)]);

disp('-----');

nodes(end + 1) = 0.33;
values(end + 1) = sin(0.33);
derivatives(end + 1) = cos(0.33);

hermite = P1_L7(nodes, values, derivatives, [point])
software = sin(point)

major = error(f, nodes, point);
disp(['Error is <= ', num2str(major)]);
