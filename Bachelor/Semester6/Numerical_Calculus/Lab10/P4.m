function I=P4(f,a,b,epsi,nmax)
  %calculul aproximativ al unei integrale, metoda lui Romberg
  %apel I=romberg(f,a,b,epsi,nmax)
  %f -functia
  %a,b - limitele de integrare
  %epsi - eroarea
  %nmax - numar maxim de iteratii
  
  if nargin < 5
    nmax=10;
  endif
  if nargin < 4
    epsi=1e-3;
  endif

  R=zeros(nmax,nmax);
  h=b-a;
  
  % prima iteratie
  R(1,1)=h/2*(sum(f([a,b])));
  
  for k=2:nmax
    %formula trapezelor;
    x=a+([1:2^(k-2)]-0.5)*h;
    R(k,1)=0.5*(R(k-1,1)+h*sum(f(x)));
    
    %extrapolare
    plj=4;
    for j=2:k
      R(k,j)=(plj*R(k,j-1)-R(k-1,j-1))/(plj-1);
      plj=plj*4;
    endfor
    
    if (abs(R(k,k)-R(k-1,k-1))<epsi)&(k>3)
      I=R(k,k);
      return
    endif
  
    %dublare noduri
    h=h/2;
  endfor
  
  error('prea multe iteratii')
endfunction

% P4(@(x) exp(x), 1, 2)
