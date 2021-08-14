bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
global start
extern Concatenare
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    s1 db 'abcd'
    l1 equ $-s1 
    s2 db 'mnop'
    l2 equ $-s2
    s3 times l1+l2+1 db 0
    formatafisare db '%s',0

; our code starts here
segment code use32 class=code
    start:
        ; ...
    push dword s1
    push dword l1
    push dword s2
    push dword l2
    push dword s3
    call Concatenare
    add esp,4*5
    
    push dword ebx
    push dword formatafisare
    call [printf]
    add esp,4*2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
