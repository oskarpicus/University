function out=Lab3_P3(M)
  % avem 6 numere castigatoare ==> 43 necastigatoare
  % avem 6 extrageri de numere
  x=zeros(1,M);
  for i=1:M
    contor=0;
    v=hygernd(49,6,6);
    while(v<2)
      v=hygernd(49,6,6);
      contor=contor+1;
    end
  x(i)=contor;
  end
  out=x;
  clf; grid on; hold on;

  prob=hygepdf(2:6,49,6,6);
  p=sum(prob);
N=histc(x,0:max(x));
bar(0:max(x),N/M,'hist','FaceColor','b');
bar(0:max(x),geopdf(0:max(x),p),'FaceColor','y');
legend('estimated probabilities','theoretical probabilities');
endfunction
