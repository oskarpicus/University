function out=Lab2_P2_b(N)
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
      distanta=pdist([Centru;P]);
      
      if distanta < pdist([V1;P]) && distanta < pdist([V2;P]) && distanta < pdist([V3;P]) && distanta < pdist([V4;P])
        plot(x,y,'^r') 
        nr_puncte++;
      end
  end
  
  out=nr_puncte/N;
  
end
