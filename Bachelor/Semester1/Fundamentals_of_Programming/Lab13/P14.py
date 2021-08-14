"""
Se dă o listă de numere întregi a1,...an. Generaţi toate permutările listei pentru care
numerele au aspect de vale (descresc până la un punct de unde cresc).
"""


def solution(x,n):
    if len(x)!=n:
        return False
    if sorted(x)==x or sorted(x,reverse=True)==x:
        return False
    bottom=False
    for i in range(1,len(x)):
        if (bottom==False and x[i-1]>x[i]) or (x[i-1]<x[i] and bottom==True):
            continue
        elif bottom==False and x[i-1]!=x[i]:
            bottom=True
        else:
            return False
    return True

def consistent(x,lista):
    if len(x)!=len(lista):
        return True
    l=sorted(lista)
    for n,i in enumerate(sorted(x)):
        if l[n]!=i:
            return False
    return True


def back(lista,n,x):
    if len(x)==n:
        return
    x.append(0)
    for i in lista:
        x[-1]=i
        if consistent(x,lista):
            if solution(x,n):
                print(x)
            back(lista,n,x)
    x.pop()




n=int(input("Dati dimensiunea listei: "))
lista=[]
print("Dati elementele listei: ")
for i in range(n):
    lista.append(int(input()))
x=[] #Vector cu solutia
back(lista,n,x)