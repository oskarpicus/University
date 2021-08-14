"""Generați toate permmutările de dimensiune n (1..n), cu propritatea că pentru orice i 2<=i<=n
exista un j, 1<=j<=i astfel încât |v(i)-v(j)|=1."""


def continuare(v):
    lista=sorted(v)
    for n,i in enumerate(lista): #este permutare
        if i in lista[(n+1):] and i!=0:
            return False
    return True

def valid(v):
    for i,n in enumerate(v):
        if i==0:
            continue
        ok = False
        for j,m in enumerate(v[0:i+ 1]):
            if abs(v [i] - v [j])==1:
                ok = True
                break
        if ok==False:
            return False
    return True

def afisare(v):
    print(v)

def back(v,n):
    if len(v)==n:
        if valid(v):
            afisare(v)
            return
    v.append(0)
    for i in range(1,n+1):
        v[-1]=i
        if continuare(v):
            back(v,n)
    v.pop()


n=int(input("Dati n "))
v=[] #vector solutie
back(v,n)