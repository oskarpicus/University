function ff = P5(nodes, y, points)
  c = barycentricWeigths(nodes);
  
  n = length(nodes) - 1;
  numer = zeros(size(points));
  denom = zeros(size(points));
  exact = zeros(size(points));
  
  for j=1:n+1
    xdiff = points - nodes(j);
    temp = c(j)./xdiff;
    numer = numer + temp*y(j);
    denom = denom + temp;
    exact(xdiff==0) = j;
  endfor
  
  ff = numer ./ denom;
  jj = find(exact);
  ff(jj) = y(exact(jj));
endfunction

% P5([2, 2.75, 4], [0.5, 4/11,1/4], [2, 2.75, 3, 5])

function c = barycentricWeigths(x)
  % x - nodes
  % c - weights
  
  n=length(x)-1;
  c=ones(1,n+1);
  
  for j=1:n+1
    c(j)=prod(x(j)-x([1:j-1,j+1:n+1]));
  end
  
  c=1./c;
endfunction
