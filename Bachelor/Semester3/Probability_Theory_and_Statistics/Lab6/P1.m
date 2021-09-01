function out = Lab6_1(n)
  m = 165; sigma = 10;
  X = normrnd(m,sigma,1,n);
  
  % i) histograma frecventelor absolute
  figure;
  hist(X,10,'FaceColor','g');
  
  % ii) histograma frecventelor relative
  valori = [min(X):0.01:max(X)];
  fctDensitate = normpdf(valori,m,sigma);
  
  % grosime (latime) bara = (max(X)-min(X))/10
  % aria = 1 <=> lungimi*latimi = 1 <=> lungimi = 1/latimi
  % ==> lungimi = 10/(max(X)-min(X))
  
  figure;
  hold on;
  hist(X,10,10/(max(X)-min(X)),'FaceColor','b');
  plot(valori,fctDensitate,'-r');
  
  % iii)
  E = mean(X)
  Std = std(X)
  % teoretic : media = 165, deviatia = 10
  
  P = mean(X>=160 & X<=170)
  Pt = normcdf(170,m,sigma) - normcdf(160,m,sigma) % teoretic
   
  out=n;
endfunction
