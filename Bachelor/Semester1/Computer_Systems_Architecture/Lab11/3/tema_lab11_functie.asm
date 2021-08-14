bits 32 ; assembling for the 32 bits architecture

; our code starts here
segment code use32 class=code
    global prefix
    ; stiva
    ;------------------------------
    ;   adresa de revenire
    ;------------------------------
    ;   prefix
    ;------------------------------
    ;   lungime sir 2
    ;------------------------------
    ;   sir 2
    ;------------------------------
    ;   lungime sir 1
    ;------------------------------
    ;   sir 1
    ;------------------------------
    
    prefix:
        mov ecx,[esp+8] ;len2
        cmp ecx,[esp+16] ;len1
        jg schimba
        jmp algoritm

        schimba: mov ecx,[esp+16] ;se pune lungimea mai mica
            
        algoritm:
        mov ebx,[esp+20] ;sirul 1
        mov edx,[esp+12] ;sirul 2
        mov edi,[esp+4] ;prefix
        mov esi,0
        repeta:
            mov al,[ebx+esi]
            cmp al,[edx+esi]
            jne final2
            mov byte[edi+esi],al
            inc esi
        loop repeta
        final2:
        mov [edi+esi],byte 0
        ret
        