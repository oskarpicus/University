m = unifrnd(150,170,1,1)
n = 100;
sigma = 10;
a = 5/100;
x = normrnd(m,sigma,1,n);
mean(x)

m0 = 160; % media cu care compar
[a1,~,a2] = ztest(x,m0,sigma,'tail','both','alpha',a); % H0: m=m0, H1: m!=m0
if(a1==0)
  printf('Inaltimea populatiei este %d \n',m0);
else
  printf('Inaltimea populatiei nu este %d \n',m0);
endif

m0 = 155;
[a1,~,a2] = ztest(x,m0,sigma,'tail','left','alpha',a); % H0 : m>=m0, H1 : m<m0
if(a1==0)
  printf('Inaltimea populatiei este cel putin %d \n',m0);
else
  printf('Inaltimea populatiei nu este cel putin %d \n',m0);
endif

m0 = 165;
[a1,~,a2] = ztest(x,m0,sigma,'tail','left','alpha',a); % H0 : m>=m0, H1 : m<m0
if(a1==1)
  printf('Inaltimea populatiei este < %d \n',m0);
else
  printf('Inaltimea populatiei este >= %d \n',m0);
endif
