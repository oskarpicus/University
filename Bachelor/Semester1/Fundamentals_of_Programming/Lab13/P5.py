"""
Generați bilete la PRONOSPORT pentru un bilet cu N meciuri. Pronosticurile pentru un meci
pot fi 1,X,2. Generați toate variantele astfel încât: pronosticul de la ultimul meci nu poate fi X
și există un maxim de două meciuri cu pronosticul 1
"""

def afisare(x):
    for i in x:
        print(f"Meciul {i[0]} - pronostic {i[1]}")
    print("###########################################")

def continuare(x):
    if x[-1][1]=="X":
        return False
    nr=0
    for n,i in enumerate(x):
        if i[1]=="1":
            nr+=1
        if nr==3:
            return False
        for j in x[(n+1):]:
            if i==j:
                return False
    return True


def back(pronostic,n,x):
    if len(x)==n:
        return
    x.append([0,"0"])
    for i in pronostic:
        x[-1]=i
        if continuare(x):
            afisare(x)
        back(pronostic,n,x)
    x.pop()



n=int(input("Dati numar meciuri "))
pronostic=[]
for i in range(n):
    pronostic.append([i,input(f"Pronostic meci {i}: ")]) # [numar meci, pronostic]
x=[] #vector solutie
back(pronostic,n,x)