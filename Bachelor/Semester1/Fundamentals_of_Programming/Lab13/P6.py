"""
Se dă o listă de numere întregi a1,...an generați toate sub-secvențele cu proprietatea că
suma numerelor este divizibul cu N dat
"""


def continuare(sublista,n):
    if sum(sublista)%n==0:
        return True
    return False


def back(lista,inceput,n):
    for i in range(inceput+1,n+1):
        final=i
        sublista=lista[inceput:final]
        if continuare(sublista,n):
            print(sublista)
        else:
            continue
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
back(lista,0,n) #lista, inceput, n