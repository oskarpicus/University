"""
Se dă o listă de numere întregi a1,...an, determinați toate sub-secvențele (ordinea
elementelor este menținută) strict crescătoare
"""


def continuare(sublista):
    if sublista == sorted(sublista):
        return True
    return False


def back(lista,inceput,n):
    for i in range(inceput+1,n+1):
        final=i
        sublista=lista[inceput:final]
        if continuare(sublista):
            print(sublista)
        else:
            back(lista,inceput+1,n)
        if inceput==n-1:
            exit()
        if final==n:
            back(lista,inceput+1,n)
            break



n=int(input("Dati dimensiunea listei "))
lista=[]
print("Dati elementele listei ")
for i in range(n):
    lista.append(int(input()))
x=[] #vector solutie
back(lista,0,n) #lista, inceput, n