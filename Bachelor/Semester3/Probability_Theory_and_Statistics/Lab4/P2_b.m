function out=Lab4_P2_b(m,k,p)
  pozitii_finale=zeros(1,m);
  pasi_succesivi=zeros(1,m);
  for j=1:m
      pozitii=zeros(1,k);
      pas_stanga=0;
      nr_succesive_dreapta=0;
      for i=2:k+1
          deplasare=binornd(1,p); % 1= dreapta, 0= stanga
          if deplasare==1
            pozitii(i)=pozitii(i-1)+1;
              if pas_stanga==0
                  nr_succesive_dreapta+=1;
              end 
          else
            pozitii(i)=pozitii(i-1)-1;
            pas_stanga=1;
          end
      end
      pozitii_finale(j)=pozitii(k+1);
      pasi_succesivi(j)=nr_succesive_dreapta;
  end
  
  
  H=histc(pozitii_finale,-10:10);
  bar(-10:10,H/m,'hist','FaceColor','b');
  % c)
  out=mean(pasi_succesivi);
endfunction
