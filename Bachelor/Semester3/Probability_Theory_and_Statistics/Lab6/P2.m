function out = Lab6_2(g,a,b,n)
  % a)
  interval = [a:0.01:b];
  valoriFunctie = g(interval);
  
  M = max(valoriFunctie);
  X = unifrnd(a,b,1,n);
  Y = unifrnd(0,M,1,n);
  figure;
  hold on;
  plot(interval,valoriFunctie,'-r');
  
  contor = 0;
  for i=1:n
      if Y(i) < g(X(i)) % se afla sub grafic 
        plot(X(i),Y(i),'xk');
        contor+=1;
      else
        plot(X(i),Y(i),'dg');
      endif
  endfor 
  
  % b)
  % metoda geometrica
  IntGeo = (contor/n)*(b-a)*M
  % metoda cu convergenta sirurilor
  IntConv = (b-a)*mean(g(X))
  % teoretic
  Teoretic = integral(g,a,b)
  
  out=n;
endfunction
