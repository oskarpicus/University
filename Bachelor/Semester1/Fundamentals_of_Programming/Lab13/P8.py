"""
Generați toate sub-secvențele de lungime 2n+1, formate din 0, -1 și 1, astfel încât a1 = 0, ...,
a2n+1 = 0 și |a(i+1) - ai| = 1 sau 2, pentru orice i, 1 <= i <= 2n.
"""

def solution(x):
    if len(x)==2*n+1 and x[-1]==0:
        return True
    else:
        return False

def consistent(x):
    if x[0]!=0:
        return False
    for i,n in enumerate(x[:-1]):
        dif=abs(x[i+1]-x[i])
        if dif!=1 and dif!=2:
            return False
    return True

def back(x,n):
    if len(x)==2*n+1:
        return
    x.append(0)
    for i in [0,-1,1]:
        x[-1]=i
        if consistent(x):
            if solution(x):
                print(x)
            back(x,n)
    x.pop()


n=int(input("Dati n "))
x=[] #vector solutie
back(x,n)
