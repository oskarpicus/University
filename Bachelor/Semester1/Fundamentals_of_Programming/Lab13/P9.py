"""
Se dau coordonatele pentru n puncte în plan. Determinați toate mulţimile de puncte cu
proprietatea că cel puţin trei puncte din mulţime sunt colineare. Tipăriţi un mesaj dacă
problema nu are soluţie.
"""
from copy import deepcopy
nr=0 #numar solutii
solutii=[] #vector cu solutiile gasite

def afisare(x):
    string=""
    for i in x:
        string+=f"( {i[0]} , {i[1]} ) "
    print(string)
    print("#########################")

def coliniare(x,y,z):
    s= x[0]*y[1]+z[0]*x[1]+y[0]*z[1]-z[0]*y[1]-y[0]*x[1]-x[0]*z[1]
    if s==0:
        return True
    else:
        return False

def solution(x):
    global nr
    global solutii
    if len(x)<3:
        return False
    if sorted(x) in solutii:
        return False
    n=len(x)
    for i in range(0,n):
        for j in range(i+1,n):
            for k in range(j+1,n):
                if coliniare(x[i],x[j],x[k]):
                    nr+=1
                    solutii.append(deepcopy(x))
                    return True
    return False

def consistent(x):
    for n,i in enumerate(x):
        for j in x[n+1:]:
            if i==j:
                return False
    return True

def back(x,puncte,n,nr):
    if len(x)==n:
        return
    x.append([0,0])
    for i in puncte:
        x[-1]=i
        if consistent(x):
            if solution(x):
                afisare(x)
            back(x,puncte,n,nr)
    x.pop()


n=int(input("Dati numar puncte "))
puncte=[]
for i in range(1,n+1):
    print("Punctul ",i)
    puncte.append([int(input("x = ")),int(input("y = "))])
x=[] #vector solutii
back(x,puncte,n,nr)
if nr==0:
    print("Nu exista solutie ")
