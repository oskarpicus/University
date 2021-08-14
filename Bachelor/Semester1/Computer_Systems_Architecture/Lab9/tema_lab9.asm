bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf,fopen,fread,fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    numefisier db "rezultat.txt",0
    modacces db "r",0
    descriptor dd -1
    len equ 100
    sir times len db 0
    mesaj db "Caracterul %c are frecventa maxima egala cu %d ",0
    frecventa db 0
    caracter db 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
    ;9.Se da un fisier text. Sa se citeasca continutul fisierului, sa se determine caracterul special (diferit de litera) cu cea mai mare frecventa si sa se afiseze acel caracter, impreuna cu frecventa acestuia. Numele fisierului text este definit in segmentul de date.
    
    ;deschidem fisierul 
    push dword modacces
    push dword numefisier
    call [fopen]
    add esp,4*2
    
    ;verificam daca deschiderea a reusit
    cmp eax,0
    je final
    
    mov [descriptor],eax 
    
    ;citim din fisier
    push dword [descriptor]
    push dword len
    push dword 1 
    push dword sir
    call [fread]
    add esp,4*4
    
    ;verificam daca am citit ceva
    cmp eax,0
    je final
    
    mov ebx,eax ;numar de valori citite
    mov esi,0
    mov dx,1 ;caracterul curent fixat
    
    repeta:   ;parcurgem caracterele speciale
        mov ecx,ebx
        mov al,0 ;frecventa curenta
        mov esi,0  
            parcurgere_sir:      
                cmp [sir+esi],dl
                je adunare_la_frecventa
                jmp fnl     
            adunare_la_frecventa:
                inc al
                 
            fnl: inc esi      
            loop parcurgere_sir
        
        cmp byte[frecventa],al
        jb schimbare
        jmp incrementare_dl
        schimbare: xchg byte[frecventa],al
                    mov byte[caracter],dl 
              
        incrementare_dl: 
           inc dx
           ;verificam daca este caracter special 
           cmp dx,'A'
           je litera
            cmp dx,'a'
           je litera
            jmp final_repeta        
            litera:
                add dx,26  
   
        final_repeta: cmp dx,127  
        jle repeta
     
    ;convertim variabilele la dword si le afisam
    mov al,byte[frecventa]
    cbw
    cwde
    push eax
    
    mov al,byte[caracter]
    cbw
    cwde 
    push eax
    
    push dword mesaj
    call [printf]
    add esp,4*3
    
    inchidere:
        push dword [descriptor]
        call [fclose]
        add esp,4*1
    
    
    final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
