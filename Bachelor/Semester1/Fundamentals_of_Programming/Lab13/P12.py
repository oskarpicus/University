"""
Se dă o listă de numere întregi a1,...an. Determinaţi toate posibilităţile de a insera
operatorul de + şi – între numere astfel încât rezultatul expresiei este pozitiv.
"""
# a + b - c

def consistent(lista,x):
    if len(x)!=n-1:
        return False
    s=lista[0]
    for i in range(1,n):
        if x[i-1]=="+":
            s+=lista[i]
        else:
            s-=lista[i]

        """if i==0 and i!=n-2:
            if x[i]=="+":
                s+=lista[i]+lista[i+1]
            else:
                s+=lista[i]-lista[i+1]
            i+=2
        else:
            if x[i]=="+":
                s+=lista[i]
            else:
                s-=lista[i]"""
    if s>=0:
        return True
    return False

def afisare(lista,x,n):
    str=""
    for i in range(n-1):
        if i==0:
            str+=f"{lista[i]} {x[i]} {lista[i+1]} "
        else: str+=f" {x[i]} {lista[i+1]} "
    print(str)

def back(lista,n,x):
    if len(x)==n-1:
        return
    x.append(0)
    for i in ["+","-"]:
        x[-1]=i
        if consistent(lista,x):
            afisare(lista,x,n)
        back(lista,n,x)
    x.pop()

n=int(input("Dati n "))
lista=[]
print("Dati elementele listei: ")
for i in range(0,n):
    lista.append(int(input()))
x=[] #vector cu solutii
back(lista,n,x)
