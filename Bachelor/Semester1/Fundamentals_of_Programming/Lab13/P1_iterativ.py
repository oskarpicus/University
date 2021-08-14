
def afisare(n,v,x,suma):
    """
    Functie pentru afisarea unei solutii corecte
    :param n: int, numar de monezi
    :param v: list, lista cu monezi
    :param x: list, solutie candidat
    :param suma: int, suma dep latit
    :return: -, afiseaza solutia daca este corecta
    """
    s=0
    for i in range(0,n):
        s+=v[i]*x[i]
    if s==suma:
        string=""
        for i in x:
            string+=str(i)+" "
        print(string)

def continuare(k,suma,v,x):
    """
    Functie pentru validarea unei solutii candidat
    :param k: int, pozitia curenta
    :param suma: int, suma de platit
    :param v: list, lista cu monezi
    :param x: list, solutie candidat
    :return: True, daca x poate fi inca considerata o solutie, False, altfel
    """
    s=0
    for i in range(0,k):
        s+=v[i]*x[i]
    if s<=suma:
        return True
    else:
        return False

def solutie(k,suma,v,x):
    s = 0
    if len(x)!=n:
        return False
    for i in range(0, k):
        s += v [i] * x [i]
    if s == suma:
        return True
    else:
        return False

def backIter(suma,n,v):
    x=[-1]
    while len(x)>0:
        choosed=False
        while not choosed and len(x)<=len(v) and x[-1]<=suma//v[len(x)-1]:
            x[-1]=x[-1]+1
            choosed = continuare(len(x),suma,v,x)
        if choosed:
            if solutie(len(x),suma,v,x):
                afisare(n,v,x,suma)
            x.append(-1)
        else:
            x=x[:-1]


suma=int(input("Dati suma de bani "))
n=int(input("Dati numar tipuri de monezi "))
print("Dati valori de monezi: ")
v=[]
for i in range(0,n):
    v.append(int(input()))
if min(v)>suma:
    print("Suma nu poate fi platita ")
    exit()
string=""
for i in v:
    string+=f"Nr. monezi de {i} / "
print(string)
backIter(suma,n,v)
