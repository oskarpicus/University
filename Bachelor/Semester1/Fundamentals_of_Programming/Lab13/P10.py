"""
Se dă o listă de numere întregi a1,...an, determinați toate sub-secvențele cu lungime mai
mare decât 2 cu proprietatea că: numerele sunt în ordine crescătoare şi numerele
consecutive au cel puţin o cifră în comun.
"""

def cifra_in_comun(x,y):
    cifrex=[]
    while x>0:
        cifrex.append(x%10)
        x//=10
    while y>0:
        if y%10 in cifrex:
            return True
        y//=10
    return False

def continuare(sublista,n):
    if sorted(sublista)!=sublista or len(sublista)<2:
        return False
    for i in range(0,n-1):
        if cifra_in_comun(sublista[i],sublista[i+1]):
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
x=[] #vector solutie
back(lista,0,n) #lista, inceput, n