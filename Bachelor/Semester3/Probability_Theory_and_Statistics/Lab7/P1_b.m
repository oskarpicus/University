function out = Lab7_b(x,a)
  medie = mean(x);
  dim = length(x);
  cuantila = tinv(1-a/2,dim-1);
  abatereSelectie = std(x);
  left = medie - abatereSelectie*cuantila/sqrt(dim);
  right = medie + abatereSelectie*cuantila/sqrt(dim);
  out = [left,right];
endfunction
