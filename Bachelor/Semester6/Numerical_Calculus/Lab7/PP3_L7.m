time = [0, 3, 5, 8, 13];
distance = [0, 225, 383, 623, 993];
velocity = [75, 77, 80, 74, 72];

acceleration = zeros(1, length(velocity));
for i=1:length(velocity)-1
  acceleration(i) = (velocity(i+1) - velocity(i)) / (time(i+1) - time(i));
endfor

points = [10];
distanceAprox = P1_L7(time, distance, velocity, points)
speedAprox = P1_L7(time, velocity, acceleration, points)