m = unifrnd(150,170,1,1);
n = 100;
sigma = unifrnd(5,20,1,1);
x = normrnd(m,sigma,1,n);
a = 1-95/100;
intervalMedie = Lab7_b(x,a) % abaterea standard nu e cunoscuta
intervalAbatere = Lab7_c(x,a)
intervalProportie = Lab7_d(x >= 155 & x <= 165, a)
