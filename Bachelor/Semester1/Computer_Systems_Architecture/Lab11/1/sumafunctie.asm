bits 32 ; assembling for the 32 bits architecture


; our code starts here
segment code use32 class=code
    global CalculSuma
    CalculSuma:
        ; ...
        mov eax,[esp+4] ;c 
        neg eax 
        add eax,[esp+8]
        add eax,[esp+12]
        mov ebx,eax 
        ret
        
