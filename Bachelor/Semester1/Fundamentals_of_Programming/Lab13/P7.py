"""
Pentru un n dat generați toate secvențele de paranteze care se închid corect. Examplu: n=4
două soluții: (()) și ()()
"""

def afisare(x):
    print("".join(x))

def valid(x,n):
    nr1,nr2=0,0
    for i in x:
        if i=="(":
            nr1+=1
        else: nr2+=1
    if nr1>n//2:
        return False
    if nr1>=nr2:
        return True
    return False

def back(x,n):
    if len(x)==n:
        afisare(x)
        return
    x.append(0)
    for i in ["(",")"]:
        x[-1]=i
        if valid(x,n):
            back(x,n)
    x.pop()


n=int(input("Dati n "))
x=[] #vector solutie
back(x,n)