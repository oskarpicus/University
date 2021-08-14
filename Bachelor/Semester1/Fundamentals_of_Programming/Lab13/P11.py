"""
Se dau două numere naturale m şi n. Generaţi liste formate din numere de la 1 la n cu
proprietatea că diferenţa (în valoare absolută) între două numere consecutive din listă este
m. Tipăriţi un mesaj dacă problema nu are soluţie.
"""
nr=0 #numar solutii

def solutie(x):
    if len(x)<2:
        return False
    return True

def consistent(x,m):
    for i in range(0,len(x)-1):
        if abs(x[i]-x[i+1])!=m:
            return False
    return True


def back(lista,x,n,m):
    global nr
    if len(x)==n:
        return
    x.append(0)
    for k,i in enumerate(lista):
        x[-1]=i
        if consistent(x,m):
            if solutie(x):
                print(x)
                nr+=1
            back(lista[k+1:],x,n,m)
    x.pop()


n=int(input("Dati n "))
m=int(input("Dati m "))
lista=list(range(1,n+1))
x=[] #vector solutie
back(lista,x,n,m)
if nr==0:
    print("Nu exista solutie ")