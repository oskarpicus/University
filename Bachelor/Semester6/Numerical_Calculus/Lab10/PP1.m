function I = PP1( f, a, b, n, mode )
  %Newton-Cotes inchisa
  %f functia de calculat integrala
  %a, b limitele intervalului de integrare
  %n numar noduri
  %mode numele formulei Netwon-Cotes
  %mode in {'trapez', 'simpson', 'simpson3/8', 'boole'}
  
  h = (b - a)/n;
  k = 0 : n;
  x = a + h * k;
  switch mode
      case 'trapez'
          I = 0;
          for node = 1:n-1
              I += (h/2)*(f(x(node))+f(x(node+1)));
          endfor
          
      case 'simpson'
          I = f(x(1)) + f(x(n));
          for node = 2 : n-1
              if(mod((node-1),2) == 0)
                  I = I + 2 * f(x(node));
              endif
              if(mod((node-1),2) ~=0 )
                  I = I + 4 * f(x(node));
              endif
          endfor
          I = (h/3) * I;
          
      case 'simpson3/8'
          I = f(x(1)) + f(x(n));
          for node = 2 : n-1
              if(mod((node-1),3) == 0)
                  I = I + 2 * f(x(node));
              endif
              if(mod((node-1),3) ~=0 )
                  I = I + 3 * f(x(node));
              endif
          endfor
          I = (3*h/8) * I;
          
       case 'boole'
          I = 7*(f(x(1)) + f(x(n)));
          for node = 2 : n-1
              if(mod((node-1),4) == 0 || mod((node-1),4) == 2)
                  I = I + 32 * f(x(node));
              endif
              if(mod((node-1),4) == 3)
                  I = I + 12 * f(x(node));
              endif
              if(mod((node-1),4) == 1 )
                  I = I + 14 * f(x(node));
              endif
          endfor
          I = (2*h/45) * I;
          
  endswitch
endfunction

% PP1(@(x) exp(x), 1, 2, 100, 'trapez')
% PP1(@(x) exp(x), 1, 2, 100, 'simpson')
% PP1(@(x) exp(x), 1, 2, 100, 'simpson3/8')
% PP1(@(x) exp(x), 1, 2, 100, 'boole')
