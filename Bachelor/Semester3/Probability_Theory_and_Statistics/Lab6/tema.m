function out = Tema_Lab6(N) 
  % N = nr simulari
  
  % fie evenimentele A = "printeaza I1", B = "printeaza I2"
  p0=0; pA=0.4; pB=0.6;
  
  % fie variabila aleatoare discreta X ~ ( I1 -> 0.4, I2 -> 0.6 )
  
  U = rand(1,N); % algoritm Lab5
  printare = zeros(1,N);
  contor = 0;
  contor5Sec = 0;
  
  for i=1:N
      if p0<U(i) & U(i)<=p0+pA % printeaza I1 ==> T1
        printare(i) = exprnd(5);
      elseif p0+pA < U(i) & U(i) <= p0+pA+pB % printeaza I2 ==> T2
        printare(i) = unifrnd(4,6);
        if printare(i)>5
            contor+=1;
        endif 
      endif
      if printare(i) > 5
          contor5Sec +=1;
      endif 
  endfor 
  
  % a)
  Medie = mean(printare)
  DevStandard = std(printare)
  
  % b)
  Prob1 = mean(printare>5)
  
  % c)
  % numarul de dati in care a printat I2 si mai mult de 5 sec, din
  % totalul datilor in care s-a printat mai mult de 5 sec
  Prob2 = contor/contor5Sec
  
  out = N;
endfunction
