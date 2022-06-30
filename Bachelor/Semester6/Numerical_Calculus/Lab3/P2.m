for n=10:15
  H = hilb(n);
  % by default the Euclidian norm is used to compute cond
  condH = cond(H);
  disp(["n = ", num2str(n), " cond = ", num2str(condH)]);
endfor