function out=Lab4_P1(n)
  N=zeros(1,n);
  for i=1:n
    biti=zeros(1,6);
    
    %primul bit
    biti(1)=binornd(1,0.8);
    
    %al doilea bit 
    if biti(1)==1 
        biti(2)=binornd(1,0.9);
    else
        biti(2)=binornd(1,0.6);
    end
    
    %al treilea bit
    if biti(1)==1 && biti(2)==1
        biti(3)=binornd(1,0.6);
    elseif biti(1)==1 && biti(2)==0
        biti(3)=binornd(1,0.9);
    elseif biti(1)==0 && biti(2)==1
        biti(3)=binornd(1,0.2);
    else 
        biti(3)=binornd(1,0.4);
    end
    
    %al 4-lea bit 
    if biti(3)==1
        biti(4)=binornd(1,0.3);
    else
        biti(4)=binornd(1,0.5);
    end
    
    %al 5-lea bit
    if biti(3)==1
        biti(5)=binornd(1,0.5);
    else
        biti(5)=binornd(1,0.8);
    end
    
    %al 6-lea bit 
    if biti(5)==1 && biti(4)==1
        biti(6)=binornd(1,0.5);
    elseif biti(5)==1 && biti(4)==0
        biti(6)=binornd(1,0.3);
    elseif biti(5)==0 && biti(4)==1
        biti(6)=binornd(1,0.8);
    else 
        biti(6)=binornd(1,0.5);
    end
    
    N(i)=biti(1)*2^0+biti(2)*2^1+biti(3)*2^2+biti(4)*2^3+biti(5)*2^4+biti(6)*2^5;
    
  end
  H=histc(N,0:64);
  bar(0:64,H/n,'hist','FaceColor','r');
  cazFav=sum(N==23);
  out=cazFav/n;
endfunction
