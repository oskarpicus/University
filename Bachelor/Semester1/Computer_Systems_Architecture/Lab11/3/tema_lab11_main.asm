bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
extern prefix
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    sir1 db 'abcdddefghi'
    len1 equ $-sir1
    sir2 db 'abcdefFFF'
    len2 equ $-sir2
    sir3 db 'abcdefF'
    len3 equ $-sir3
    prefix1_2 times len1+1 db 0
    prefix2_3 times len2+1 db 0
    prefix1_3 times len3+1 db 0
    format1 db "Cel mai lung prefix dintre primul si al doilea sir este : %s ",10,0
    format2 db "Cel mai lung prefix dintre al treilea si al doilea sir este : %s ",10,0
    format3 db "Cel mai lung prefix dintre primul si al treilea sir este : %s ",10,0
; our code starts here
segment code use32 class=code
    start:
        ; ...
    ;7. Se dau trei siruri de caractere. Sa se afiseze cel mai lung prefix comun pentru fiecare din cele trei ;perechi de cate doua siruri ce se pot forma.
    
    
    ;prefixul pentru perechea (1,2)
    push dword sir1
    push dword len1
    push dword sir2
    push dword len2
    push dword prefix1_2
    call prefix
    add esp,4*5

    ;afisam prefixul pentru perechea (1,2)
    push dword prefix1_2
    push dword format1
    call [printf]
    add esp,4*2
    
    ;prefixul pentru perechea (2,3)
    push dword sir2
    push dword len2
    push dword sir3
    push dword len3
    push dword prefix2_3
    call prefix
    add esp,4*5
    
    ;afisam prefixul pentru perechea (2,3)
    push dword prefix2_3
    push dword format2
    call [printf]
    add esp,4*2
    
    ;prefixul pentru perechea (1,3)
    push dword sir1
    push dword len1
    push dword sir3
    push dword len3
    push dword prefix1_3
    call prefix
    add esp,4*5
    
    ;afisam prefixul pentru perechea (1,3)
    push dword prefix1_3
    push dword format3
    call [printf]
    add esp,4*2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
