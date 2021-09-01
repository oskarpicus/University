function out=Lab7_a(x,s,a)
  medie = mean(x);
  dim = length(x);
  cuantila = norminv(1-a/2,0,1);
  left = medie - s*cuantila/sqrt(dim);
  right = medie + s*cuantila/sqrt(dim);
  out=[left,right];
endfunction
