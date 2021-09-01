function out=Lab2_P1(m,n,N)
  
  fete=zeros(1,m);
  baieti=ones(1,n);
  list=[fete,baieti];
  doua_vecine=0;
  for i=1:N
    permutare=list(randperm(length(list)));
    poz=randi(m+n-1);
    if poz>1 && poz<m+n+1 && list(poz)==0 && list(poz-1)==0
      doua_vecine++;
    end
  end
  out=doua_vecine/N;
end
