function out = Lab7_d(x,a)
  medie = mean(x);
  dim = length(x);
  cuantila = norminv(1-a/2,0,1);
  radical = sqrt(medie*(1-medie)/dim);
  left = medie - radical*cuantila;
  right = medie + radical*cuantila;
  if left < 0
    left = 0;
  endif
  if right > 1
    right = 1;
  endif
  out = [left,right];
endfunction
