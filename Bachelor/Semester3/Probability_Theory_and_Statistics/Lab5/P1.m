function out=Lab5_1(N)
  % pentru aplicatie
  p0=0; p1=46/100; p2=40/100;
  p3=10/100; p4=4/100;
  % 1 - grupa 0
  % 2 - grupa A
  % 3 - grupa B
  % 4 - grupa AB
  U=rand(1,N);
  grupe=zeros(1,N);
  for i=1:N
    if p0<U(i) & U(i)<=p0+p1
      grupe(i)=1;
    elseif p0+p1<U(i) & U(i)<=p0+p1+p2
      grupe(i)=2;
    elseif p0+p1+p2<U(i) & U(i)<=p0+p1+p2+p3
      grupe(i)=3;
    else
      grupe(i)=4;
    endif
  endfor
  grupe;
  % frecventele relative
  sum(grupe==1)/N
  sum(grupe==2)/N
  sum(grupe==3)/N 
  sum(grupe==4)/N

  H=histc(grupe,1:4);
  bar(1:4,H/N,'hist','FaceColor','b');
  out=0;
endfunction
