x = [0, 30, 45, 60, 90];
sinX = [0, 1/2, sqrt(2)/2, sqrt(3)/2, 1];
cosX = [0, sqrt(3)/2, sqrt(2)/2, 1/2, 0];

disp(['sin(5 degrees) = ', num2str(P5(x, sinX, [5]))]);

disp(['cos(5 degrees) = ', num2str(P5(x, cosX, [5]))]);

% 5 degrees = 5 * pi / 180 radians
