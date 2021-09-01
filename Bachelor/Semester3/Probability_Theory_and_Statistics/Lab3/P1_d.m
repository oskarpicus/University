function out=Lab3_P1_4(N)
  % 0 rosie, 1 albastru, 2 verde
  vector = [0,0,0,0,0,1,1,1,2,2];
  nrA=0;
  nrB=0;
  for i=1:N
    rez=randsample(vector,3,false);
    if rez(1)==0 || rez(2)==0 || rez(3)==0 %are loc A
      nrA=nrA+1;
      if rez(1)==0 && rez(2)==0 && rez(3)==0 %are loc B
          nrB=nrB+1;       
      end
    end
  end
  out=nrB/nrA;
endfunction
