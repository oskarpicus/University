function out=Lab5_2(lambda,N)
  % inversa functiei : F^(-1)(x)=-1/lambda *log(1-x) 
  U=rand(1,N);
  valori=(-1/lambda) * log(1-U);
  % valoarea medie
  disp('Valoarea medie')
  mean(valori)
  % deviatia standard
  disp('Deviatia standard')
  std(valori)
  out=0;
endfunction
