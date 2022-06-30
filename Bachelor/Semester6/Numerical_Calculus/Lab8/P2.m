function z=P2(x,a,b,c,d,t)
  %evaluare spline
  %apel z=P2P2(x,a,b,c,d,t)
  %z - valorile
  %t - punctele in care se face evaluare
  %x - nodurile
  %a,b,c,d - coeficientii
  
  n=length(x);
  x=x(:); 
  t=t(:);
  
  k = ones(size(t));
  for j = 2:n-1
    k(x(j) <= t) = j;
  end
  
  % Evaluare interpolant
  s = t - x(k);
  z = d(k) + s.*(c(k) + s.*(b(k) + s.*a(k)));
endfunction

% [a,b,c,d] = P1([1,2,3],[2,3,5],0);
% P2([1,2,3],a,b,c,d,[1,2,3,5,10]);
