function out=Lab2_P2_a(N)
  %patratul e in origine cu latura 1
  axis square
  rectangle('Position',[0 0 1 1])
  hold on
  nr_puncte=0;
  
  for i=1:N
    
    %ecuatia cercului tangent (este centrat in (1/2,1/2):
    % (x-1/2)^2 + (y-1/2)^2 = 1/4 (are raza 1/2)
    % ==> punctele care se afla in interior verifica (x-1/2)^2 + (y-1/2)^2 < 1/4
    
      x=rand;
      y=rand;
      O=[1/2,1/2];
      P=[x,y];
      %if (x-1/2)*(x-1/2) + (y-1/2)*(y-1/2) < 1/4
      if pdist([O;P])<1/2
        plot(x,y,'^r') 
        nr_puncte++;
      end
  end
  
  out=nr_puncte/N;
  
end
