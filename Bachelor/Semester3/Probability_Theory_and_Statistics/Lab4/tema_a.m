function out=Tema_Lab4_a(n,k,p)
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
    pozitii(i) %afisez pozitia curenta
  endfor
  out=0;
endfunction
