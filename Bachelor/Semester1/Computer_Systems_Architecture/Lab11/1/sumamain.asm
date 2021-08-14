bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf,scanf,exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll
global start
extern CalculSuma
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    format db '%d',0
    a resd 1
    b resd 1
    c resd 1
    formatafisare db 'Suma = %d',0

; our code starts here
segment code use32 class=code
    start:
        ; ...
    ;citire a
    push dword a
    push dword format
    call [scanf]
    add esp,4*2
    
    ;citire b
    push dword b
    push dword format
    call [scanf]
    add esp,4*2
    
    ;citire c 
    push dword c
    push dword format
    call [scanf]
    add esp,4*2
    
    push dword [a]
    push dword[b]
    push dword[c]
    call CalculSuma
    add esp,4*3
    
    ;afisare suma
    push dword ebx
    push dword formatafisare
    call [printf]
    add esp,4*2 
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
