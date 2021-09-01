function out=Lab2_P2_c(N)
  %patratul e in origine cu latura 1
  axis square
  rectangle('Position',[0 0 1 1])
  hold on
  nr_puncte=0;
  
  for i=1:N
    
      x=rand;
      y=rand;
      Centru=[1/2,1/2];
      P=[x,y];
      V1=[1,0];
      V2=[1,1];
      V3=[0,0];
      V4=[0,1];
      dist_P_V1=pdist([P;V1]);
      dist_P_V2=pdist([P;V2]);
      dist_P_V3=pdist([P;V3]);
      dist_P_V4=pdist([P;V4]);
      
      obtuz=0;
      if dist_P_V1^2 + dist_P_V2^2 < 1
          obtuz++;
      end
    
      if dist_P_V1^2 + dist_P_V3^2 < 1  
          obtuz++;
      end
      
      if dist_P_V3^2 + dist_P_V4 < 1
          obtuz++;
      end
      
      if dist_P_V2^2 + dist_P_V4^2 < 1
          obtuz++;
      end
      
      if obtuz==2
        plot(x,y,'^r') 
        nr_puncte++;
      end
  end
  
  out=nr_puncte/N;
  
end