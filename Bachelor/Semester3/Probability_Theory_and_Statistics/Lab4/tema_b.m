function out=Tema_Lab4_b(m,n,k,p)
  pozitii_finale=zeros(1,m);
  for j=1:m
      pozitii=zeros(1,k);
      for i=2:k+1
        if binornd(1,p)==1 % spre dreapta
            if pozitii(i-1)==n-1 % ==> mergem in nodul 0
                  pozitii(i)=0;
            else
                  pozitii(i)=pozitii(i-1)+1;
            endif
        else % spre stanga 
            if pozitii(i-1)==0 % ==> mergem in nodul (n-1)
                  pozitii(i)=n-1;
            else
                  pozitii(i)=pozitii(i-1)-1;
            endif
        endif
      endfor
      pozitii_finale(j)=pozitii(k+1);
  endfor
  
  H=histc(pozitii_finale,-10:10);
  bar(-10:10,H/m,'hist','FaceColor','b');
  out=1;
endfunction
