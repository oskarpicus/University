bits 32 ; assembling for the 32 bits architecture


; our code starts here
segment code use32 class=code
global Concatenare
    Concatenare:
        ; ...
        mov edi,[esp+4]
        mov esi,[esp+20]
        mov ecx,[esp+16]
        cld
        concatenare_s1:
            lodsb
            stosb
        loop concatenare_s1
        mov esi,[esp+12]
        mov ecx,[esp+8]
        concatenare_s2:
            lodsb
            stosb
        loop concatenare_s2
        mov ebx,[esp+4]
        ret 
        
