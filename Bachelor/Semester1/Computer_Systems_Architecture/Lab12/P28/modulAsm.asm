bits 32

;informam asamblorul ca dorim ca aceste functii sa fie disponibile si altor unitati de compilare
global _litere_mici
global _litere_mari

;codul scris in asamblare este dispus intr-un segment public, posibil a fi partajat cu alt cod extern
segment code public code use32


; argumente:
; [ebp] - valoarea ebp pentru apelant
; [ebp+4] - adresa de return (valoarea din EIP la momentul apelului)
; [ebp+8] - adresa sir
; [ebp+12] - lungime sir
; [ebp+16] - adresa sir rezultat


;void * litere_mici(char,int,char)
_litere_mici:
    
    ; creare cadru de stiva pentru programul apelat
    push ebp
    mov ebp, esp

    mov esi,[ebp+8] ;sir initial
    mov edi,[ebp+16] ;sir rezultat
    mov ecx,[ebp+12] ;lungime sir initial

    repeta:
        lodsb ; al <- sir[esi]
        cmp al,'a'
        jb final1
        cmp al,'z'
        ja final1
        stosb
    final1:
    loop repeta

    mov al,0 ;terminator
    stosb

    mov eax,[ebp+16] ;returned value

    ; refacem cadrul de stiva pentru programul apelant
    mov esp, ebp
    pop ebp

    ret


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;void * litere_mari(char,int,char)
_litere_mari:
    
    ; creare cadru de stiva pentru programul apelat
    push ebp
    mov ebp, esp


    mov esi,[ebp+8] ;sir initial
    mov edi,[ebp+16] ;sir rezultat
    mov ecx,[ebp+12] ;lungime sir initial

    parcurgere:
        lodsb ;al <-sir[esi]
        cmp al,'A'
        jb final2
        cmp al,'Z'
        ja final2
        stosb

    final2:
    loop parcurgere

    mov al,0 ;terminator
    stosb

    mov eax,[ebp+16] ;returned value

    ; refacem cadrul de stiva pentru programul apelant
    mov esp, ebp
    pop ebp

    ret