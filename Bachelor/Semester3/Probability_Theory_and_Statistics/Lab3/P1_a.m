function out=Lab3_P1_1(N)
  % 0 rosie, 1 albastru 2 verde
  vector = [0,0,0,0,0,1,1,1,2,2];
  nr=0;
  for i=1:N
    rez=randsample(vector,3,false);
    if rez(1)==0 || rez(2)==0 || rez(3)==0
      nr=nr+1;
    end
  end
  out=nr/N;
endfunction
