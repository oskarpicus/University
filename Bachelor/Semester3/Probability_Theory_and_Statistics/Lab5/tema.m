function out=Tema_Lab5(N)
  aruncariTotal = 0;
  for i=1:N
    monede=rand(1,2);
    aruncariCurent=0;
    while monede(1)>0.5 || monede(2)>0.3
      aruncariCurent +=1;
      monede=rand(1,2);
    endwhile
    aruncariTotal += aruncariCurent;
  endfor
  out=aruncariTotal/N;
endfunction

