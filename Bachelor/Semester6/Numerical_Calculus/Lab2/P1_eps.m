function mEps = P1_eps()
  mEps = 1;
  x = 1;
  while x + mEps != x
    mEps = mEps / 2;
  endwhile
  mEps = mEps * 2;
  disp(["My machine eps: ", num2str(mEps)])
  disp(["eps command: ", num2str(eps)])
endfunction
