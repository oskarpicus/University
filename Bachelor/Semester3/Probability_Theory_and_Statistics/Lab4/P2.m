function out=Lab4_P2_a(k,p)
  pozitii=zeros(1,k);
  for i=2:k+1
    deplasare=binornd(1,p); % 1= dreapta, 0= stanga
    if deplasare==1
      pozitii(i)=pozitii(i-1)+1;
    else
      pozitii(i)=pozitii(i-1)-1;
    end
  end
  pozitii
  out=1;
endfunction
