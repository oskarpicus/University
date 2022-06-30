# Aproximare Pade
function out = P8(f, m, k)
  pkg load symbolic
  
  syms x;
  fSimb = sym(f);
  derivatives = sym(zeros(m+k, 1));
  derivatives(1) = fSimb;
  
  for i=1:(m+k)
    derivatives(i+1) = diff(derivatives(i));
  endfor
  
  L = zeros(k, k);
  R = zeros(k, 1);
  
  for i=1:k
    start = m + i - 1;
    for j=1:k
      L(i, [j]) = ci(derivatives, start);
      start-=1;
    endfor
  endfor
  
  for i=1:k
    R(i) = -ci(derivatives, m + i);
  endfor
  
  b = [1 ; L \ R];
  
  a = zeros(m+1, 1);
  for j=0:m
    for l=0:j
      if l+1 <= k
        a(j+1) += ci(derivatives, j-l) * b(l+1);
      else
        a(j+1) += 0;
      end
    endfor
  endfor
  
  numarator = sym(0);
  numitor = sym(0);
  
  for i=0:m
    numarator += vpa(a(i+1)) * x^i;
  endfor
  
  for j=0:k
    numitor += vpa(b(j+1)) * x^j;
  endfor
  
  out = numarator / numitor;
  
endfunction

function out = ci(derivatives, i)
    syms x
    if (i < 0)
      out = 0;
    else
      out = double(subs(derivatives(i+1), x, 0)) / factorial(i);
    endif
endfunction
