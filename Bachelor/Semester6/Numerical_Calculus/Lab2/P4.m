function P4(x)
  y = abs(x);
  wholePart = floor(y);
  floatingPart = y - wholePart;
  
  wholePart = dec2bin(wholePart);
  floatingPart = floatingPartToBinary(floatingPart);
  
  if x >= 0
    disp("S = 0");
  else
    disp("S = 1");
  endif
  
  E = dec2bin(127 + size(wholePart)(2) - 1)
  M = strcat(wholePart(2:end), floatingPart)(1:32)
endfunction

function out = floatingPartToBinary(x)
  out = "";
  for i=1:32
    x = x * 2;
    out = strcat(out, num2str(floor(x)));
    x = x - floor(x);
  endfor
endfunction