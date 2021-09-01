function out = Lab7_c(x,a)
  variantaSelectie = var(x);
  dim = length(x);
  cuantila1 = chi2inv(1-a/2,dim-1);
  cuantila2 = chi2inv(a/2,dim-1);
  left = sqrt((dim-1)*variantaSelectie/cuantila1);
  right = sqrt((dim-1)*variantaSelectie/cuantila2);
  out = [left,right];
endfunction
