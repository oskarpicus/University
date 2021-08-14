"""
2.Se dă un întreg pozitiv, găsiți toate descompunerile în sumă de numere prime
"""
def prim(x):
    ok=True
    if x<2:
        return False
    for i in range(2,x//2+1):
        if x%i==0:
            ok=False
            break
    return ok

def afisare(nr,x,solutii):
    rez=[str(nr)," ="]
    numere=[]
    for i in x:
        if i==0:
            break
        numere.append(i)
        rez+=[" ",str(i)," ","+"]
    rez.pop()
    if sorted(numere) in solutii:
        return
    else:
        solutii.append(numere)
        print("".join(rez))


def continuare(nr,x):
    s=sum(x)
    if s<=nr:
        return True
    return False


def back(k,nr,x,solutii):
    for val in range(1,nr+1):
        if not prim(val):
            if k==0:
                x[1]=0
            continue
        x[k]=val
        if continuare(nr,x):
            if sum(x)==nr:
                afisare(nr,x,solutii)
            else:
                back(k+1,nr,x,solutii)
        else:
            x[k]=0 #anulez operatia
            break


#nr=int(input("Dati numar "))
x=[] #vector solutie
solutii=[] #vector cu toate solutiile
#for i in range(0,nr//2+1):
 #   x.append(0)
#back(0,nr,x,solutii)

eps=10**(-200)
a=1
b=1/2
i=3
while abs(a-b)>eps:
    a=b
    b=1/i
    i+=1
print(a)





