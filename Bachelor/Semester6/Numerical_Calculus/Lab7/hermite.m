function f = hermite(nodes, values, derivatives)
  n = 2 * length(nodes);
  Q = zeros(n);
  
  i = 1;
  for value = values
    Q(i, 1) = value;
    Q(i + 1, 1) = value;
    i += 2;
  endfor
  
  nodesNew = zeros(1, n);
  i = 1;
  for node = nodes
    nodesNew(i) = node;
    nodesNew(i+1) = node;
    i += 2;
  endfor

  nrDerivates = 1;
  for j=2:n
    for k=j:n
      if (Q(k, j-1) == Q(k-1, j-1) && nodesNew(k) == nodesNew(k-j+1))
        Q(k, j) = derivatives(nrDerivates);
        nrDerivates += 1;
      else
        Q(k, j) = (Q(k, j-1) - Q(k-1, j-1)) / (nodesNew(k) - nodesNew(k-j+1));
      endif
    endfor
  endfor
  
  syms x;
  f = sym(vpa(values(1)));
  g = sym(1);
  
  for i=1:n-1
    g *= x - nodesNew(i);
    f += vpa(Q(i+1, i+1)) * g;
  endfor
  
endfunction
