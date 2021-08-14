bits 32
;informam asamblorul ca dorim ca functia _asmConcat sa fie disponibila altor unitati de compilare
global _asmConcat
segment code public code use32

;char asmConcat(char,int,char,int,char)
;conventie cdecl
_asmConcat:
	;creare cadru de stiva
	push ebp
	mov ebp,esp

	;obtinem argumentele transmise pe stiva functiei sumaNumere
	; la [ebp] - valoarea ebp pentru apeland
	; la [ebp+4] - adresa de revenire
	; la [ebp+8] - adresa sirului 1
	; la [ebp+12] - lungime sir 1
	; la [ebp+16] - adresa sirului 2
	; la [ebp+20] - lungime sir 2
	; la [ebp+24] - adresa sirului rezultat

	mov ecx,[ebp+12] ;lungime sir 1
	mov esi,[ebp+8] ;sir 1
	mov ebx,[ebp+24] ;sir rezultat
	mov edi,0 
	cld
	

	concatenare_sir1:
		lodsb
		mov [ebx+edi],al
		inc edi
	loop concatenare_sir1

	mov ecx,[ebp+20] ;lungime sir 2
	mov esi,[ebp+16] ;sir 2


	concatenare_sir2:
		lodsb ;al <- sir2[esi]
		cmp al,'0'
		jb final
		cmp al,'9'
		ja final
		mov [ebx+edi],al
		inc edi
		final:			
	loop concatenare_sir2

	mov [ebx+edi],byte 0 ;terminator
	
	mov eax,edi

	;refacem cadrul de stiva
	mov esp, ebp
    pop ebp

	ret

